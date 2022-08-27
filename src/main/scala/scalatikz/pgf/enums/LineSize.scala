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

package scalatikz.pgf.enums

import enumeratum._
import scala.collection.immutable._

sealed abstract class LineSize(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object LineSize extends Enum[LineSize] {

  val values: IndexedSeq[LineSize] = findValues

  case object ULTRA_THIN extends LineSize("ultra thin")
  case object VERY_THIN extends LineSize("very thin")
  case object THIN extends LineSize("thin")
  case object SEMI_THICK extends LineSize("semi" + "thick")
  case object THICK extends LineSize("thick")
  case object VERY_THICK extends LineSize("very thick")
  case object ULTRA_THICK extends LineSize("ultra thick")
}
