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
