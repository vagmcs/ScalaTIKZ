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
import scalatikz.pgf.plots.enums.{ Color, LineSize, LineStyle, Mark }

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X
  * along vertical and/or horizontal error bars at each data point.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param error sequence of x, y error points
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  * @param smooth true in case the line is smooth
  */
case class ErrorArea(
    coordinates: Coordinates2D,
    error: Coordinates2D,
    lineColor: Color,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    smooth: Boolean) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[$lineStyle, $lineSize, color=$lineColor,
         |          mark=$marker, mark size=${markSize}pt, ${if (smooth) " smooth," else ""}
         |          mark options={draw=$markStrokeColor, fill=$markFillColor}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
         | \addplot[name path=upper, color=$lineColor, ${if (smooth) " smooth," else ""}] coordinates {
         |   ${coordinates.zip(error).map { case ((x, y), (_, e)) => s"($x,${y + e})" }.mkString("\n")}
         | };
         | \addplot[name path=lower, color=$lineColor, ${if (smooth) " smooth," else ""}] coordinates {
         |   ${coordinates.zip(error).map { case ((x, y), (_, e)) => s"($x,${y - e})" }.mkString("\n")}
         | };
         | \addplot [$lineColor, fill opacity=$opacity] fill between [of=upper and lower];
  """.stripMargin
}
