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

package scalatikz

import scalatikz.pgf.Compiler
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }
import scalatikz.pgf.plots.enums._

package object app {

  final val DEFAULT_NAME = "result"

  object GraphType extends Enumeration {
    type GraphType = Value
    val PLOT, SCATTER, MESH, STEM, BAR = Value
  }

  implicit val colorMixRead: scopt.Read[Color] = scopt.Read.reads { x =>
    val colors = x.toLowerCase.split("#")
    colors.tail.foldLeft(Color.withName(colors.head)) {
      case (color, next) if next matches "\\d++" => color ! next.toInt
      case (color, next) => color ! Color.withName(next)
    }
  }

  implicit val compilerRead: scopt.Read[Compiler] = scopt.Read.reads(x => Compiler.withName(x.toLowerCase))

  implicit val markRead: scopt.Read[Mark] = scopt.Read.reads(x => Mark.withName(x.toLowerCase))

  implicit val lineStyleRead: scopt.Read[LineStyle] = scopt.Read.reads(x => LineStyle.withName(x.toLowerCase))

  implicit val lineSizeRead: scopt.Read[LineSize] = scopt.Read.reads(x => LineSize.withName(x.toLowerCase))

  implicit val lineTypeRead: scopt.Read[LineType] = scopt.Read.reads(x => LineType.withName(x.toLowerCase))

  implicit val fontSizeRead: scopt.Read[FontSize] = scopt.Read.reads(FontSize.withName)

  implicit val legendPosRead: scopt.Read[LegendPos] = scopt.Read.reads(x => LegendPos.withName(x.toLowerCase))

  implicit val axisLinePosRead: scopt.Read[AxisLinePos] = scopt.Read.reads(x => AxisLinePos.withName(x.toLowerCase))

  implicit val patternRead: scopt.Read[Pattern] = scopt.Read.reads(x => Pattern.withName(x.toLowerCase))

  implicit val colorMapRead: scopt.Read[ColorMap] = scopt.Read.reads(x => ColorMap.withName(x.toLowerCase))
}
