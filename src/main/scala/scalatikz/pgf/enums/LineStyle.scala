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

sealed abstract class LineStyle(val entryName: String) {
  override def toString: String = entryName
}

object LineStyle {

  val values: IndexedSeq[LineStyle] = IndexedSeq(
    SOLID,
    DOTTED,
    DENSELY_DOTTED,
    LOOSELY_DOTTED,
    DASHED,
    DENSELY_DASHED,
    LOOSELY_DASHED,
    DASH_DOTTED,
    DENSELY_DASH_DOTTED,
    LOOSELY_DASH_DOTTED,
    DASH_DOT_DOTTED,
    DENSELY_DASH_DOT_DOTTED,
    LOOSELY_DASH_DOT_DOTTED
  )

  def withName(name: String): LineStyle = values.find(_.entryName == name).get

  case object SOLID extends LineStyle("solid")
  case object DOTTED extends LineStyle("dotted")
  case object DENSELY_DOTTED extends LineStyle("densely dotted")
  case object LOOSELY_DOTTED extends LineStyle("loosely dotted")
  case object DASHED extends LineStyle("dashed")
  case object DENSELY_DASHED extends LineStyle("densely dashed")
  case object LOOSELY_DASHED extends LineStyle("loosely dashed")
  case object DASH_DOTTED extends LineStyle("dash" + "dotted")
  case object DENSELY_DASH_DOTTED extends LineStyle("densely dash" + "dotted")
  case object LOOSELY_DASH_DOTTED extends LineStyle("loosely dash" + "dotted")
  case object DASH_DOT_DOTTED extends LineStyle("dash" + "dot" + "dotted")
  case object DENSELY_DASH_DOT_DOTTED extends LineStyle("dash" + "dot" + "dotted")
  case object LOOSELY_DASH_DOT_DOTTED extends LineStyle("dash" + "dot" + "dotted")
}
