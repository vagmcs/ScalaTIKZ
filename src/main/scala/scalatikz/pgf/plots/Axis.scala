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

package scalatikz.pgf.plots

import scalatikz.pgf.enums.Color
import scalatikz.pgf.plots.enums.AxisLinePos.BOX
import scalatikz.pgf.plots.enums.AxisScale.LINEAR
import scalatikz.pgf.enums.Color.WHITE
import scalatikz.pgf.plots.enums.FontSize.NORMAL
import scalatikz.pgf.plots.enums.LegendPos.OUTER_NORTH_EAST
import scalatikz.pgf.plots.enums._

/**
 * Axis configuration.
 *
 * @param height axis height (in centimeters)
 * @param width axis width (in centimeters)
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
 * @param fontSize axis font size
 * @param legends legends for the plotted data sequences
 * @param legendPos legends position
 * @param legendColumns number of legend columns
 * @param legendFontSize legends font size
 * @param xAxisLinePos X axis position
 * @param yAxisLinePos Y axis position
 * @param zAxisLinePos Z axis position
 * @param xAxisHideTicks hide X axis ticks
 * @param yAxisHideTicks hide Y axis ticks
 * @param zAxisHideTicks hide Z axis ticks
 * @param xTickLabels a sequence of labels for X axis ticks
 * @param yTickLabels a sequence of labels for Y axis ticks
 * @param zTickLabels a sequence of labels for Z axis ticks
 * @param rotateXTicks rotate X axis ticks by the given degrees
 * @param rotateYTicks rotate Y axis ticks by the given degrees
 * @param rotateZTicks rotate Z axis ticks by the given degrees
 * @param scaleXTicks scale X axis ticks
 * @param scaleYTicks scale Y axis ticks
 * @param scaleZTicks scale Z axis ticks
 */
