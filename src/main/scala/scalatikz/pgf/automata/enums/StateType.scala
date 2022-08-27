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

sealed abstract class StateType(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object StateType extends Enum[StateType] {

  val values: IndexedSeq[StateType] = findValues

  case object INTERNAL extends StateType("state")
  case object INITIAL extends StateType("state, initial")
  case object ACCEPTING extends StateType("state, accepting")
}
