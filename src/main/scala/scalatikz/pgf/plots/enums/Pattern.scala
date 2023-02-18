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

sealed abstract class Pattern(val entryName: String) {
  override def toString: String = entryName
}

object Pattern {

  val values: IndexedSeq[Pattern] = IndexedSeq(
    PLAIN,
    HORIZONTAL_LINES,
    VERTICAL_LINES,
    NORTH_EAST_LINES,
    NORTH_WEST_LINES,
    GRID,
    CROSSHATCH,
    DOTS,
    CROSSHATCH_DOTS,
    BRICKS,
    FIVE_POINTED_STARS,
    SIX_POINTED_STARS
  )

  def withName(name: String): Pattern = values.find(_.entryName == name).get

  case object PLAIN extends Pattern("plain")
  case object HORIZONTAL_LINES extends Pattern("horizontal lines")
  case object VERTICAL_LINES extends Pattern("vertical lines")
  case object NORTH_EAST_LINES extends Pattern("north east lines")
  case object NORTH_WEST_LINES extends Pattern("north west lines")
  case object GRID extends Pattern("grid")
  case object CROSSHATCH extends Pattern("crosshatch")
  case object DOTS extends Pattern("dots")
  case object CROSSHATCH_DOTS extends Pattern("crosshatch dots")
  case object BRICKS extends Pattern("bricks")
  case object FIVE_POINTED_STARS extends Pattern("five" + "pointed stars")
  case object SIX_POINTED_STARS extends Pattern("six" + "pointed stars")
}
