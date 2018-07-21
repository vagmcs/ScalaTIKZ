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

sealed abstract class Mark(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object Mark extends Enum[Mark] {

  val values: IndexedSeq[Mark] = findValues

  case object NONE extends Mark("none")
  case object X extends Mark("x")
  case object DASH extends Mark("-")
  case object DOT extends Mark("*")
  case object CIRCLE extends Mark("o")
  case object STAR extends Mark("star")
  case object PLUS extends Mark("+")
  case object ASTERISK extends Mark("asterisk")
  case object TRIANGLE extends Mark("triangle")
  case object TRIANGLE_FILLED extends Mark("triangle*")
  case object DIAMOND extends Mark("diamond")
  case object DIAMOND_FILLED extends Mark("diamond*")
  case object SQUARE extends Mark("square")
  case object SQUARE_FILLED extends Mark("square*")
  case object PENTAGON extends Mark("pentagon")
  case object PENTAGON_FILLED extends Mark("pentagon*")
  case object CUBE extends Mark("cube")
  case object CUBE_FILLED extends Mark("cube*")
}
