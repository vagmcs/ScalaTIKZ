package scalatikz

import scalatikz.graphics.pgf._

package object app {

  final val DEFAULT_NAME = "result"

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

  object GraphType extends Enumeration {
    type GraphType = Value
    val PLOT, STEM, SCATTER, STAIR, AREA, ERROR_BAR = Value
  }
}
