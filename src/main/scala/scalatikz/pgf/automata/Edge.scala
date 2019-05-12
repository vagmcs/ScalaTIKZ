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

case class Edge(
    from: String,
    to: String,
    label: String,
    conf: String) {

  override def toString: String = {
    val builder = StringBuilder.newBuilder

    builder ++= s"""\path[->] (${from.replaceAll("\\$", "")})"""
    if (conf.nonEmpty) builder ++= s"[$conf]"
    builder ++= s"""edge node {$label} (${to.replaceAll("\\$", "")});"""
    builder.result
  }
}

class LoopAbove(
    state: String,
    label: String) extends Edge(state, "", label, "loop above")

class LoopBelow(
    state: String,
    label: String) extends Edge(state, "", label, "loop below")

class LoopLeft(
    state: String,
    label: String) extends Edge(state, "", label, "loop left")

class LoopRight(
    state: String,
    label: String) extends Edge(state, "", label, "loop right")
