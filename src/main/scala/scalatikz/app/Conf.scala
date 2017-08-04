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

package scalatikz.app

import scalatikz.app.GraphType.GraphType
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.Figure
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Mark.Mark

trait OptionConf

final case class Conf(output: String = System.getProperty("user.dir"),
                      format: String = "PDF",
                      figure: Figure = Figure(DEFAULT_NAME),
                      inputs: IndexedSeq[String] = IndexedSeq.empty,
                      delimiters: IndexedSeq[Char] = IndexedSeq.empty,
                      graphics: Seq[GraphicConf] = Seq.empty) extends OptionConf

final case class GraphicConf(graph: GraphType,
                             xColumn: Option[String] = None,
                             yColumn: Option[String] = None,
                             xErrorColumn: Option[String] = None,
                             yErrorColumn: Option[String] = None,
                             lineColor: Option[Color] = None,
                             marker: Option[Mark] = None,
                             markStrokeColor: Option[Color] = None,
                             markFillColor: Option[Color] = None,
                             markSize: Double = 2,
                             lineStyle: Option[LineStyle] = None,
                             lineSize: Option[LineSize] = None,
                             opacity: Double = 0.5,
                             smooth: Boolean = false,
                             constant: Boolean = false)
