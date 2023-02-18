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

import scalatikz.pgf.enums.Color
import scalatikz.pgf.enums.Color.values

sealed abstract class AxisSystem(val entryName: String) {
  override def toString: String = this.entryName
}

object AxisSystem {

  val values: IndexedSeq[AxisSystem] = IndexedSeq(CARTESIAN, POLAR)

  def withName(name: String): AxisSystem = values.find(_.entryName == name).get

  case object CARTESIAN extends AxisSystem("axis")
  case object POLAR extends AxisSystem("polar" + "axis")
}
