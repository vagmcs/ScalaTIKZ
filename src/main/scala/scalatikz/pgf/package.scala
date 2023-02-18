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

import scala.collection.immutable._
import scala.language.reflectiveCalls

package object pgf {

  final private val SLASH = """\"""
  final private val USE = """use"""
  final private val TIKZ = """tikz"""
  final private val LIBRARY = """library"""
  final private val PACKAGE = """package"""
  final private val PGF_PLOTS = """pgf""" + """plots"""
  final val TIKZLibrary = SLASH + USE + TIKZ + LIBRARY
  final val UsePackage = SLASH + USE + PACKAGE
  final val PGFLibrary = SLASH + USE + PGF_PLOTS + LIBRARY

  sealed abstract class Compiler(val entryName: String) {
    override def toString: String = entryName
  }

  object Compiler {

    val values: IndexedSeq[Compiler] = IndexedSeq(PDF_LATEX, LUA_LATEX)

    def withName(name: String): Compiler = values.find(_.entryName == name).get

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
  def using[R, C <: { def close(): Unit }](closeable: C)(f: C => R): R =
    try f(closeable)
    finally closeable.close()
}
