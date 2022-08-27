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

package scalatikz.pgf.charts.enums

import enumeratum._
import scala.collection.immutable._

sealed abstract class TextLocation(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object TextLocation extends Enum[TextLocation] {

  val values: IndexedSeq[TextLocation] = findValues

  case object LABEL extends TextLocation("label")
  case object PIN extends TextLocation("pin")
  case object INSIDE extends TextLocation("inside")
  case object LEGEND extends TextLocation("legend")
}
