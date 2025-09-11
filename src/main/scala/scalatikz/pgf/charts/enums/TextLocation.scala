package scalatikz.pgf.charts.enums

sealed abstract class TextLocation(val entryName: String) {
  override def toString: String = entryName
}

object TextLocation {

  val values: IndexedSeq[TextLocation] = IndexedSeq(
    LABEL,
    PIN,
    INSIDE,
    LEGEND
  )

  def withName(name: String): TextLocation = values.find(_.entryName == name).get

  case object LABEL extends TextLocation("label")
  case object PIN extends TextLocation("pin")
  case object INSIDE extends TextLocation("inside")
  case object LEGEND extends TextLocation("legend")
}
