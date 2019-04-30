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

package scalatikz.graphics.pgf

import com.github.dwickern.macros.NameOf._
import scalatikz.graphics.pgf.enums.AxisLinePos.BOX
import scalatikz.graphics.pgf.enums.AxisScale.LINEAR
import scalatikz.graphics.pgf.enums.Color.WHITE
import scalatikz.graphics.pgf.enums.LegendPos.OUTER_NORTH_EAST
import scalatikz.graphics.pgf.enums._

/**
  * Axis configuration.
  *
  * @param xMode X axis scale (linear or log)
  * @param yMode Y axis scale (linear or log)
  * @param zMode Z axis scale (linear or log)
  * @param xLabel X axis label
  * @param yLabel Y axis label
  * @param zLabel Z axis label
  * @param xMin X axis lower bound
  * @param xMax X axis upper bound
  * @param yMin Y axis lower bound
  * @param yMax Y axis upper bound
  * @param zMin Z axis lower bound
  * @param zMax Z axis upper bound
  * @param grid axis grid type (minor, major or both)
  * @param colorMap color map type (used for mesh plots)
  * @param backgroundColor axis background color
  * @param header axis header title
  * @param fontSize font size
  * @param legends legends for the plotted data sequences
  * @param legendPos legends position
  * @param xAxisLinePos X axis position
  * @param yAxisLinePos Y axis position
  * @param xAxisHideTicks hide X axis ticks
  * @param yAxisHideTicks hide Y axis ticks
  * @param xTickLabels a sequence of labels for X axis ticks
  * @param yTickLabels a sequence of labels for Y axis ticks
  * @param rotateXTicks rotate X axis ticks by the given degrees
  * @param rotateYTicks rotate Y axis ticks by the given degrees
  */
case class Axis private (
    xMode: AxisScale = LINEAR,
    yMode: AxisScale = LINEAR,
    zMode: AxisScale = LINEAR,
    xLabel: Option[String] = None,
    yLabel: Option[String] = None,
    zLabel: Option[String] = None,
    xMin: Option[Double] = None,
    xMax: Option[Double] = None,
    yMin: Option[Double] = None,
    yMax: Option[Double] = None,
    zMin: Option[Double] = None,
    zMax: Option[Double] = None,
    grid: Option[GridStyle] = None,
    colorMap: Option[ColorMap] = None,
    backgroundColor: Color = WHITE,
    header: Option[String] = None,
    fontSize: Option[FontSize] = None,
    legends: Seq[String] = List.empty,
    legendPos: LegendPos = OUTER_NORTH_EAST,
    xAxisLinePos: AxisLinePos = BOX,
    yAxisLinePos: AxisLinePos = BOX,
    xAxisHideTicks: Boolean = false,
    yAxisHideTicks: Boolean = false,
    xTickLabels: Seq[String] = List.empty,
    yTickLabels: Seq[String] = List.empty,
    rotateXTicks: Int = 0,
    rotateYTicks: Int = 0) {

  override def toString: String = {
    val builder = StringBuilder.newBuilder

    builder ++= s"\t${nameOf(xMode).toLowerCase}=$xMode,\n"
    builder ++= s"\t${nameOf(yMode).toLowerCase}=$yMode,\n"
    builder ++= s"\t${nameOf(zMode).toLowerCase}=$zMode,\n"
    builder ++= s"\taxis background/.style={fill=$backgroundColor},\n"
    builder ++= s"\taxis x line=$xAxisLinePos,\n"
    builder ++= s"\taxis y line=$yAxisLinePos,\n"
    builder ++= s"\tx tick label style={rotate=$rotateXTicks},\n"
    builder ++= s"\ty tick label style={rotate=$rotateYTicks}"

    if (xAxisHideTicks)
      builder ++= s",\n\t${nameOf(xTickLabels).toLowerCase}={,,}"
    else if (xTickLabels.nonEmpty)
      builder ++= s",\n\tx" + s"tick=data,\n${nameOf(xTickLabels).toLowerCase}={${xTickLabels.mkString(",")}}"

    if (yAxisHideTicks)
      builder ++= s",\n\t${nameOf(yTickLabels).toLowerCase}={,,}"
    else if (yTickLabels.nonEmpty)
      builder ++= s",\n\ty" + s"tick=data,\n${nameOf(yTickLabels).toLowerCase}={${yTickLabels.mkString(",")}}"

    if (xLabel.isDefined) builder ++= s",\n\t${nameOf(xLabel).toLowerCase}=${xLabel.get.toTex}"
    if (yLabel.isDefined) builder ++= s",\n\t${nameOf(yLabel).toLowerCase}=${yLabel.get.toTex}"
    if (zLabel.isDefined) builder ++= s",\n\t${nameOf(zLabel).toLowerCase}=${zLabel.get.toTex}"

    if (xMin.isDefined) builder ++= s",\n\t${nameOf(xMin).toLowerCase}=${xMin.get}"
    if (xMax.isDefined) builder ++= s",\n\t${nameOf(xMax).toLowerCase}=${xMax.get}"
    if (yMin.isDefined) builder ++= s",\n\t${nameOf(yMin).toLowerCase}=${yMin.get}"
    if (yMax.isDefined) builder ++= s",\n\t${nameOf(yMax).toLowerCase}=${yMax.get}"
    if (zMin.isDefined) builder ++= s",\n\t${nameOf(zMin).toLowerCase}=${zMin.get}"
    if (zMax.isDefined) builder ++= s",\n\t${nameOf(zMax).toLowerCase}=${zMax.get}"

    if (grid.isDefined) builder ++= s",\n\t${nameOf(grid)}=${grid.get}"
    if (colorMap.isDefined) builder ++= s",\n\tcolormap/${colorMap.get},\n\tcolor" + "bar"
    if (header.isDefined) builder ++ s",\n\ttitle=${header.get.toTex}"
    if (fontSize.isDefined) builder ++= s",\n\tfont=\\${fontSize.get}"
    if (legends.nonEmpty)
      builder ++= s",\n\tlegend entries={${legends.map(_.toTex).mkString(",")}},\n\tlegend pos=$legendPos"

    builder.result
  }
}
