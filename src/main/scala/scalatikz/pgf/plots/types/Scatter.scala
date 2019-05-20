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
import scalatikz.pgf.plots.enums.{ Color, Mark }

/**
  * Creates a scatter at the locations specified by the data sequence. The type
  * of the graph is also called a bubble plot.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  */
case class Scatter(
    coordinates: Coordinates2D,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         |\addplot[
         |  only marks,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
}
