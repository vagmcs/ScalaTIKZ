/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A PGF/TIKZ plot library for Scala.
 *     
 */

package scalatikz.pgf

import almond.api.JupyterApi
import java.awt.Desktop
import java.io.{ File, PrintStream }
import java.nio.file.{ Files, Paths, StandardCopyOption }
import java.util.UUID
import javax.imageio.ImageIO
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.{ ImageType, PDFRenderer }
import org.apache.pdfbox.tools.imageio.ImageIOUtil
import scala.io.Source
import scala.util.{ Success, Failure, Try }
import scalatikz.common.Logging
import scalatikz.pgf.Compiler._
import sys.process._

/**
  * TIKZ picture is the environment that contains (possibly) a sequence
  * of pgf graphic elements to be compiled.
  */
trait TIKZPicture extends Logging {

  val name: String
  protected val libraries: String
  protected val scale: Int = 1
  protected val nodeDistance: Double = 2
  protected val tiKZArgs: List[String] = Nil

  private val path = new File(System.getProperty("java.io.tmpdir"))
  private val texFile: File = new File(s"$path/source.tex")

  private val devNullLogger =
    ProcessLogger(msg => logger.debug(msg), msg => if (!msg.contains("$TERM")) logger.error(msg))

  // Keep scale to 2. It seems much cleaner.
  private def asTex: String =
    raw"""
       |\documentclass{standalone}
       |
       |\usepackage{luatex85}
       |\usepackage{tikz,pgfplots}
       |$libraries
       |\pgfplotsset{compat=newest}
       |
       |\begin{document}
       |\pagestyle{empty}
       |\begin{tikzpicture}[scale=$scale, node distance=${nodeDistance}cm,${tiKZArgs.mkString(",")}]
       |${this.toString}
       |\end{tikzpicture}
       |\end{document}
    """.stripMargin

  /**
    * Compile the TIKZ picture into a PDF.
    *
    * @param compiler a latex compiler
    * @return a file for the generated PDF
    */
  private def compilePDF(compiler: Compiler = PDF_LATEX): File = {
    if (!path.exists) path.mkdir

    val stream: PrintStream = new PrintStream(texFile)
    stream println asTex
    stream.close()

    using(new PrintStream(s"$path/pgf-pie.sty")) { outputStream =>
      val styStream = getClass.getClassLoader.getResourceAsStream("pgf-pie.sty")
      if (styStream == null)
        s"cp ./src/main/resources/pgf-pie.sty $path" ! devNullLogger
      else using(Source.fromInputStream(styStream)) {
        _.getLines.foreach(outputStream.println)
      }
    }

    s"$compiler --shell-escape -output-directory $path ${texFile.getAbsolutePath}" ! devNullLogger

    if (!Files.exists(Paths.get(s"$path/source.pdf"))) fatal {
      using(Source.fromFile(s"$path/source.log")) {
        _.getLines.find(_.startsWith("!")) match {
          case Some(error) => error.drop(2)
          case None => "PDF was not generated but no errors exists in the logs."
        }
      }
    }

    s"rm $path/pgf-pie.sty" ! devNullLogger
    s"rm $path/source.aux $path/source.log $path/source.tex" ! devNullLogger
    s"mv $path/source.pdf $path/$name.pdf" ! devNullLogger

    new File(s"$path/$name.pdf")
  }

  /**
    * Compile the TIKZ picture into an image, either PNG or JPG.
    *
    * @param extension a file extension (can be either PNG or JPG)
    * @param compiler a latex compiler
    * @return a file for the generated image
    */
  private def compileImage(extension: String, compiler: Compiler): Try[File] = Try {
    // load PDF document and create a renderer
    val document = PDDocument.load(compilePDF(compiler))
    val renderer = new PDFRenderer(document)

    val outputPNGFile = new File(s"$path/$name.${extension.toLowerCase}")

    // render image
    val image = renderer.renderImageWithDPI(0, 720, ImageType.RGB)
    ImageIOUtil.writeImage(image, outputPNGFile.getAbsolutePath, 720, 1f)
    document.close()

    outputPNGFile
  }

  /**
    * Moves a given input file.
    *
    * @param file the file to move
    * @param destination the destination path
    * @return the moved file
    */
  private def moveFile(file: File, destination: String): Try[File] = Try {
    Files.move(
      Paths.get(file.getAbsolutePath),
      Paths.get(s"$destination/$name.pdf"),
      StandardCopyOption.REPLACE_EXISTING
    )

    new File(s"$destination/$name.pdf")
  }

  /**
    * Generate and open the resulted figure as PDF using the default
    * desktop application used for PDFs.
    */
  final def show(compiler: Compiler = PDF_LATEX)(implicit kernel: JupyterApi = null): Unit = {
    if (kernel == null) Try(Desktop.getDesktop.open(compilePDF(compiler))) match {
      case Failure(ex) =>
        logger.warn(s"Cannot open PDF: ${ex.getMessage}")
      case _ =>
    }
    else compileImage("png", compiler) match {
      case Failure(exception) => throw exception
      case Success(imageFile) =>

        val img = ImageIO.read(imageFile)
        val ratio = math.max(img.getHeight, img.getWidth).toDouble / math.min(img.getHeight, img.getWidth).toDouble
        val (adjHeight, adjWidth) = if (img.getHeight <= img.getWidth) 33 -> ratio * 33 else ratio * 33 -> 33

        val uuid = UUID.randomUUID.toString
        s"mv $path/$name.png $name-$uuid.png" ! devNullLogger
        kernel.publish.html(s"<img src='$name-$uuid.png' height='$adjHeight%' width='$adjWidth%'/>", id = name)
        Thread.sleep(500)
        s"rm $name-$uuid.png" ! devNullLogger
    }
  }

  /**
    * Save a PDF generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated PDF
    * @return a Try holding the saved PDF file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsPDF(path: String, compiler: Compiler = PDF_LATEX): Try[File] =
    moveFile(compilePDF(compiler), path)

  /**
    * Save a TeX generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated TeX file
    * @return a Try holding the saved TeX file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsTeX(path: String): Try[File] = Try {
    using(new PrintStream(s"$path/$name.tex"))(_ println asTex)
    new File(s"$path/$name.tex")
  }

  /**
    * Save a PNG generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated PNG
    * @return a Try holding the saved PNG file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsPNG(path: String, compiler: Compiler = PDF_LATEX): Try[File] =
    compileImage(extension = "PNG", compiler).flatMap(moveFile(_, path))

  /**
    * Save a JPEG generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated JPEG
    * @return a Try holding the saved JPEG file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsJPEG(path: String, compiler: Compiler = PDF_LATEX): Try[File] =
    compileImage(extension = "JPG", compiler).flatMap(moveFile(_, path))
}
