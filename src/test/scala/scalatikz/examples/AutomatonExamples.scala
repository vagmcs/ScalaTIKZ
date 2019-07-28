package scalatikz.examples

import scalatikz.pgf.automata.Automaton
import scalatikz.pgf.enums.Color._

object AutomatonExamples extends App {

  Automaton("simple_automaton")
    .initialState(id = 0, name = "$q_0$")
    .state(id = 1, name = "$q_1$").aboveRightOf(0)
    .state(id = 2, name = "$q_2$").belowRightOf(0)
    .acceptingState(id = 3, name = "$q_3$").belowRightOf(1)
    .edge(0, 1, "0").straight
    .edge(1, 1, "0").loopAbove
    .edge(1, 3, "1").straight
    .edge(0, 2, "1").straight
    .edge(2, 2, "1").loopBelow
    .edge(2, 3, "0").straight
    .saveAsPNG("images/automaton")

  Automaton("light_blue_automaton")
    .initialState(drawColor = BLUE!50, fillColor = BLUE!20)(id = 0, name = "$q_0$")
    .state(drawColor = BLUE!50, fillColor = BLUE!20)(id = 1, name = "$q_1$").aboveRightOf(0)
    .state(drawColor = BLUE!50, fillColor = BLUE!20)(id = 2, name = "$q_2$").belowRightOf(0)
    .edge(0, 1, "0").straight
    .edge(1, 1, "0").loopAbove
    .edge(0, 2, "1").straight
    .edge(2, 2, "1").loopBelow
    .saveAsPNG("images/automaton")

  Automaton("colorful_automaton")
    .initialState(drawColor = RED, fillColor = RED, textColor = WHITE)(id = 0, name = "$q_0$")
    .state(drawColor = ORANGE, fillColor = ORANGE, textColor = WHITE)(id = 1, name = "$q_1$")
    .aboveRightOf(0)
    .state(drawColor = ORANGE, fillColor = ORANGE, textColor = WHITE)(id = 2, name = "$q_2$")
    .belowRightOf(0)
    .acceptingState(drawColor = GREEN!50!BLACK, fillColor = GREEN!50!BLACK, textColor = WHITE)(id = 3, name = "$q_3$")
    .belowRightOf(1)
    .edge(0, 1, "0").straight
    .edge(1, 1, "0").loopAbove
    .edge(1, 3, "1").straight
    .edge(0, 2, "1").straight
    .edge(2, 2, "1").loopBelow
    .edge(2, 3, "0").straight
    .saveAsPNG("images/automaton")
}
