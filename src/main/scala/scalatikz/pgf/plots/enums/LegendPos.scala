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

sealed class LegendPos(val entryName: String) {

  def this(x: Double, y: Double, anchor: String) = this(s"at={($x, $y)}, anchor=$anchor")

  override def toString: String = entryName
}

object LegendPos {

  val values: IndexedSeq[LegendPos] = IndexedSeq(SOUTH_WEST, SOUTH_EAST, NORTH_WEST, NORTH_EAST, OUTER_NORTH_EAST)

  def withName(name: String): LegendPos = values.find(_.entryName == name).get

  case object SOUTH_WEST extends LegendPos("south west")
  case object SOUTH_EAST extends LegendPos("south east")
  case object NORTH_WEST extends LegendPos("north west")
  case object NORTH_EAST extends LegendPos("north east")
  case object OUTER_NORTH_EAST extends LegendPos("outer north east")
}
