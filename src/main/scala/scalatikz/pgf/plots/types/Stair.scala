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

import scalatikz.pgf.PGFPlot
import scalatikz.pgf.plots.DataTypes.Coordinates2D
import scalatikz.pgf.plots.enums.{ Color, LineSize, LineStyle, Mark }

/**
  * Creates a stair step of the data in Y versus the corresponding values in X.
  *
  * @param coordinates sequence of x, y points in the Euclidean space
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  */
final class Stair private (
                            coordinates: Coordinates2D,
                            lineColor: Color,
                            marker: Mark,
                            markStrokeColor: Color,
                            markFillColor: Color,
                            markSize: Double,
                            lineStyle: LineStyle,
                            lineSize: LineSize) extends PGFPlot {

  override def toString: String =
    raw"""
       | \addplot[const plot, $lineStyle, $lineSize, color=$lineColor, mark=$marker,
       |          mark size=${markSize}pt, mark options={draw=$markStrokeColor, fill=$markFillColor}] coordinates {
       |   ${coordinates.mkString("\n")}
       | };
  """.stripMargin
}

private[pgf] object Stair {

  /**
    * Creates a stair step of the data in Y versus the corresponding values in X.
    *
    * @param coordinates sequence of x, y points in the Euclidean space
    * @param lineColor line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    */
  def apply(
             coordinates: Coordinates2D,
             lineColor: Color,
             marker: Mark,
             markStrokeColor: Color,
             markFillColor: Color,
             markSize: Double,
             lineStyle: LineStyle,
             lineSize: LineSize): Stair =
    new Stair(coordinates, lineColor, marker, markStrokeColor, markFillColor, markSize, lineStyle, lineSize)
}
