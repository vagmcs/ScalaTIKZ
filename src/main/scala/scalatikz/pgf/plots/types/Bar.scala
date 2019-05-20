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

import scalatikz.pgf.PGFPlot
import scalatikz.pgf.plots.DataTypes.Coordinates
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
final class xBar private (
    coordinates: Coordinates,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[xbar, color=$color, $lineStyle, $lineSize, bar width=$barWidth, fill opacity=$opacity,
         | ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"fill=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
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
final class yBar private (
    coordinates: Coordinates,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[ybar, color=$color, $lineStyle, $lineSize, bar width=$barWidth, fill opacity=$opacity,
         | ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"fill=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
  """.stripMargin
}

private[pgf] object xBar {

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
  def apply(
      coordinates: Coordinates,
      color: Color,
      pattern: Pattern,
      lineStyle: LineStyle,
      lineSize: LineSize,
      opacity: Double,
      barWidth: Double): xBar =
    new xBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}

private[pgf] object yBar {

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
  def apply(
      coordinates: Coordinates,
      color: Color,
      pattern: Pattern,
      lineStyle: LineStyle,
      lineSize: LineSize,
      opacity: Double,
      barWidth: Double): yBar =
    new yBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}
