package scalatikz.pgf.automata.enums

sealed abstract class StateType(val entryName: String) {
  override def toString: String = entryName
}

object StateType {

  val values: IndexedSeq[StateType] = IndexedSeq(INTERNAL, INITIAL, ACCEPTING)

  def withName(name: String): StateType = values.find(_.entryName == name).get

  case object INTERNAL extends StateType("state")
  case object INITIAL extends StateType("state, initial")
  case object ACCEPTING extends StateType("state, accepting")
}
