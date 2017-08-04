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

package scalatikz.graphics

package object pgf {

  final val colors: List[Color.Value] = Color.values.toList
  final val markers: List[Mark.Value] = Mark.values.toList
  final val lineStyles: List[LineStyle.Value] = LineStyle.values.toList
  final val lineSizes: List[LineSize.Value] = LineSize.values.toList
  final val legendsPositions: List[LegendPos.Value] = LegendPos.values.toList
  final val fontSizes: List[FontSize.Value] = FontSize.values.toList
  final val axisPositions: List[AxisLinePos.Value] = AxisLinePos.values.toList

  implicit class TeX(val tex: String) extends AnyVal {
    def toTex: String = tex
      .replaceAll("%", "\\\\%")
      .replaceAll("#", "\\\\#")
      .split("\\$").zipWithIndex.map { case (str, i) =>
        if ((i + 1) % 2 != 0) str
            .replaceAll("_", "\\\\_")
            .replaceAll("&", "\\\\&")
            .replaceAll("~", "\\\\~")
            .replaceAll("\\^", "\\\\^")
        else "$" + str + "$"
      }.reduce(_ + _)
  }

  object AxisLinePos extends Enumeration {

    type AxisLinePos = Value

    val BOX = Value("box")
    val TOP = Value("top")
    val MIDDLE = Value("middle")
    val CENTER = Value("center")
    val BOTTOM = Value("bottom")
    val LEFT = Value("left")
    val RIGHT = Value("right")
    //val NONE = Value("none")
  }

  object LegendPos extends Enumeration {

    type LegendPos = Value

    val SOUTH_WEST = Value("south west")
    val SOUTH_EAST = Value("south east")
    val NORTH_WEST = Value("north west")
    val NORTH_EAST = Value("north east")
    val OUTER_NORTH_EAST = Value("outer north east")
  }

  object FontSize extends Enumeration {

    type FontSize = Value

    val TINY = Value("tiny")
    val SCRIPT = Value("script" + "size")
    val FOOTNOTE = Value("footnote" + "size")
    val SMALL = Value("small")
    val NORMAL = Value("normal" + "size")
    val LARGE = Value("large")
    val VERY_LARGE = Value("LARGE")
    val HUGE = Value("huge")
    val VERY_HUGE = Value("HUGE")
  }

  object AxisStyle extends Enumeration {

    type AxisStyle = Value

    val LINEAR = Value("linear")
    val LOG = Value("log")
  }

  object GridStyle extends Enumeration {

    type GridStyle = Value

    val MINOR = Value("minor")
    val MAJOR = Value("major")
    val BOTH = Value("both")
  }

  object LineSize extends Enumeration {

    type LineSize = Value

    val ULTRA_THIN = Value("ultra thin")
    val VERY_THIN = Value("very thin")
    val THIN = Value("thin")
    val THICK = Value("thick")
    val VERY_THICK = Value("very thick")
    val ULTRA_THICK = Value("ultra thick")
  }

  object LineStyle extends Enumeration {

    type LineStyle = Value

    val SOLID = Value("solid")
    val DOTTED = Value("dotted")
    val DENSELY_DOTTED = Value("densely dotted")
    val LOOSELY_DOTTED = Value("loosely dotted")
    val DASHED = Value("dashed")
    val DENSELY_DASHED = Value("densely dashed")
    val LOOSELY_DASHED = Value("loosely dashed")
  }

  object Color extends Enumeration {

    type Color = Value

    val RED = Value("red")
    val BLUE = Value("blue")
    val GREEN = Value("green")
    val YELLOW = Value("yellow")
    val BROWN = Value("brown")
    val CYAN = Value("cyan")
    val MAGENTA = Value("magenta")
    val ORANGE = Value("orange")
    val PINK = Value("pink")
    val PURPLE = Value("purple")
    val VIOLET = Value("violet")
    val TEAL = Value("teal")
    val LIME = Value("lime")
    val BLACK = Value("black")
    val GRAY = Value("gray")
    val WHITE = Value("white")

    implicit class ColorMix(val color: Color) extends AnyVal {

      def !(that: Color): Color =
        if (color.toString matches ".*\\d+") Value(color + "!" + that)
        else Value(color + "!50!" + that)

      def !(percentage: Int): Color =
        if (percentage < 0) Value(color + "!0")
        else if (percentage > 100) Value(color + "!100")
        else Value(color + "!" + percentage)
    }
  }

  object Mark extends Enumeration {

    type Mark = Value

    val NONE = Value("none")
    val X = Value("x")
    val DASH = Value("-")
    val DOT = Value("*")
    val CIRCLE = Value("o")
    val STAR = Value("star")
    val PLUS = Value("+")
    val ASTERISK = Value("asterisk")
    val TRIANGLE = Value("triangle")
    val TRIANGLE_FILLED = Value("triangle*")
    val DIAMOND = Value("diamond")
    val DIAMOND_FILLED = Value("diamond*")
    val SQUARE = Value("square")
    val SQUARE_FILLED = Value("square*")
    val PENTAGON = Value("pentagon")
    val PENTAGON_FILLED = Value("pentagon*")
    val CUBE = Value("cube")
    val CUBE_FILLED = Value("cube*")
  }

  object ColorMap extends Enumeration {

    type ColorMap = Value

    val HOT = Value("hot")
    val COOL = Value("cool")
    val BLUE_RED = Value("blue" + "red")
    val GREEN_YELLOW = Value("green" + "yellow")
    val RED_YELLOW = Value("red" + "yellow")
    val VIOLET = Value("violet")
    val BLACK_WHITE = Value("black" + "white")
  }
}
