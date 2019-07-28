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

package scalatikz.pgf.automata.enums

import enumeratum._
import scala.collection.immutable._

sealed abstract class StatePos(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object StatePos extends Enum[StatePos] {

  val values: IndexedSeq[StatePos] = findValues

  case object ABOVE extends StatePos("above")
  case object ABOVE_LEFT extends StatePos("above left")
  case object ABOVE_RIGHT extends StatePos("above right")
  case object BELOW extends StatePos("below")
  case object BELOW_LEFT extends StatePos("below left")
  case object BELOW_RIGHT extends StatePos("below right")
  case object LEFT extends StatePos("left")
  case object RIGHT extends StatePos("right")
}
