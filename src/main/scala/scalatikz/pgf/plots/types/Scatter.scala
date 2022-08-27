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

import scalatikz.pgf.enums.Color
import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.Mark

/**
 * Creates a scatter of data points.
 *
 * @note Scatter plot is also called a bubble plot.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 * @param nodesNearCoords depict nodes near coords
 */
case class Scatter(
  coordinates: Coordinates2D,
  marker: Mark,
  markStrokeColor: Color,
  markFillColor: Color,
  markSize: Double,
  nodesNearCoords: Boolean)
    extends PGFPlot {

  override def toString: String =
    if (!nodesNearCoords) raw"""
           |\addplot[
           |  only marks,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
    else raw"""
           |\addplot[
           |  only marks,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor},
           |  nodes near coords,
           |  nodes near coords align={vertical},
           |  nodes near coords style={font=\tiny,/pgf/number format/.cd,fixed,precision=2}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
}

/**
 * Creates a scatter of data points along vertical and/or horizontal
 * error bars at each data point
 *
 * @note Scatter plot is also called a bubble plot.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param error sequence of X, Y error points
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 */
case class ErrorScatter(
  coordinates: Coordinates2D,
  error: Coordinates2D,
  marker: Mark,
  markStrokeColor: Color,
  markFillColor: Color,
  markSize: Double)
    extends PGFPlot {

  override def toString: String = raw"""
         |\addplot[
         |  only marks,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor},
         |  error bars/.cd,
         |  x explicit,
         |  x dir=both,
         |  y explicit
         |  y dir=both
         |] coordinates {
         |${coordinates.zip(error).map { case (xy, e) => s"$xy +- $e" }.mkString("\n")}
         |};
    """.stripMargin
}
