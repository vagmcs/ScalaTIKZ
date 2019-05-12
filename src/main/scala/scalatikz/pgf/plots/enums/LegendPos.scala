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

import enumeratum._
import scala.collection.immutable._

sealed abstract class LegendPos(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object LegendPos extends Enum[LegendPos] {

  val values: IndexedSeq[LegendPos] = findValues

  case object SOUTH_WEST extends LegendPos("south west")
  case object SOUTH_EAST extends LegendPos("south east")
  case object NORTH_WEST extends LegendPos("north west")
  case object NORTH_EAST extends LegendPos("north east")
  case object OUTER_NORTH_EAST extends LegendPos("outer north east")
}
