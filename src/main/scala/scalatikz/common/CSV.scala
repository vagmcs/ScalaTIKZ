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

package scalatikz.common

import com.univocity.parsers.csv._
import com.univocity.parsers.common.processor.ColumnProcessor
import java.io.{ File, FileInputStream, InputStream, UnsupportedEncodingException }
import java.util.zip.{ GZIPInputStream, ZipInputStream }
import scala.collection.JavaConverters._
import scala.util.{ Failure, Success, Try }
import scalatikz.graphics.pgf.DataTypes.DataSeq

object CSV {

  /**
    * Maps a given file into an `InputStream` according to its extension. It supports
    * .gz, .zip and .csv file extensions.
    *
    * @param inputFile a given file
    * @return an input stream
    */
  private def toInputStream(inputFile: File): Try[InputStream] = inputFile.getName match {

    case fileName if fileName.matches(".*[.]gz") =>
      Try(new GZIPInputStream(new FileInputStream(inputFile)))

    case fileName if fileName.matches(".*[.]zip") =>
      Try(new ZipInputStream(new FileInputStream(inputFile)))

    case fileName if fileName.matches(".*[.]csv") =>
      Try(new FileInputStream(inputFile))

    case _ => Failure(new UnsupportedEncodingException(s"Unsupported extension for file ${inputFile.getName}."))
  }

  /**
    * Parse an input CSV file and extract the columns specified
    * by the given index sequence.
    *
    * @note The function does not ignore the header line. It assumes
    *       that no headers are available. For parsing using headers
    *       please use '''parseColumns''' function.
    *
    * @param inputFile the input file
    * @param separator separator for the file
    * @param columns a sequence of column indexes
    * @return a sequence of column data
    */
  def parseColumnsByIndex(
      inputFile: File,
      separator: Char,
      columns: Int*): Try[List[DataSeq]] = {

    val inputStream: InputStream = toInputStream(inputFile) match {
      case Success(stream) => stream
      case Failure(ex) => return Failure(ex)
    }

    val processor = new ColumnProcessor()
    val settings = new CsvParserSettings()

    settings.setProcessor(processor)
    settings.getFormat.setDelimiter(separator)
    settings.getFormat.setLineSeparator("\n")

    // CSV parser
    val parser = new CsvParser(settings)
    parser.parse(inputStream)

    val results = Try {
      columns.map(processor.getColumn).toList.map { seq =>
        seq.asScala.toList.map(_.toDouble)
      }
    } match {
      case Success(xs) => xs
      case Failure(ex) => return Failure(ex)
    }

    Success(results)
  }

  /**
    * Parse an input file and extract the columns specified
    * by the given column sequence.
    *
    * @note The function ignores the header line. It assumes
    *       that no data are available in the header. For parsing
    *       using indexes please use '''parseColumnsByIndex'''
    *       function.
    *
    * @param inputFile the input file
    * @param separator separator for the file
    * @param columns a sequence of column names
    * @return a sequence of column data
    */
  def parseColumns(
      inputFile: File,
      separator: Char, columns: String*): Try[List[DataSeq]] = {

    val inputStream: InputStream = toInputStream(inputFile) match {
      case Success(stream) => stream
      case Failure(ex) => return Failure(ex)
    }

    val processor = new ColumnProcessor()
    val settings = new CsvParserSettings()

    settings.setProcessor(processor)
    settings.getFormat.setDelimiter(separator)
    settings.getFormat.setLineSeparator("\n")

    if (!columns.forall(_ forall Character.isDigit))
      settings.setHeaderExtractionEnabled(true)

    // CSV parser
    val parser = new CsvParser(settings)
    parser.parse(inputStream)

    val results = Try {
      if (columns.forall(_ forall Character.isDigit))
        columns.map(_.toInt).map(processor.getColumn).toList.map { seq =>
          seq.asScala.toList.map(_.toDouble)
        }
      else columns.map(processor.getColumn).toList.map { seq =>
        seq.asScala.toList.map(_.toDouble)
      }
    } match {
      case Success(xs) => xs
      case Failure(ex) => return Failure(ex)
    }

    Success(results)
  }
}
