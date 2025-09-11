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
