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

sealed abstract class LineSize(val entryName: String) {
  override def toString: String = entryName
}

object LineSize {

  val values: IndexedSeq[LineSize] = IndexedSeq(
    ULTRA_THIN,
    VERY_THIN,
    THIN,
    SEMI_THICK,
    THICK,
    VERY_THICK,
    ULTRA_THICK
  )

  def withName(name: String): LineSize = values.find(_.entryName == name).get

  case object ULTRA_THIN extends LineSize("ultra thin")
  case object VERY_THIN extends LineSize("very thin")
  case object THIN extends LineSize("thin")
  case object SEMI_THICK extends LineSize("semi" + "thick")
  case object THICK extends LineSize("thick")
  case object VERY_THICK extends LineSize("very thick")
  case object ULTRA_THICK extends LineSize("ultra thick")
}
