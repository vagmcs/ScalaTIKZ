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

case class State(
    name: String,
    position: Option[StatePos] = None,
    output: Option[String] = None) {

  override def toString: String = {

    raw"""
         |\node[state${if (output.isDefined) s" with output" else ""}] (${name.replaceAll("\\$", "")}) ${position.getOrElse("")} {$name${if (output.isDefined) s"\\nodepart{lower} ${output.get}" else ""}};
    """.stripMargin
  }
}

class InitialState(override val name: String) extends State(name) {

  override def toString: String =
    raw"""
         |\node[state, initial] (${name.replaceAll("\\$", "")}) {$name};
    """.stripMargin
}

class FinalState(override val name: String) extends State(name) {

  override def toString: String =
    raw"""
         |\node[state, accepting] (${name.replaceAll("\\$", "")})${position.getOrElse("")}{$name};
    """.stripMargin
}

case class StatePos(location: String, state: String) {
  override def toString: String = s" [$location of=${state.replaceAll("\\$", "")}] "
}
