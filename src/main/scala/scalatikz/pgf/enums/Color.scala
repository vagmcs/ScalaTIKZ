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

sealed class Color(val entryName: String) {
  override def toString: String = entryName
}

object Color {

  val values: IndexedSeq[Color] = IndexedSeq(
    RED,
    BLUE,
    GREEN,
    YELLOW,
    BROWN,
    CYAN,
    MAGENTA,
    ORANGE,
    PINK,
    PURPLE,
    VIOLET,
    TEAL,
    LIME,
    OLIVE,
    BLACK,
    GRAY,
    LIGHT_GRAY,
    WHITE
  )

  def withName(name: String): Color = values.find(_.entryName == name).get

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
      if (color.toString.matches(".*\\d+")) new Color(s"$color!$that")
      else new Color(s"$color!50!$that")

    def !(percentage: Int): Color =
      if (percentage < 0) new Color(s"$color!0")
      else if (percentage > 100) new Color(s"$color!100")
      else new Color(s"$color!$percentage")
  }
}
