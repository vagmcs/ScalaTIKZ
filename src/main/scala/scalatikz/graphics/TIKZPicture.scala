/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
 */

package scalatikz.graphics

import java.awt.Desktop
import java.awt.image.RenderedImage
import java.io.{File, PrintStream}
import java.nio.file.{Files, Paths, StandardCopyOption}
import javax.imageio.ImageIO
import org.ghost4j.document.PDFDocument
import org.ghost4j.renderer.SimpleRenderer
import scala.util.{Failure, Try}
import scalatikz.common.Logging
import sys.process._

/**
  * TIKZ picture is the environment that contains a sequence
  * of graphic elements to be compiled.
  *
  * @tparam T the type of the graphic elements
  */
trait TIKZPicture[T <: Graphic] extends Logging {

  val name: String
  protected val graphics: Seq[T]

  private val path = new File(System.getProperty("java.io.tmpdir"))
  private val texFile: File = new File(s"$path/source.tex")

  private val devNullLogger =
    ProcessLogger(msg => logger.debug(msg), msg => logger.error(msg))

  private def asTex: String =
    raw"""
       | \documentclass{standalone}
       |
       | \usepackage{tikz,pgfplots}
       | \usepackage{pgflibraryplotmarks}
       |
       | \begin{document}
       | \pagestyle{empty}
       |  \begin{tikzpicture}[scale=2]
       |   ${this.toString}
       |  \end{tikzpicture}
       | \end{document}
       |
    """.stripMargin

  /**
    * Compile the TIKZ picture into a PDF.
    *
    * @return a file for the generated PDF
    */
  private def compilePDF: File = {
    if (!path.exists) path.mkdir

    val stream: PrintStream = new PrintStream(texFile)
    stream println asTex
    stream.close()

    s"pdflatex --shell-escape -output-directory $path ${texFile.getAbsolutePath}" ! devNullLogger
    s"rm $path/source.aux $path/source.log $path/source.tex" ! devNullLogger
    s"mv $path/source.pdf $path/$name.pdf" ! devNullLogger

    new File(s"$path/$name.pdf")
  }

  /**
    * Generate and open the resulted figure as PDF using the default
    * desktop application used for PDFs.
    */
  final def show(): Unit = Try(Desktop.getDesktop.open(compilePDF)) match {
    case Failure(ex) =>
      logger.warn(s"Cannot open PDF: ${ex.getMessage}")
    case _ => Runtime.getRuntime
      .addShutdownHook(new Thread {
        override def run() {
          s"rm $path/$name.pdf" ! devNullLogger
        }
      })
  }

  /**
    * Save a PDF generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated PDF
    * @return a Try holding the saved PDF file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsPDF(path: String): Try[File] = Try {
    Files.move(
      Paths.get(compilePDF.getAbsolutePath),
      Paths.get(s"$path/$name.pdf"),
      StandardCopyOption.REPLACE_EXISTING
    )

    new File(s"$path/$name.pdf")
  }

  /**
    * Save a TeX generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated TeX file
    * @return a Try holding the saved TeX file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsTeX(path: String): Try[File] = Try {
    val stream: PrintStream = new PrintStream(s"$path/$name.tex")
    stream println asTex
    stream.close()

    new File(s"$path/$name.tex")
  }

  /**
    * Save a PNG generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated PNG
    * @return a Try holding the saved PNG file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsPNG(path: String): Try[File] = Try {
    // load PDF document
    val doc = new PDFDocument()
    doc.load(compilePDF)

    // create renderer
    val renderer = new SimpleRenderer()
    val outputPNGFile = new File(s"$path/$name.png")

    // set resolution (in DPI)
    renderer.setResolution(300)
    ImageIO.write(
      renderer.render(doc).get(0).asInstanceOf[RenderedImage],
      "png", outputPNGFile
    )

    outputPNGFile
  }

  /**
    * Save a JPEG generated by the TIKZ picture into the given path.
    *
    * @param path a path to save the generated JPEG
    * @return a Try holding the saved JPEG file. In case of non-fatal
    *         exception, a Failure object is returned holding the exception.
    */
  final def saveAsJPEG(path: String): Try[File] = Try {
    // load PDF document
    val doc = new PDFDocument()
    doc.load(compilePDF)

    // create renderer
    val renderer = new SimpleRenderer()
    val outputJPEGFile = new File(s"$path/$name.jpeg")

    // set resolution (in DPI)
    renderer.setResolution(300)
    ImageIO.write(
      renderer.render(doc).get(0).asInstanceOf[RenderedImage],
      "jpeg", outputJPEGFile
    )

    outputJPEGFile
  }
}