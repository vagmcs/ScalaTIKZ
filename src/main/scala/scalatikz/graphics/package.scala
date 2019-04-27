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

package scalatikz

import enumeratum._
import scala.collection.immutable._
import scala.language.reflectiveCalls

package object graphics {

  sealed abstract class Compiler(override val entryName: String) extends EnumEntry {
    override def toString: String = entryName
  }

  object Compiler extends Enum[Compiler] {

    val values: IndexedSeq[Compiler] = findValues

    case object PDF_LATEX extends Compiler("pdflatex")
    case object LUA_LATEX extends Compiler("lua" + "latex")
  }

  /**
    * Uses an object that can be closed (e.g. BufferedSource) and
    * applies a function to get a result. Finally it closes the source.
    *
    * @param closeable a closable instance
    * @param f a function
    * @tparam C type of the closable
    * @tparam R type of the return value
    * @return a result from the function
    */
  def using[R, C <: { def close(): Unit }] (closeable: C) (f: C => R): R =
    try { f(closeable) } finally { closeable.close() }
}
