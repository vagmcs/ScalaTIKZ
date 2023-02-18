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

sealed abstract class FontSize(val entryName: String) {
  override def toString: String = entryName
}

object FontSize {

  val values: IndexedSeq[FontSize] =
    IndexedSeq(TINY, SCRIPT, FOOTNOTE, SMALL, NORMAL, LARGE, VERY_LARGE, HUGE, VERY_HUGE)

  def withName(name: String): FontSize = values.find(_.entryName == name).get
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
