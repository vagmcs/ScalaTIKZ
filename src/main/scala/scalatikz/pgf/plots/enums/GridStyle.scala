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

sealed abstract class GridStyle(val entryName: String) {
  override def toString: String = entryName
}

object GridStyle {

  val values: IndexedSeq[GridStyle] = IndexedSeq(MINOR, MAJOR, BOTH)

  def withName(name: String): GridStyle = values.find(_.entryName == name).get
  case object MINOR extends GridStyle("minor")
  case object MAJOR extends GridStyle("major")
  case object BOTH extends GridStyle("both")
}
