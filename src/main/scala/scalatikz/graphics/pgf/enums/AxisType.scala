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

sealed abstract class AxisType(override val entryName: String) extends EnumEntry {
  override def toString: String = this.entryName
}

object AxisType extends Enum[AxisType] {

  val values: IndexedSeq[AxisType] = findValues

  case object CARTESIAN extends AxisType("axis")
  case object POLAR extends AxisType("polar" + "axis")
}
