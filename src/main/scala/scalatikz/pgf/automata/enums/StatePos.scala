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

sealed abstract class StatePos(val entryName: String) {
  override def toString: String = entryName
}

object StatePos {

  val values: IndexedSeq[StatePos] =
    IndexedSeq(ABOVE, ABOVE_LEFT, ABOVE_RIGHT, BELOW, BELOW_LEFT, BELOW_RIGHT, LEFT, RIGHT)

  def withName(name: String): StatePos = values.find(_.entryName == name).get

  case object ABOVE extends StatePos("above")
  case object ABOVE_LEFT extends StatePos("above left")
  case object ABOVE_RIGHT extends StatePos("above right")
  case object BELOW extends StatePos("below")
  case object BELOW_LEFT extends StatePos("below left")
  case object BELOW_RIGHT extends StatePos("below right")
  case object LEFT extends StatePos("left")
  case object RIGHT extends StatePos("right")
}
