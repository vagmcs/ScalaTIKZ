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

sealed abstract class GridStyle(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object GridStyle extends Enum[GridStyle] {

  val values: IndexedSeq[GridStyle] = findValues

  case object MINOR extends GridStyle("minor")
  case object MAJOR extends GridStyle("major")
  case object BOTH extends GridStyle("both")
}
