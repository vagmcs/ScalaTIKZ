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

package scalatikz.graphics.pgf.plots

import scalatikz.graphics.PGFPlot
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.DataTypes.Coordinates
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Pattern._

/**
  * Creates 2D bars of the data in Y versus the corresponding values in X.
  *
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.LineStyle]]
  *      [[scalatikz.graphics.pgf.LineSize]]
  *      [[scalatikz.graphics.pgf.Pattern]]
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
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.LineStyle]]
  *      [[scalatikz.graphics.pgf.LineSize]]
  *      [[scalatikz.graphics.pgf.Pattern]]
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

private[graphics] object xBar {

  /**
    * Creates 2D bars of the data in Y versus the corresponding values in X.
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *      [[scalatikz.graphics.pgf.LineStyle]]
    *      [[scalatikz.graphics.pgf.LineSize]]
    *      [[scalatikz.graphics.pgf.Pattern]]
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

private[graphics] object yBar {

  /**
    * Creates 2D bars of the data in X versus the corresponding values in Y.
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *      [[scalatikz.graphics.pgf.LineStyle]]
    *      [[scalatikz.graphics.pgf.LineSize]]
    *      [[scalatikz.graphics.pgf.Pattern]]
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
