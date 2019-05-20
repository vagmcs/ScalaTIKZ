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
import scalatikz.pgf.plots.enums._

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X
  * and fills the area beneath the curve.
  *
  * Creates a 2D line of the data in Y versus the corresponding values in X.
  *
  * @param coordinates sequence of x, y points in the Euclidean space.
  * @param lineType
  * @param lineColor area color
  * @param lineStyle line style
  * @param lineSize line size
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param pattern the pattern to fill the area under the curve
  * @param fillColor
  * @param opacity opacity of the area under the curve
  */
case class Line(
    coordinates: Coordinates2D,
    lineType: LineType,
    lineColor: Color,
    lineStyle: LineStyle,
    lineSize: LineSize,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    pattern: Pattern = PLAIN,
    fillColor: Option[Color] = None,
    opacity: Double = 0.5) extends PGFPlot {

  override def toString: String = {

    val fill =
      if (fillColor.nonEmpty && pattern == PLAIN)
        Some(s"fill=${fillColor.get}, fill opacity=$opacity")
      else if (fillColor.nonEmpty && pattern != PLAIN)
        Some(s"pattern=$pattern, pattern color=${fillColor.get}, fill opacity=$opacity")
      else if (fillColor.isEmpty && pattern != PLAIN)
        Some(s"pattern=$pattern, pattern color=$lineColor, fill opacity=$opacity")
      else None

    raw"""
         |\addplot[
         |  $lineType,
         |  color=$lineColor,
         |  $lineStyle,
         |  $lineSize,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor}${if (fill.nonEmpty) s",\n  ${fill.get}" else ""}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
  }
}
