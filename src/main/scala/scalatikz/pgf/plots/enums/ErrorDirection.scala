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

sealed abstract class ErrorDirection(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object ErrorDirection extends Enum[ErrorDirection] {

  val values: IndexedSeq[ErrorDirection] = findValues

  case object NONE extends ErrorDirection("none")
  case object PLUS extends ErrorDirection("plus")
  case object MINUS extends ErrorDirection("minus")
  case object BOTH extends ErrorDirection("both")
}
