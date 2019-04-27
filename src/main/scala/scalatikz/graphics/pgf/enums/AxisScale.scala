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

sealed abstract class AxisScale(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object AxisScale extends Enum[AxisScale] {

  val values: IndexedSeq[AxisScale] = findValues

  case object LINEAR extends AxisScale("linear")
  case object LOG extends AxisScale("log")
}
