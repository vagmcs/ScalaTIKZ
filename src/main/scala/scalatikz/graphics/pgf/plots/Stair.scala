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
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.DataTypes.Coordinates
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Mark.Mark

/**
  * Creates a stair step of the data in Y versus the corresponding values in X.
  *
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.Mark]]
  *      [[scalatikz.graphics.pgf.LineStyle]]
  *      [[scalatikz.graphics.pgf.LineSize]]
  * @param coordinates sequence of x, y points in the Euclidean space.
  * @param lineColor line color
  * @param marker mark style
  * @param markStrokeColor mark stroke color
  * @param markFillColor mark fill color
  * @param markSize mark size
  * @param lineStyle line style
  * @param lineSize line size
  */
final class Stair private(coordinates: Coordinates,
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

private[graphics] object Stair {

  /**
    * Creates a stair step of the data in Y versus the corresponding values in X.
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *      [[scalatikz.graphics.pgf.Mark]]
    *      [[scalatikz.graphics.pgf.LineStyle]]
    *      [[scalatikz.graphics.pgf.LineSize]]
    * @param coordinates sequence of x, y points in the Euclidean space.
    * @param lineColor line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    */
  def apply(coordinates: Coordinates,
            lineColor: Color,
            marker: Mark,
            markStrokeColor: Color,
            markFillColor: Color,
            markSize: Double,
            lineStyle: LineStyle,
            lineSize: LineSize): Stair =
    new Stair(coordinates, lineColor, marker, markStrokeColor, markFillColor, markSize, lineStyle, lineSize)
}
