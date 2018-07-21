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

sealed abstract class FontSize(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object FontSize extends Enum[FontSize] {

  val values: IndexedSeq[FontSize] = findValues

  case object TINY extends FontSize("tiny")
  case object SCRIPT extends FontSize("script" + "size")
  case object FOOTNOTE extends FontSize("footnote" + "size")
  case object SMALL extends FontSize("small")
  case object NORMAL extends FontSize("normal" + "size")
  case object LARGE extends FontSize("large")
  case object VERY_LARGE extends FontSize("LARGE")
  case object HUGE extends FontSize("huge")
  case object VERY_HUGE extends FontSize("HUGE")
}
