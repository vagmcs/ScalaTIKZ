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
  case object HALF_CIRCLE extends Mark("half" + "circle")
  case object HALF_CIRCLE_FILLED extends Mark("half" + "circle*")
  case object STAR extends Mark("star")
  case object TEN_POINTED_STAR extends Mark("10-pointed star")
  case object PLUS extends Mark("+")
  case object O_PLUS extends Mark("o" + "plus")
  case object O_PLUS_FILLED extends Mark("o" + "plus*")
  case object O_TIMES extends Mark("o" + "times")
  case object O_TIMES_FILLED extends Mark("o" + "times*")
  case object ASTERISK extends Mark("asterisk")
  case object TRIANGLE extends Mark("triangle")
  case object TRIANGLE_FILLED extends Mark("triangle*")
  case object DIAMOND extends Mark("diamond")
  case object DIAMOND_FILLED extends Mark("diamond*")
  case object HALF_DIAMOND extends Mark("half" + "diamond*")
  case object SQUARE extends Mark("square")
  case object SQUARE_FILLED extends Mark("square*")
  case object HALF_SQUARE extends Mark("half" + "square*")
  case object HALF_SQUARE_LEFT extends Mark("half" + "square left*")
  case object HALF_SQUARE_RIGHT extends Mark("half" + "square right*")
  case object PENTAGON extends Mark("pentagon")
  case object PENTAGON_FILLED extends Mark("pentagon*")
  case object CUBE extends Mark("cube")
  case object CUBE_FILLED extends Mark("cube*")
  case object BALL extends Mark("ball")
}