case class Axis(
  height: Option[Double] = None,
  width: Option[Double] = None,
  azimuth: Option[Double] = None,
  elevation: Option[Double] = None,
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
  legendColumns: Int = 1,
  legendFontSize: FontSize = NORMAL,
  xAxisLinePos: AxisLinePos = BOX,
  yAxisLinePos: AxisLinePos = BOX,
  zAxisLinePos: AxisLinePos = BOX,
  xAxisHideTicks: Boolean = false,
  yAxisHideTicks: Boolean = false,
  zAxisHideTicks: Boolean = false,
  xTickLabels: Seq[String] = List.empty,
  yTickLabels: Seq[String] = List.empty,
  zTickLabels: Seq[String] = List.empty,
  rotateXTicks: Int = 0,
  rotateYTicks: Int = 0,
  rotateZTicks: Int = 0,
  scaleXTicks: Boolean = false,
  scaleYTicks: Boolean = false,
  scaleZTicks: Boolean = false,
  stackedBars: Boolean = true
) {

  override def toString: String = {
    val builder = new StringBuilder

    if (!stackedBars) builder ++= s"\t${Symbol("yBar").name.toLowerCase},\n"

    if (height.isDefined) builder ++= s"\theight=${height.get}cm,\n"
    if (width.isDefined) builder ++= s"\twidth=${width.get}cm,\n"

    if (azimuth.isDefined || elevation.isDefined)
      builder ++= s"\tview={${azimuth.getOrElse(25)}{${elevation.getOrElse(30)}}"

    builder ++= s"\t${Symbol("xMode").name.toLowerCase}=$xMode,\n"
    builder ++= s"\t${Symbol("yMode").name.toLowerCase}=$yMode,\n"
    builder ++= s"\t${Symbol("zMode").name.toLowerCase}=$zMode,\n"
    builder ++= s"\taxis background/.style={fill=$backgroundColor},\n"
    builder ++= s"\taxis x line=$xAxisLinePos,\n"
    builder ++= s"\taxis y line=$yAxisLinePos,\n"
    builder ++= s"\taxis z line=$zAxisLinePos,\n"
    builder ++= s"\tx tick label style={rotate=$rotateXTicks},\n"
    builder ++= s"\ty tick label style={rotate=$rotateYTicks},\n"
    builder ++= s"\tz tick label style={rotate=$rotateZTicks}"

    if (xAxisHideTicks) builder ++= s",\n\t${Symbol("xTickLabels").name.toLowerCase}={,,}"
    else if (xTickLabels.nonEmpty)
      builder ++= s",\n\tx" + s"tick=data,\n${Symbol("xTickLabels").name.toLowerCase}={${xTickLabels.mkString(",")}}"

    if (yAxisHideTicks) builder ++= s",\n\t${Symbol("yTickLabels").name.toLowerCase}={,,}"
    else if (yTickLabels.nonEmpty)
      builder ++= s",\n\ty" + s"tick=data,\n${Symbol("yTickLabels").name.toLowerCase}={${yTickLabels.mkString(",")}}"

    if (zAxisHideTicks) builder ++= s",\n\t${"zTickLabels".toLowerCase}={,,}"
    else if (yTickLabels.nonEmpty)
      builder ++= s",\n\tz" + s"tick=data,\n${"zTickLabels".toLowerCase}={${yTickLabels.mkString(",")}}"

    if (scaleXTicks) builder ++= s",\n\tscaled x ticks = true"
    else builder ++= s",\n\tscaled x ticks = false"

    if (scaleYTicks) builder ++= s",\n\tscaled y ticks = true"
    else builder ++= s",\n\tscaled y ticks = false"

    if (scaleZTicks) builder ++= s",\n\tscaled y ticks = true"
    else builder ++= s",\n\tscaled y ticks = false"

    if (xLabel.isDefined) builder ++= s",\n\t${Symbol("xLabel").name.toLowerCase}=${xLabel.get.toTex}"
    if (yLabel.isDefined) builder ++= s",\n\t${Symbol("yLabel").name.toLowerCase}=${yLabel.get.toTex}"
    if (zLabel.isDefined) builder ++= s",\n\t${Symbol("zLabel").name.toLowerCase}=${zLabel.get.toTex}"

    if (xMin.isDefined) builder ++= s",\n\t${Symbol("xMin").name.toLowerCase}=${xMin.get}"
    if (xMax.isDefined) builder ++= s",\n\t${Symbol("xMax").name.toLowerCase}=${xMax.get}"
    if (yMin.isDefined) builder ++= s",\n\t${Symbol("yMin").name.toLowerCase}=${yMin.get}"
    if (yMax.isDefined) builder ++= s",\n\t${Symbol("yMax").name.toLowerCase}=${yMax.get}"
    if (zMin.isDefined) builder ++= s",\n\t${Symbol("zMin").name.toLowerCase}=${zMin.get}"
    if (zMax.isDefined) builder ++= s",\n\t${Symbol("zMax").name.toLowerCase}=${zMax.get}"

    if (grid.isDefined) builder ++= s",\n\tgrid=${grid.get}"
    if (colorMap.isDefined) builder ++= s",\n\tcolormap/${colorMap.get},\n\tcolor" + "bar"
    if (header.isDefined) builder ++= s",\n\ttitle=${header.get.toTex}"
    if (fontSize.isDefined) builder ++= s",\n\tfont=\\${fontSize.get}"
    if (legends.nonEmpty) {
      builder ++= s",\n\tlegend entries={${legends.map(_.toTex).mkString(",")}}"
      if (LegendPos.values.contains(legendPos)) {
        builder ++= s",\n\tlegend pos=$legendPos"
        builder ++= s",\n\tlegend style={font=\\$legendFontSize}"
      } else builder ++= s",\n\tlegend style={$legendPos,font=\\$legendFontSize}"
      builder ++= s",\n\tlegend columns=$legendColumns"
    }

    builder.result
  }
}
