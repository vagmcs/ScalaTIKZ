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

import scalatikz.pgf.automata.enums.EdgeType
import scalatikz.pgf.automata.enums.EdgeType._
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }

/**
  * Creates an automaton edge connecting a pair of states.
  *
  * @see [[scalatikz.pgf.automata.State]]
  *
  * @param from starting state id
  * @param to ending state id
  * @param condition edge condition
  * @param edgeType edge type (loopy, straight or bended)
  * @param drawColor edge color
  * @param textColor text color
  * @param lineStyle line style
  * @param lineSize line size
  */
case class Edge(
    from: Int, to: Int,
    condition: String,
    edgeType: EdgeType,
    drawColor: Color,
    textColor: Color,
    lineStyle: LineStyle,
    lineSize: LineSize) {

  override def toString: String =
    raw"""
         |\path[
         |  ->,
         |  draw=$drawColor,
         |  text=$textColor,
         |  $lineStyle,
         |  $lineSize
         |] ($from) [$edgeType] edge node {$condition} (${if (to < 0) "" else to});
    """.stripMargin
}

case class EdgeConf private[automata] (a: Automaton) {

  /**
    * @return a straight edge
    */
  def straight: Automaton = a

  /**
    * @return a left bended edge
    */
  def bendLeft: Automaton =
    a.copy(edges = a.edges.head.copy(edgeType = BEND_LEFT) :: a.edges.tail)

  /**
    * @return a right bended edge
    */
  def bendRight: Automaton =
    a.copy(edges = a.edges.head.copy(edgeType = BEND_RIGHT) :: a.edges.tail)

  /**
    * @return a loop edge above the state
    */
  def loopAbove: Automaton =
    a.copy(edges = a.edges.head.copy(to       = 0, edgeType = LOOP_ABOVE) :: a.edges.tail)

  /**
    * @return a loop edge below the state
    */
  def loopBelow: Automaton =
    a.copy(edges = a.edges.head.copy(to       = 0, edgeType = LOOP_BELOW) :: a.edges.tail)

  /**
    * @return a loop edge left of the state
    */
  def loopLeft: Automaton =
    a.copy(edges = a.edges.head.copy(to       = 0, edgeType = LOOP_LEFT) :: a.edges.tail)

  /**
    * @return a loop edge right of the state
    */
  def loopRight: Automaton =
    a.copy(edges = a.edges.head.copy(to       = 0, edgeType = LOOP_RIGHT) :: a.edges.tail)
}
