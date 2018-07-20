/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A plot library for Scala.
 *     
 */

package scalatikz.graphics.pgf.plots

import scalatikz.graphics.PGFPlot
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Mark.Mark
import scalatikz.graphics.pgf.DataTypes.Coordinates

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X
  * and fills the area beneath the curve.
  *
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.Mark]]
  *      [[scalatikz.graphics.pgf.LineStyle]]
  *      [[scalatikz.graphics.pgf.LineSize]]
  * @param coordinates sequence of x, y points in the Euclidean space.
  * @param color area color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  * @param opacity opacity of the area under the curve
  * @param smooth true in case the line is smooth
  * @param constant true in case the area is constant
  */
final class Area private(coordinates: Coordinates,
                         color: Color,
                         marker: Mark,
                         markStrokeColor: Color,
                         markFillColor: Color,
                         markSize: Double,
                         lineStyle: LineStyle,
                         lineSize: LineSize,
                         opacity: Double,
                         smooth: Boolean,
                         constant: Boolean) extends PGFPlot {

  override def toString: String =
    raw"""
       | \addplot[$lineStyle, $lineSize, color=$color, mark=$marker, mark size=${markSize}pt, fill=$color,
       |          fill opacity=$opacity, mark options={draw=$markStrokeColor, fill=$markFillColor}
       |          ${if (smooth) ", smooth]" else if (constant) ", const plot]" else "]"} coordinates {
       |   ${coordinates.mkString("\n")}
       | };
  """.stripMargin
}

private[graphics] object Area {

  /**
    * Creates a 2D line of the data in Y versus the corresponding values in X
    * and fills the area beneath the curve.
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *      [[scalatikz.graphics.pgf.Mark]]
    *      [[scalatikz.graphics.pgf.LineStyle]]
    *      [[scalatikz.graphics.pgf.LineSize]]
    * @param coordinates sequence of x, y points in the Euclidean space.
    * @param color area color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    * @param opacity opacity of the area under the curve
    * @param smooth true in case the line is smooth
    * @param constant true in case the area is constant
    */
  def apply(coordinates: Coordinates,
            color: Color,
            marker: Mark,
            markStrokeColor: Color,
            markFillColor: Color,
            markSize: Double,
            lineStyle: LineStyle,
            lineSize: LineSize,
            opacity: Double,
            smooth: Boolean,
            constant: Boolean): Area =
    new Area(coordinates, color, marker, markStrokeColor, markFillColor, markSize, lineStyle, lineSize, opacity, smooth, constant)
}

