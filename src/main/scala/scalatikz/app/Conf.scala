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

package scalatikz.app

import scalatikz.app.GraphType.GraphType
import scalatikz.pgf.Compiler
import scalatikz.pgf.plots.Figure
import scalatikz.pgf.plots.enums._

trait OptionConf

final case class Conf(
    output: String = System.getProperty("user.dir"),
    format: String = "PDF",
    compiler: Compiler = Compiler.PDF_LATEX,
    figure: Figure = Figure(DEFAULT_NAME),
    inputs: IndexedSeq[String] = IndexedSeq.empty,
    delimiters: IndexedSeq[Char] = IndexedSeq.empty,
    graphics: Seq[GraphicConf] = Seq.empty) extends OptionConf

final case class GraphicConf(
    graph: GraphType,
    xColumn: Option[String] = None,
    yColumn: Option[String] = None,
    xErrorColumn: Option[String] = None,
    yErrorColumn: Option[String] = None,
    lineColor: Option[Color] = None,
    marker: Option[Mark] = None,
    markStrokeColor: Option[Color] = None,
    markFillColor: Option[Color] = None,
    markSize: Double = 1,
    lineStyle: Option[LineStyle] = None,
    lineSize: Option[LineSize] = None,
    pattern: Option[Pattern] = None,
    barWidth: Double = .5,
    opacity: Double = .5,
    smooth: Boolean = false,
    constant: Boolean = false)
