package scalatikz.pgf.plots.enums

sealed abstract class AxisScale(val entryName: String) {
  override def toString: String = entryName
}

object AxisScale {

  val values: IndexedSeq[AxisScale] = IndexedSeq(
    LINEAR,
    LOG
  )

  def withName(name: String): AxisScale = values.find(_.entryName == name).get

  case object LINEAR extends AxisScale("linear")
  case object LOG extends AxisScale("log")
}
