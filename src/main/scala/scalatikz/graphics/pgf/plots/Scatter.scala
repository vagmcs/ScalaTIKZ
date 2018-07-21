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
import scalatikz.graphics.pgf.enums.{ Color, Mark }

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
final class Scatter private (
    coordinates: Coordinates,
    marker: Mark,
    markStrokeColor: Color,
    markFillColor: Color,
    markSize: Double) extends PGFPlot {

  override def toString: String =
    raw"""
       | \addplot[mark=$marker, only marks, mark size=${markSize}pt,
       |          mark options={draw=$markStrokeColor, fill=$markFillColor}
       |         ] coordinates {
       |   ${coordinates.mkString("\n")}
       | };
  """.stripMargin
}

private[graphics] object Scatter {

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
  def apply(
      coordinates: Coordinates,
      marker: Mark,
      markStrokeColor: Color,
      markFillColor: Color,
      markSize: Double): Scatter =
    new Scatter(coordinates, marker, markStrokeColor, markFillColor, markSize)
}
