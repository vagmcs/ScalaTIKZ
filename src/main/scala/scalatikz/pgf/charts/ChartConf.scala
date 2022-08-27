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

package scalatikz.pgf.charts

import scalatikz.pgf.charts.enums.TextLocation
import scalatikz.pgf.enums.Color

/**
 * Chart configuration.
 *
 * @param magnify magnify chart pieces
 * @param radius scale radius
 * @param rotationDegrees rotation degrees
 * @param explodeFactor piece explode factor
 * @param textLocation text location
 * @param textBeforeNumbers text appearing before numbers
 * @param textAfterNumbers text appearing after numbers
 * @param colors piece colors
 */
case class ChartConf private[charts] (
  magnify: Boolean = false,
  radius: Int = 3,
  rotationDegrees: Int = 0,
  explodeFactor: Double = 0,
  textLocation: TextLocation = TextLocation.LABEL,
  textBeforeNumbers: Option[String] = None,
  textAfterNumbers: Option[String] = None,
  colors: Option[Seq[Color]] = None) {

  override def toString: String = {
    val builder = new StringBuilder

    if (magnify) builder ++= "\tscale font,\n"
    if (colors.nonEmpty) builder ++= s"\tcolor={${colors.get.mkString(",")}},\n"
    builder ++= s"\tradius=$radius,\n\trotate=$rotationDegrees,\n\texplode=$explodeFactor,\n\ttext=$textLocation,"
    builder ++= s"\n\tbefore number={${textBeforeNumbers.getOrElse("")}},\n\tafter number={${textAfterNumbers.getOrElse("")}}"

    builder.result
  }
}
