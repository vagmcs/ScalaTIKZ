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

package scalatikz.graphics.pgf

import scalatikz.graphics.pgf.AxisLinePos._
import scalatikz.graphics.pgf.AxisStyle._
import scalatikz.graphics.pgf.Color._
import scalatikz.graphics.pgf.ColorMap._
import scalatikz.graphics.pgf.FontSize._
import scalatikz.graphics.pgf.GridStyle._
import scalatikz.graphics.pgf.LegendPos._
import com.github.dwickern.macros.NameOf._

/**
  * Axis configuration.
  *
  * @see [[scalatikz.graphics.pgf.Color]]
  *      [[scalatikz.graphics.pgf.GridStyle]]
  *      [[scalatikz.graphics.pgf.FontSize]]
  *      [[scalatikz.graphics.pgf.LegendPos]]
  *      [[scalatikz.graphics.pgf.ColorMap]]
  *      [[scalatikz.graphics.pgf.AxisStyle]]
  *      [[scalatikz.graphics.pgf.AxisLinePos]]
  * @param xMode X axis scale (linear or log)
  * @param yMode Y axis scale (linear or log)
  * @param zMode Z axis scale (linear or log)
  * @param xLabel X label
  * @param yLabel Y label
  * @param zLabel Z label
  * @param xMin X lower bound
  * @param xMax X upper bound
  * @param yMin Y lower bound
  * @param yMax Y upper bound
  * @param zMin Z lower bound
  * @param zMax Z upper bound
  * @param grid axis grid type (minor, major or both)
  * @param colorMap color map type
  * @param backgroundColor axis background color
  * @param header axis header
  * @param fontSize font size
  * @param legends legends for the data sequences
  * @param legendPos legend position
  * @param xAxisLinePos X axis position
  * @param yAxisLinePos Y axis position
  */
final case class Axis private(xMode: AxisStyle = LINEAR,
                              yMode: AxisStyle = LINEAR,
                              zMode: AxisStyle = LINEAR,
                              xLabel: Option[String] = None,
                              yLabel: Option[String] = None,
                              zLabel: Option[String] = None,
                              xMin: Option[Double] = None,
                              xMax: Option[Double] = None,
                              yMin: Option[Double] = None,
                              yMax: Option[Double] = None,
                              zMin: Option[Double] = None,
                              zMax: Option[Double] = None,
                              grid: Option[GridStyle] = None,
                              colorMap: Option[ColorMap] = None,
                              backgroundColor: Color = WHITE,
                              header: Option[String] = None,
                              fontSize: Option[FontSize] = None,
                              legends: Seq[String] = List.empty,
                              legendPos: LegendPos = OUTER_NORTH_EAST,
                              xAxisLinePos: AxisLinePos = BOX,
                              yAxisLinePos: AxisLinePos = BOX) {

  override def toString: String =
    s"${nameOf(xMode).toLowerCase}=$xMode, ${nameOf(yMode).toLowerCase}=$yMode," +
      s"${nameOf(zMode).toLowerCase}=$zMode, axis background/.style={fill=$backgroundColor}," +
      s"axis x line=$xAxisLinePos, axis y line=$yAxisLinePos" +
      s"${if (xLabel.isDefined) s",${nameOf(xLabel).toLowerCase}=${xLabel.get}" else ""}" +
      s"${if (yLabel.isDefined) s",${nameOf(yLabel).toLowerCase}=${yLabel.get}" else ""}" +
      s"${if (zLabel.isDefined) s",${nameOf(zLabel).toLowerCase}=${zLabel.get}" else ""}" +
      s"${if (xMin.isDefined) s",${nameOf(xMin).toLowerCase}=${xMin.get}" else ""}" +
      s"${if (xMax.isDefined) s",${nameOf(xMax).toLowerCase}=${xMax.get}" else ""}" +
      s"${if (yMin.isDefined) s",${nameOf(yMin).toLowerCase}=${yMin.get}" else ""}" +
      s"${if (yMax.isDefined) s",${nameOf(yMax).toLowerCase}=${yMax.get}" else ""}" +
      s"${if (zMin.isDefined) s",${nameOf(zMin).toLowerCase}=${zMin.get}" else ""}" +
      s"${if (zMax.isDefined) s",${nameOf(zMax).toLowerCase}=${zMax.get}" else ""}" +
      s"${if (grid.isDefined) s",${nameOf(grid)}=${grid.get}" else ""}" +
      s"${if (colorMap.isDefined) s",colormap/${colorMap.get}, colorbar" else ""}" +
      s"${if (header.isDefined) s",title=${header.get}" else ""}" +
      s"${if (legends.nonEmpty) s",legend entries={${legends.mkString(",")}}, legend pos=$legendPos" else ""}" +
      s"${if (fontSize.isDefined) s",font=\\${fontSize.get}" else ""}"
}