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

package scalatikz.pgf.plots.types

import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.Pattern.PLAIN
import scalatikz.pgf.plots.enums.{ Color, LineSize, LineStyle, Pattern }

/**
  * Creates 2D bars of the data in Y versus the corresponding values in X.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param color line color
  * @param pattern a pattern to fill the bars
  * @param lineStyle line style
  * @param lineSize line size
  * @param opacity opacity of the bars
  * @param barWidth the bars width
  */
case class xBar(
    coordinates: Coordinates2D,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         |\addplot[
         |  ybar,
         |  color=$color,
         |  $lineStyle,
         |  $lineSize,
         |  bar width=$barWidth,
         |  fill opacity=$opacity,
         |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"fill=$color"}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
}

/**
  * Creates 2D bars of the data in X versus the corresponding values in Y.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param color line color
  * @param pattern a pattern to fill the bars
  * @param lineStyle line style
  * @param lineSize line size
  * @param opacity opacity of the bars
  * @param barWidth the bars width
  */
case class yBar(
    coordinates: Coordinates2D,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         |\addplot[
         |  ybar,
         |  color=$color,
         |  $lineStyle,
         |  $lineSize,
         |  bar width=$barWidth,
         |  fill opacity=$opacity,
         |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"fill=$color"}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
}
