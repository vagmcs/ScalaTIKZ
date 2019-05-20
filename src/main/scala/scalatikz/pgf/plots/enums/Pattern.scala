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

sealed abstract class Pattern(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object Pattern extends Enum[Pattern] {

  val values: IndexedSeq[Pattern] = findValues

  case object PLAIN extends Pattern("plain")
  case object HORIZONTAL_LINES extends Pattern("horizontal lines")
  case object VERTICAL_LINES extends Pattern("vertical lines")
  case object NORTH_EAST_LINES extends Pattern("north east lines")
  case object NORTH_WEST_LINES extends Pattern("north west lines")
  case object GRID extends Pattern("grid")
  case object CROSSHATCH extends Pattern("crosshatch")
  case object DOTS extends Pattern("dots")
  case object CROSSHATCH_DOTS extends Pattern("crosshatch dots")
  case object BRICKS extends Pattern("bricks")
  case object FIVE_POINTED_STARS extends Pattern("five" + "pointed stars")
  case object SIX_POINTED_STARS extends Pattern("six" + "pointed stars")
}
