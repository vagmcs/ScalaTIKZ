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

sealed abstract class ColorMap(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object ColorMap extends Enum[ColorMap] {

  val values: IndexedSeq[ColorMap] = findValues

  case object ViRiDiS extends ColorMap("vi" + "ri" + "dis")
  case object HOT extends ColorMap("hot")
  case object HOTTER extends ColorMap("hot2")
  case object JET extends ColorMap("jet")
  case object COOL extends ColorMap("cool")
  case object BLUE_RED extends ColorMap("blue" + "red")
  case object GREEN_YELLOW extends ColorMap("green" + "yellow")
  case object RED_YELLOW extends ColorMap("red" + "yellow")
  case object VIOLET extends ColorMap("violet")
  case object BLACK_WHITE extends ColorMap("black" + "white")
}
