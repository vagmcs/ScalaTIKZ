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

sealed abstract class LineType(val entryName: String) {
  override def toString: String = entryName
}

object LineType {

  val values: IndexedSeq[LineType] = IndexedSeq(SHARP, CONST, SMOOTH)

  def withName(name: String): LineType = values.find(_.entryName == name).get

  case object SHARP extends LineType("sharp plot")
  case object CONST extends LineType("const plot")
  case object SMOOTH extends LineType("smooth")
}
