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
 * Creates stems of the given data extending from the X-axis to their corresponding
 * y values. The data values along the Y-axis are indicated by marks terminating each stem.
 *
 * @note You can also create horizontal stems extending from Y-axis to X values.
 *
 * @param coordinates sequence of X, Y points in the Euclidean space
 * @param lineColor line color
 * @param marker mark style
 * @param markStrokeColor mark stroke color
 * @param markFillColor mark fill color
 * @param markSize mark size
 * @param nodesNearCoords depict nodes near coords
 * @param horizontal horizontal stems extending from Y-axis to X values
 */
case class Stem(
  coordinates: Coordinates2D,
  lineColor: Color,
  marker: Mark,
  markStrokeColor: Color,
  markFillColor: Color,
  markSize: Double,
  nodesNearCoords: Boolean,
  horizontal: Boolean
) extends PGFPlot {

  override def toString: String =
    if (!nodesNearCoords) raw"""
           |\addplot[
           |  ${if (horizontal) "xComb".toLowerCase else "yComb".toLowerCase},
           |  color=$lineColor,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
    else raw"""
           |\addplot[
           |  ${if (horizontal) "xComb".toLowerCase else "yComb".toLowerCase},
           |  color=$lineColor,
           |  mark=$marker,
           |  mark size=${markSize}pt,
           |  mark options={draw=$markStrokeColor, fill=$markFillColor},
           |  nodes near coords,
           |  nodes near coords align={${if (horizontal) "horizontal" else "vertical"}},
           |  nodes near coords style={font=\tiny,/pgf/number format/.cd,fixed,precision=2}
           |] coordinates {
           |${coordinates.mkString("\n")}
           |};
      """.stripMargin
}
