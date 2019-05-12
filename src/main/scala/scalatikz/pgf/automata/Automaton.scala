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

import scalatikz.pgf.TIKZPicture

case class Automaton private (
    override val name: String,
    private[automata] val states: List[State],
    private[automata] val edges: List[Edge]) extends TIKZPicture {

  def state(name: String, output: Option[String] = None): StateConf =
    StateConf(this.copy(states = State(name, None, output) :: states))

  def acceptingState(name: String, output: Option[String] = None): StateConf =
    StateConf(this.copy(states = new FinalState(name) :: states))

  def edge(from: String, to: String, label: String): EdgeConf =
    EdgeConf(this.copy(edges = Edge(from, to, label, "") :: edges))

  override def toString: String =
    raw"""
         |${states.reverse.mkString("\n")}
         |${edges.reverse.mkString("\n")}
    """.stripMargin
}

object Automaton {

  /**
    * @param name the name of the automaton
    * @param initialState the name of the initial state
    * @return an Automaton instance
    */
  def apply(name: String)(initialState: String): Automaton =
    new Automaton(name, List(new InitialState(initialState)), List.empty)
}

case class StateConf private[automata] (a: Automaton) {

  def aboveOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("above", state))) :: a.states.tail)

  def aboveLeftOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("above left", state))) :: a.states.tail)

  def aboveRightOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("above right", state))) :: a.states.tail)

  def belowOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("below", state))) :: a.states.tail)

  def belowLeftOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("below left", state))) :: a.states.tail)

  def belowRightOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("below right", state))) :: a.states.tail)

  def leftOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("left", state))) :: a.states.tail)

  def rightOf(state: String): Automaton =
    a.copy(states = a.states.head.copy(position = Some(StatePos("right", state))) :: a.states.tail)

  def havingLoopAbove(label: String): StateConf =
    StateConf(a.copy(edges = new LoopAbove(s"${a.states.head.name}", label) :: a.edges))

  def havingLoopBelow(label: String): StateConf =
    StateConf(a.copy(edges = new LoopBelow(s"${a.states.head.name}", label) :: a.edges))

  def havingLoopLeft(label: String): StateConf =
    StateConf(a.copy(edges = new LoopRight(s"${a.states.head.name}", label) :: a.edges))

  def havingLoopRight(label: String): StateConf =
    StateConf(a.copy(edges = new LoopRight(s"${a.states.head.name}", label) :: a.edges))
}

case class EdgeConf private[automata] (a: Automaton) {

  def straight: Automaton = a

  def bendLeft: Automaton =
    a.copy(edges = a.edges.head.copy(conf = "bend left") :: a.edges.tail)

  def bendRight: Automaton =
    a.copy(edges = a.edges.head.copy(conf = "bend right") :: a.edges.tail)
}
