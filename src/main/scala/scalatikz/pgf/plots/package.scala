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

package scalatikz.pgf

package object plots {

  implicit class TeX(val tex: String) extends AnyVal {
    def toTex: String = tex
      .replaceAll("%", "\\\\%")
      .replaceAll("#", "\\\\#")
      .split("\\$")
      .zipWithIndex
      .map {
        case (str, i) =>
          if ((i + 1) % 2 != 0) str
            .replaceAll("_", "\\\\_")
            .replaceAll("&", "\\\\&")
            .replaceAll("~", "\\\\~")
            .replaceAll("\\^", "\\\\^")
          else "$" + str + "$"
      }
      .reduce(_ + _)
  }
}
