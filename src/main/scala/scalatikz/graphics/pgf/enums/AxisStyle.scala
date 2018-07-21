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

sealed abstract class AxisStyle(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object AxisStyle extends Enum[AxisStyle] {

  val values: IndexedSeq[AxisStyle] = findValues

  case object LINEAR extends AxisStyle("linear")
  case object LOG extends AxisStyle("log")
}
