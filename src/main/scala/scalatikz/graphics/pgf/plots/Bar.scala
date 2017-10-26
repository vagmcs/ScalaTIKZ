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
import scalatikz.graphics.pgf.Pattern.Pattern

final class xBar private(coordinates: Coordinates,
                         color: Color,
                         pattern: Option[Pattern],
                         lineStyle: LineStyle,
                         lineSize: LineSize,
                         opacity: Double,
                         barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[xbar, $lineStyle, $lineSize, bar width = $barWidth,
         | ${if (pattern.isDefined) s"pattern=${pattern.get}, pattern color=$color" else s"color=$color, fill=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
  """.stripMargin
}

final class yBar private(coordinates: Coordinates,
                         color: Color,
                         pattern: Option[Pattern],
                         lineStyle: LineStyle,
                         lineSize: LineSize,
                         opacity: Double,
                         barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[ybar, $lineStyle, $lineSize, bar width = $barWidth,
         | ${if (pattern.isDefined) s"pattern=${pattern.get}, pattern color=$color" else s"color=$color, fill=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
  """.stripMargin
}

private[graphics] object xBar {

  def apply(coordinates: Coordinates,
            color: Color,
            pattern: Option[Pattern],
            lineStyle: LineStyle,
            lineSize: LineSize,
            opacity: Double,
            barWidth: Double): xBar =
    new xBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}
private[graphics] object yBar {

  def apply(coordinates: Coordinates,
            color: Color,
            pattern: Option[Pattern],
            lineStyle: LineStyle,
            lineSize: LineSize,
            opacity: Double,
            barWidth: Double): yBar =
    new yBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}