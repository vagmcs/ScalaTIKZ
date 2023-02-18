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

import scalatikz.pgf.{ TIKZLibrary, TIKZPicture }
import scalatikz.pgf.automata.enums.EdgeType._
import scalatikz.pgf.automata.enums.StateType
import scalatikz.pgf.enums.Color.BLACK
import scalatikz.pgf.enums.LineSize.THIN
import scalatikz.pgf.enums.LineStyle.SOLID
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }

case class Automaton private (
  override val name: String,
  override protected val nodeDistance: Double,
  private[automata] val states: List[State],
  private[automata] val edges: List[Edge]
) extends TIKZPicture {

  override protected val libraries: String = s"""
      |$TIKZLibrary{automata}
      |""".stripMargin

  override protected val tiKZArgs: List[String] = List("auto")

  /**
   * @param centimeters the node distance in centimeters
   * @return an Automaton having the given node distance
   */
  def havingStateDistance(centimeters: Int): Automaton = copy(nodeDistance = centimeters)

  /**
   * Creates an initial state for the automaton.
   *
   * @param id a state id
   * @param name a state name
   * @return an Automaton having the specified initial state
   */
  def initialState(id: Int, name: String): Automaton = initialState()(id, name)

  /**
   * Creates an initial state for the automaton.
   *
   * @param drawColor state color
   * @param fillColor state fill color
   * @param textColor text color
   * @param lineStyle line style
   * @param lineSize line size
   * @param id a state id
   * @param name a state name
   * @return an Automaton having the specified initial state
   */
  def initialState(
    drawColor: Color = Color.BLACK,
    fillColor: Color = Color.WHITE,
    textColor: Color = Color.BLACK,
    lineStyle: LineStyle = LineStyle.SOLID,
    lineSize: LineSize = LineSize.THICK
  )(id: Int, name: String): Automaton = copy(states =
    State(id, name, StateType.INITIAL, None, None, None, drawColor, fillColor, textColor, lineStyle, lineSize) :: states
  )

  /**
   * Creates an accepting state for the automaton.
   *
   * @param id a state id
   * @param name a state name
   * @return an Automaton having the specified accepting state
   */
  def acceptingState(id: Int, name: String): StateConf = acceptingState()(id, name, None)

  /**
   * Creates an accepting state for the automaton.
   *
   * @param id a state id
   * @param name a state name
   * @param output a state output
   * @return an Automaton having the specified accepting state
   */
  def acceptingState(id: Int, name: String, output: String): StateConf = acceptingState()(id, name, Some(output))

  /**
   * Creates an accepting state for the automaton.
   *
   * @param drawColor state color
   * @param fillColor state fill color
   * @param textColor text color
   * @param lineStyle line style
   * @param lineSize line size
   * @param id a state id
   * @param name a state name
   * @param output a state output
   * @return an Automaton having the specified accepting state
   */
  def acceptingState(
    drawColor: Color = Color.BLACK,
    fillColor: Color = Color.WHITE,
    textColor: Color = Color.BLACK,
    lineStyle: LineStyle = LineStyle.SOLID,
    lineSize: LineSize = LineSize.THICK
  )(id: Int, name: String, output: Option[String] = None): StateConf = StateConf(
    copy(states =
      State(
        id,
        name,
        StateType.ACCEPTING,
        None,
        None,
        output,
        drawColor,
        fillColor,
        textColor,
        lineStyle,
        lineSize
      ) :: states
    )
  )

  /**
   * Creates an internal state for the automaton.
   *
   * @param id a state id
   * @param name a state name
   * @return an Automaton having the specified state
   */
  def state(id: Int, name: String): StateConf = state()(id, name, None)

  /**
   * Creates an internal state for the automaton.
   *
   * @param id a state id
   * @param name a state name
   * @param output a state output
   * @return an Automaton having the specified state
   */
  def state(id: Int, name: String, output: String): StateConf = state()(id, name, Some(output))

  /**
   * Creates an internal state for the automaton.
   *
   * @param drawColor state color
   * @param fillColor state fill color
   * @param textColor text color
   * @param lineStyle line style
   * @param lineSize line size
   * @param id a state id
   * @param name a state name
   * @param output a state output
   * @return an Automaton having the specified state
   */
  def state(
    drawColor: Color = Color.BLACK,
    fillColor: Color = Color.WHITE,
    textColor: Color = Color.BLACK,
    lineStyle: LineStyle = LineStyle.SOLID,
    lineSize: LineSize = LineSize.THICK
  )(id: Int, name: String, output: Option[String] = None): StateConf = StateConf(
    copy(states =
      State(
        id,
        name,
        StateType.INTERNAL,
        None,
        None,
        output,
        drawColor,
        fillColor,
        textColor,
        lineStyle,
        lineSize
      ) :: states
    )
  )

  /**
   * Creates an edge for the automaton.
   *
   * @param from starting state id
   * @param to ending state id
   * @param condition edge condition
   * @return an Automaton having the specified edge
   */
  def edge(from: Int, to: Int, condition: String): EdgeConf = edge()(from, to, condition)

  /**
   * Creates an edge for the automaton.
   *
   * @param drawColor edge color
   * @param textColor text color
   * @param lineStyle line style
   * @param lineSize line size
   * @param from starting state id
   * @param to ending state id
   * @param condition edge condition
   * @return an Automaton having the specified edge
   */
  def edge(
    drawColor: Color = BLACK,
    textColor: Color = BLACK,
    lineStyle: LineStyle = SOLID,
    lineSize: LineSize = THIN
  )(from: Int, to: Int, condition: String): EdgeConf =
    EdgeConf(copy(edges = Edge(from, to, condition, STRAIGHT, drawColor, textColor, lineStyle, lineSize) :: edges))

  override def toString: String = raw"""
         |${states.reverse.mkString("\n")}
         |
         |${edges.reverse.mkString("\n")}
    """.stripMargin
}

object Automaton {

  /**
   * Creates an empty automaton.
   *
   * @param name a name for the automaton
   * @return an Automaton instance
   */
  def apply(name: String): Automaton = new Automaton(name, 2, List.empty, List.empty)
}
