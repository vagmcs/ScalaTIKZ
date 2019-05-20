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

sealed abstract class LineType(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object LineType extends Enum[LineType] {

  val values: IndexedSeq[LineType] = findValues

  case object SHARP extends LineType("sharp plot")
  case object CONST extends LineType("const plot")
  case object SMOOTH extends LineType("smooth")
}
