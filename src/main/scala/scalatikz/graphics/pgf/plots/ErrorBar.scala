/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
 */

package scalatikz.graphics.pgf.plots

import scalatikz.graphics.PGFPlot
import scalatikz.graphics.pgf.Color._
import scalatikz.graphics.pgf.LineSize._
import scalatikz.graphics.pgf.LineStyle._
import scalatikz.graphics.pgf.Mark._
import scalatikz.graphics.pgf.DataTypes.Coordinates

/**
  * Creates a 2D line of the data in Y versus the corresponding values in X
  * along vertical and/or horizontal error bars at each data point.
  *
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.Mark]]
  *      [[scalatikz.graphics.pgf.LineStyle]]
  *      [[scalatikz.graphics.pgf.LineSize]]
  * @param coordinates sequence of x, y points in the Euclidean space.
  * @param error sequence of x, y error points
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  * @param smooth true in case the line is smooth
  */
final class ErrorBar private(coordinates: Coordinates,
                             error: Coordinates,
                             lineColor: Color,
                             marker: Mark,
                             markStrokeColor: Color,
                             markFillColor: Color,
                             markSize: Double,
                             lineStyle: LineStyle,
                             lineSize: LineSize,
                             smooth: Boolean) extends PGFPlot {

  override def toString: String =
    raw"""
       | \addplot[$lineStyle, $lineSize, color=$lineColor,
       |          mark=$marker, mark size=${markSize}pt, ${if (smooth) " smooth," else ""}
       |          mark options={draw=$markStrokeColor, fill=$markFillColor},
       |          error bars/.cd, y dir=both, x dir=both, y explicit, x explicit,
       |          error bar style=solid] coordinates {
       |   ${coordinates.zip(error).map { case (xy, e) => s"$xy +- $e" }.mkString("\n")}
       | };
  """.stripMargin
}

private[graphics] object ErrorBar {

  /**
    * Creates a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *      [[scalatikz.graphics.pgf.Mark]]
    *      [[scalatikz.graphics.pgf.LineStyle]]
    *      [[scalatikz.graphics.pgf.LineSize]]
    * @param coordinates sequence of x, y points in the Euclidean space.
    * @param error sequence of x, y error points
    * @param lineColor line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    * @param smooth true in case the line is smooth
    */
  def apply(coordinates: Coordinates,
            error: Coordinates,
            lineColor: Color,
            marker: Mark,
            markStrokeColor: Color,
            markFillColor: Color,
            markSize: Double,
            lineStyle: LineStyle,
            lineSize: LineSize,
            smooth: Boolean): ErrorBar =
    new ErrorBar(coordinates, error, lineColor, marker, markStrokeColor, markFillColor, markSize, lineStyle, lineSize, smooth)

}