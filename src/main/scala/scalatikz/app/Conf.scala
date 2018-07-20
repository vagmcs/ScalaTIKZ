/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A plot library for Scala.
 *     
 */

package scalatikz.app

import scalatikz.app.GraphType.GraphType
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.Figure
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Mark.Mark

trait OptionConf

final case class Conf(output: String = System.getProperty("user.dir"),
                      format: String = "PDF",
                      figure: Figure = Figure(DEFAULT_NAME),
                      inputs: IndexedSeq[String] = IndexedSeq.empty,
                      delimiters: IndexedSeq[Char] = IndexedSeq.empty,
                      graphics: Seq[GraphicConf] = Seq.empty) extends OptionConf

final case class GraphicConf(graph: GraphType,
                             xColumn: Option[String] = None,
                             yColumn: Option[String] = None,
                             xErrorColumn: Option[String] = None,
                             yErrorColumn: Option[String] = None,
                             lineColor: Option[Color] = None,
                             marker: Option[Mark] = None,
                             markStrokeColor: Option[Color] = None,
                             markFillColor: Option[Color] = None,
                             markSize: Double = 2,
                             lineStyle: Option[LineStyle] = None,
                             lineSize: Option[LineSize] = None,
                             opacity: Double = 0.5,
                             smooth: Boolean = false,
                             constant: Boolean = false)
