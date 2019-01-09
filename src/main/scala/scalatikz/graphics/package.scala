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

package object graphics {

  sealed abstract class Compiler(override val entryName: String) extends EnumEntry {
    override def toString: String = entryName
  }

  object Compiler extends Enum[Compiler] {

    val values: IndexedSeq[Compiler] = findValues

    case object PDFLATEX extends Compiler("pdflatex")
    case object LUALATEX extends Compiler("lualatex")
  }
}
