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
import scalatikz.pgf.plots.enums.{ Color, LineSize, LineStyle, Mark, Pattern }

/**
  * Creates 2D bars of the data in X against the corresponding values in Y.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param barColor bar color
  * @param lineStyle line style
  * @param lineSize line size
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param pattern a pattern to fill the bars
  * @param opacity opacity of the bars
  * @param barWidth the bars width
  * @param nodesNearCoords depict nodes near coords
  * @param horizontal horizontal stems extending from Y-axis to X values
  */
case class Bar(
    coordinates: Coordinates2D,
    barColor: Color,
    lineStyle: LineStyle,
    lineSize: LineSize,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    pattern: Pattern,
    opacity: Double,
    barWidth: Double,
    nodesNearCoords: Boolean,
    horizontal: Boolean) extends PGFPlot {

  override def toString: String = {
    if (!nodesNearCoords)
      raw"""
           |\addplot[
           |  ${if (horizontal) "xBar".toLowerCase else "yBar".toLowerCase},
           |  color=$barColor,
           |  $lineStyle,
           |  $lineSize,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor},
           |  bar width=$barWidth,
           |  fill opacity=$opacity,
           |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$barColor" else s"fill=$barColor"}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
    else
      raw"""
           |\addplot[
           |  ${if (horizontal) 'xBar.name.toLowerCase else 'yBar.name.toLowerCase},
           |  color=$barColor,
           |  $lineStyle,
           |  $lineSize,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor},
           |  bar width=$barWidth,
           |  fill opacity=$opacity,
           |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$barColor" else s"fill=$barColor"},
           |  nodes near coords,
           |  nodes near coords align={${if (horizontal) "horizontal" else "vertical"}},
           |  nodes near coords style={font=\tiny}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
  }
}

/**
  * Creates 2D bars of the data in X against the corresponding values in Y
  * along vertical and/or horizontal error bars at each data point.
  *
  * @param coordinates sequence of X, Y points in the Euclidean space
  * @param error sequence of X, Y error points
  * @param barColor bar color
  * @param lineStyle line style
  * @param lineSize line size
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param pattern a pattern to fill the bars
  * @param opacity opacity of the bars
  * @param barWidth the bars width
  * @param horizontal horizontal stems extending from Y-axis to X values
  */
case class ErrorBar(
    coordinates: Coordinates2D,
    error: Coordinates2D,
    barColor: Color,
    lineStyle: LineStyle,
    lineSize: LineSize,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double,
    pattern: Pattern,
    opacity: Double,
    barWidth: Double,
    horizontal: Boolean) extends PGFPlot {

  override def toString: String = {
    raw"""
         |\addplot[
         |  ${if (horizontal) "xBar".toLowerCase else "yBar".toLowerCase},
         |  color=$barColor,
         |  $lineStyle,
         |  $lineSize,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor},
         |  bar width=$barWidth,
         |  fill opacity=$opacity,
         |  ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$barColor" else s"fill=$barColor"},
         |  error bars/.cd,
         |  x explicit,
         |  x dir=${if (horizontal) "both" else "none"},
         |  y explicit,
         |  y dir=${if (horizontal) "none" else "both"}
         |] coordinates {
         |${coordinates.zip(error).map { case (xy, e) => s"$xy +- $e" }.mkString("\n")}
         |};
    """.stripMargin
  }
}
