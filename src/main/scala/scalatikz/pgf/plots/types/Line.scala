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

import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }
import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.{ LineType, Mark, Pattern }
import scalatikz.pgf.plots.enums.Pattern.PLAIN

/**
 * Creates a 2D line of the data in X against the corresponding values in Y.
 * Optionally it fills the area under the curve.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space.
 * @param lineType type of line (sharp, const or smooth)
 * @param lineColor line color
 * @param lineStyle line style
 * @param lineSize line size
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 * @param pattern a fill pattern for the area under the curve
 * @param fillColor pattern color
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
  pattern: Pattern,
  fillColor: Option[Color],
  opacity: Double
) extends PGFPlot {

  override def toString: String = {

    val fill =
      if (fillColor.nonEmpty && pattern == PLAIN) Some(s"fill=${fillColor.get}, fill opacity=$opacity")
      else if (fillColor.nonEmpty && pattern != PLAIN)
        Some(s"pattern=$pattern, pattern color=${fillColor.get}, fill opacity=$opacity")
      else if (fillColor.isEmpty && pattern != PLAIN)
        Some(s"pattern=$pattern, pattern color=$lineColor, fill opacity=$opacity")
      else None

    if (fill.isEmpty) raw"""
           |\addplot[
           |  $lineType,
           |  color=$lineColor,
           |  $lineStyle,
           |  $lineSize,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
    else raw"""
           |\addplot[
           |  $lineType,
           |  color=$lineColor,
           |  $lineStyle,
           |  $lineSize,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor},
           |  ${fill.get}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
  }
}

/**
 * Creates a 2D line of the data in X against the corresponding values in Y
 * along vertical and/or horizontal error bars at each data point.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param error sequence of X, Y error points
 * @param lineType line type (sharp, const or smooth)
 * @param lineColor line color
 * @param lineStyle line style
 * @param lineSize line size
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 */
case class ErrorLine(
  coordinates: Coordinates2D,
  error: Coordinates2D,
  lineType: LineType,
  lineColor: Color,
  lineStyle: LineStyle,
  lineSize: LineSize,
  marker: Mark,
  markStrokeColor: Color,
  markFillColor: Color,
  markSize: Double
) extends PGFPlot {

  override def toString: String = raw"""
         |\addplot[
         |  $lineType,
         |  $lineStyle,
         |  $lineSize,
         |  color=$lineColor,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor},
         |  error bars/.cd,
         |  x explicit,
         |  x dir=both,
         |  y explicit,
         |  y dir=both
         |] coordinates {
         |${coordinates.zip(error).map { case (xy, e) => s"$xy +- $e" }.mkString("\n")}
         |};
    """.stripMargin
}

/**
 * Creates a 2D line of the data in X against the corresponding values in Y
 * along an error area around the 2D line.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param error sequence of X, Y error points
 * @param lineType line type (sharp, const or smooth)
 * @param lineColor line color
 * @param lineStyle line style
 * @param lineSize line size
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 * @param fillColor color of the error area
 * @param opacity opacity of the error area
 */
case class ErrorArea(
  coordinates: Coordinates2D,
  error: Coordinates2D,
  lineType: LineType,
  lineColor: Color,
  lineStyle: LineStyle,
  lineSize: LineSize,
  marker: Mark,
  markStrokeColor: Color,
  markFillColor: Color,
  markSize: Double,
  fillColor: Color,
  opacity: Double
) extends PGFPlot {

  override def toString: String = raw"""
         |\addplot[
         |  $lineType,
         |  $lineStyle,
         |  $lineSize,
         |  color=$lineColor,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
         |
         |\addplot[
         |  forget plot,
         |  name path=upper,
         |  color=$fillColor,
         |] coordinates {
         |${coordinates.zip(error).map { case ((x, y), (_, e)) => s"($x,${y + e})" }.mkString("\n")}
         |};
         |
         |\addplot[
         |  forget plot,
         |  name path=lower,
         |  color=$fillColor
         |] coordinates {
         |${coordinates.zip(error).map { case ((x, y), (_, e)) => s"($x,${y - e})" }.mkString("\n")}
         |};
         |
         |\addplot [forget plot, $fillColor, fill opacity=$opacity] fill between [of=upper and lower];
    """.stripMargin
}
