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
import scalatikz.pgf.plots.enums.{ Color, LineSize, LineStyle, Mark }

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  * @param smooth true in case the line is smooth
  */
final class Line private (
    coordinates: Coordinates,
    lineColor: Color,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    lineStyle: LineStyle,
    lineSize: LineSize,
    smooth: Boolean) extends PGFPlot {

  override def toString: String =
    raw"""
    | \addplot[$lineStyle, $lineSize, color=$lineColor, mark=$marker, mark size=${markSize}pt,
    |          mark options={draw=$markStrokeColor, fill=$markFillColor}
    |          ${if (smooth) ", smooth]" else "]"} coordinates {
    |   ${coordinates.mkString("\n")}
    | };
  """.stripMargin
}

private[pgf] object Line {

  /**
    * Creates a 2D line of the data in Y versus the corresponding values in X.
    *
    * @param coordinates sequence of x, y points in the Euclidean space
    * @param lineColor line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size (default is 2 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param smooth true in case the line is smooth
    */
  def apply(
      coordinates: Coordinates,
      lineColor: Color,
      marker: Mark,
      markStrokeColor: Color,
      markFillColor: Color,
      markSize: Double,
      lineStyle: LineStyle,
      lineSize: LineSize,
      smooth: Boolean): Line =
    new Line(coordinates, lineColor, marker, markStrokeColor, markFillColor, markSize, lineStyle, lineSize, smooth)
}
