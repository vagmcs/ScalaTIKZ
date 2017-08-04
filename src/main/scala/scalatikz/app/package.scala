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

package scalatikz

import scalatikz.graphics.pgf._

package object app {

  final val DEFAULT_NAME = "result"

  object GraphType extends Enumeration {
    type GraphType = Value
    val PLOT, STEM, SCATTER, STAIR, AREA, ERROR_BAR = Value
  }

  implicit val colorMixRead: scopt.Read[Color.Value] = scopt.Read.reads { x =>
    val colors = x.toLowerCase.split("#")
    colors.tail.foldLeft(Color.withName(colors.head)) {
      case (color, next) if next matches "\\d++" => color ! next.toInt
      case (color, next) => color ! Color.withName(next)
    }
  }

  implicit val markRead: scopt.Read[Mark.Value] =
    scopt.Read.reads(x => Mark.withName(x.toLowerCase))

  implicit val lineStyleRead: scopt.Read[LineStyle.Value] =
    scopt.Read.reads(x => LineStyle.withName(x.toLowerCase))

  implicit val lineSizeRead: scopt.Read[LineSize.Value] =
    scopt.Read.reads(x => LineSize.withName(x.toLowerCase))

  implicit val fontSizeRead: scopt.Read[FontSize.Value] =
    scopt.Read.reads(FontSize.withName)

  implicit val legendPosRead: scopt.Read[LegendPos.Value] =
    scopt.Read.reads(x => LegendPos.withName(x.toLowerCase))
}
