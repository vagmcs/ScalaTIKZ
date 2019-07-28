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

sealed class Color(override val entryName: String) extends EnumEntry {
  override def toString: String = entryName
}

object Color extends Enum[Color] {

  val values: IndexedSeq[Color] = findValues

  case object RED extends Color("red")
  case object BLUE extends Color("blue")
  case object GREEN extends Color("green")
  case object YELLOW extends Color("yellow")
  case object BROWN extends Color("brown")
  case object CYAN extends Color("cyan")
  case object MAGENTA extends Color("magenta")
  case object ORANGE extends Color("orange")
  case object PINK extends Color("pink")
  case object PURPLE extends Color("purple")
  case object VIOLET extends Color("violet")
  case object TEAL extends Color("teal")
  case object LIME extends Color("lime")
  case object OLIVE extends Color("olive")
  case object BLACK extends Color("black")
  case object GRAY extends Color("gray")
  case object LIGHT_GRAY extends Color("lightgray")
  case object WHITE extends Color("white")

  implicit class ColorMix(val color: Color) extends AnyVal {

    def !(that: Color): Color =
      if (color.toString matches ".*\\d+") new Color(color + "!" + that)
      else new Color(color + "!50!" + that)

    def !(percentage: Int): Color =
      if (percentage < 0) new Color(color + "!0")
      else if (percentage > 100) new Color(color + "!100")
      else new Color(color + "!" + percentage)
  }
}
