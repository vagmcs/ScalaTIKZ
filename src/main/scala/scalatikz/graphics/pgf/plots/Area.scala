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
import scalatikz.graphics.pgf.DataTypes.Coordinates
import scalatikz.graphics.pgf.enums.Pattern.PLAIN
import scalatikz.graphics.pgf.enums._

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X
  * and fills the area beneath the curve.
  *
  * @param coordinates sequence of x, y points in the Euclidean space.
  * @param lineColor area color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  * @param pattern the pattern to fill the area under the curve
  * @param opacity opacity of the area under the curve
  * @param lineType
  */
case class Area(
    coordinates: Coordinates,
    lineColor: Color,
    fillColor: Color,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    lineStyle: LineStyle,
    lineSize: LineSize,
    pattern: Pattern,
    opacity: Double,
    lineType: LineType) extends PGFPlot {

  override def toString: String =
    raw"""
       |\addplot[
       |  $lineType,
       |  color=$lineColor,
       |  $lineStyle,
       |  $lineSize,
       |  mark=$marker,
       |  mark size=${markSize}pt,
       |  mark options={draw=$markStrokeColor, fill=$markFillColor},
       |  fill opacity=$opacity,
       |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$fillColor" else s"fill=$fillColor"}
       |] coordinates {
       |${coordinates.mkString("\n")}
       |};
    """.stripMargin
}
