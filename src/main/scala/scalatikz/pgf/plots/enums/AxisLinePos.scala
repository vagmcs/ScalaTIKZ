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

package scalatikz.pgf.plots.enums

sealed abstract class AxisLinePos(val entryName: String) {
  override def toString: String = entryName
}

object AxisLinePos {

  val values: IndexedSeq[AxisLinePos] = IndexedSeq(
    NONE,
    BOX,
    TOP,
    MIDDLE,
    CENTER,
    BOTTOM,
    LEFT,
    RIGHT
  )

  def withName(name: String): AxisLinePos = values.find(_.entryName == name).get

  case object NONE extends AxisLinePos("none")
  case object BOX extends AxisLinePos("box")
  case object TOP extends AxisLinePos("top")
  case object MIDDLE extends AxisLinePos("middle")
  case object CENTER extends AxisLinePos("center")
  case object BOTTOM extends AxisLinePos("bottom")
  case object LEFT extends AxisLinePos("left")
  case object RIGHT extends AxisLinePos("right")
}
