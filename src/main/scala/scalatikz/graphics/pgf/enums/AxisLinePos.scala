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

package scalatikz.graphics.pgf.enums

import enumeratum._
import scala.collection.immutable._

sealed abstract class AxisLinePos(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object AxisLinePos extends Enum[AxisLinePos] {

  val values: IndexedSeq[AxisLinePos] = findValues

  case object NONE extends AxisLinePos("none")
  case object BOX extends AxisLinePos("box")
  case object TOP extends AxisLinePos("top")
  case object MIDDLE extends AxisLinePos("middle")
  case object CENTER extends AxisLinePos("center")
  case object BOTTOM extends AxisLinePos("bottom")
  case object LEFT extends AxisLinePos("left")
  case object RIGHT extends AxisLinePos("right")
}
