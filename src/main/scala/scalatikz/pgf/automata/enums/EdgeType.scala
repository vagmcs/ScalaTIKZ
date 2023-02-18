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

sealed abstract class EdgeType(val entryName: String) {
  override def toString: String = entryName
}

object EdgeType {

  val values: IndexedSeq[EdgeType] =
    IndexedSeq(STRAIGHT, LOOP_ABOVE, LOOP_BELOW, LOOP_LEFT, LOOP_RIGHT, BEND_LEFT, BEND_RIGHT)

  def withName(name: String): EdgeType = values.find(_.entryName == name).get

  case object STRAIGHT extends EdgeType("")
  case object LOOP_ABOVE extends EdgeType("loop above")
  case object LOOP_BELOW extends EdgeType("loop below")
  case object LOOP_LEFT extends EdgeType("loop left")
  case object LOOP_RIGHT extends EdgeType("loop right")
  case object BEND_LEFT extends EdgeType("bend left")
  case object BEND_RIGHT extends EdgeType("bend right")
}
