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
  * Creates stems of the a data sequence that extend from a baseline along the
  * x-axis. The data values are indicated by marks terminating each stem.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  */
case class Stem(
    coordinates: Coordinates2D,
    lineColor: Color,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         |\addplot[
         |  ycomb,
         |  color=$lineColor,
         |  mark=$marker,
         |  mark size=${markSize}pt,
         |  mark options={draw=$markStrokeColor, fill=$markFillColor}
         |] coordinates {
         |${coordinates.mkString("\n")}
         |};
    """.stripMargin
}