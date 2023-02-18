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

package scalatikz.pgf.automata

import scalatikz.pgf.automata.enums.StatePos._
import scalatikz.pgf.automata.enums.{ StatePos, StateType }
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }

/**
 * Creates an automaton state.
 *
 * @param id state id
 * @param name state name
 * @param stateType state type (internal, initial or accepting)
 * @param statePos state relative position (optional)
 * @param relativeId relative state id (optional)
 * @param output state output (optional)
 * @param drawColor state color
 * @param fillColor state fill color
 * @param textColor text color
 * @param lineStyle line style
 * @param lineSize line size
 */
case class State(
  id: Int,
  name: String,
  stateType: StateType,
  statePos: Option[StatePos],
  relativeId: Option[Int],
  output: Option[String],
  drawColor: Color,
  fillColor: Color,
  textColor: Color,
  lineStyle: LineStyle,
  lineSize: LineSize
) {

  require(id > -1, "State ID cannot be negative.")

  override def toString: String = raw"""
         |\node[
         |  $stateType${output.map(_ => " with output").getOrElse("")},
         |  draw=$drawColor,
         |  fill=$fillColor,
         |  text=$textColor,
         |  $lineStyle,
         |  $lineSize
         |] ($id) [${statePos.map(x => s"$x of=${relativeId.getOrElse("")}").getOrElse("")}]
         |{$name${output.map(x => s" \\${"node" + "part"}{lower} $x").getOrElse("")}};
    """.stripMargin
}

case class StateConf private[automata] (a: Automaton) {

  /**
   * @param id a state id
   * @return an automaton having this state above of the given state
   */
  def aboveOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(ABOVE), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state above and left of the given state
   */
  def aboveLeftOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(ABOVE_LEFT), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state above and right of the given state
   */
  def aboveRightOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(ABOVE_RIGHT), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state below of the given state
   */
  def belowOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(BELOW), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state below and left of the given state
   */
  def belowLeftOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(BELOW_LEFT), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state below and right of the given state
   */
  def belowRightOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(BELOW_RIGHT), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state left of the given state
   */
  def leftOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(LEFT), relativeId = Some(id)) :: a.states.tail)

  /**
   * @param id a state id
   * @return an automaton having this state right of the given state
   */
  def rightOf(id: Int): Automaton =
    a.copy(states = a.states.head.copy(statePos = Some(RIGHT), relativeId = Some(id)) :: a.states.tail)
}
