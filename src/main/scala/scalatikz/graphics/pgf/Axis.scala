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
import scalatikz.graphics.pgf.enums.AxisStyle.LINEAR
import scalatikz.graphics.pgf.enums.Color.WHITE
import scalatikz.graphics.pgf.enums.LegendPos.OUTER_NORTH_EAST
import scalatikz.graphics.pgf.enums._

/**
  * Axis configuration.
  *
  * @see [[scalatikz.graphics.pgf.enums.Color]]
  *      [[scalatikz.graphics.pgf.enums.GridStyle]]
  *      [[scalatikz.graphics.pgf.enums.FontSize]]
  *      [[scalatikz.graphics.pgf.enums.LegendPos]]
  *      [[scalatikz.graphics.pgf.enums.ColorMap]]
  *      [[scalatikz.graphics.pgf.enums.AxisStyle]]
  *      [[scalatikz.graphics.pgf.enums.AxisLinePos]]
  * @param xMode X axis scale (linear or log)
  * @param yMode Y axis scale (linear or log)
  * @param zMode Z axis scale (linear or log)
  * @param xLabel X label
  * @param yLabel Y label
  * @param zLabel Z label
  * @param xMin X lower bound
  * @param xMax X upper bound
  * @param yMin Y lower bound
  * @param yMax Y upper bound
  * @param zMin Z lower bound
  * @param zMax Z upper bound
  * @param grid axis grid type (minor, major or both)
  * @param colorMap color map type
  * @param backgroundColor axis background color
  * @param header axis header
  * @param fontSize font size
  * @param legends legends for the data sequences
  * @param legendPos legend position
  * @param xAxisLinePos X axis position
  * @param yAxisLinePos Y axis position
  * @param xAxisHideTicks hide X axis ticks
  * @param yAxisHideTicks hide Y axis ticks
  */
final case class Axis private (
    xMode: AxisStyle = LINEAR,
    yMode: AxisStyle = LINEAR,
    zMode: AxisStyle = LINEAR,
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
    rotateXTicks: Int = 0,
    rotateYTicks: Int = 0) {

  override def toString: String =
    s"${nameOf(xMode).toLowerCase}=$xMode, ${nameOf(yMode).toLowerCase}=$yMode, " +
      s"${nameOf(zMode).toLowerCase}=$zMode, axis background/.style={fill=$backgroundColor}, " +
      s"axis x line=$xAxisLinePos, axis y line=$yAxisLinePos\n" +
      s"${if (xAxisHideTicks) ", xticklabels={,,}" else ""}" +
      s"${if (yAxisHideTicks) ", yticklabels={,,}" else ""}" +
      s", x tick label style={rotate=$rotateXTicks}" +
      s", y tick label style={rotate=$rotateYTicks}" +
      s"${if (xLabel.isDefined) s" ,${nameOf(xLabel).toLowerCase}=${xLabel.get.toTex}" else ""}" +
      s"${if (yLabel.isDefined) s" ,${nameOf(yLabel).toLowerCase}=${yLabel.get.toTex}" else ""}" +
      s"${if (zLabel.isDefined) s" ,${nameOf(zLabel).toLowerCase}=${zLabel.get.toTex}" else ""}" +
      s"${if (xMin.isDefined) s" ,${nameOf(xMin).toLowerCase}=${xMin.get}" else ""}" +
      s"${if (xMax.isDefined) s" ,${nameOf(xMax).toLowerCase}=${xMax.get}" else ""}" +
      s"${if (yMin.isDefined) s" ,${nameOf(yMin).toLowerCase}=${yMin.get}" else ""}" +
      s"${if (yMax.isDefined) s" ,${nameOf(yMax).toLowerCase}=${yMax.get}" else ""}" +
      s"${if (zMin.isDefined) s" ,${nameOf(zMin).toLowerCase}=${zMin.get}" else ""}" +
      s"${if (zMax.isDefined) s" ,${nameOf(zMax).toLowerCase}=${zMax.get}" else ""}" +
      s"${if (grid.isDefined) s" ,${nameOf(grid)}=${grid.get}" else ""}" +
      s"${if (colorMap.isDefined) s" ,colormap/${colorMap.get}, colorbar" else ""}" +
      s"${if (header.isDefined) s" ,title=${header.get.toTex}" else ""}" +
      s"${if (legends.nonEmpty) s" ,legend entries={${legends.map(_.toTex).mkString(",")}}, legend pos=$legendPos" else ""}" +
      s"${if (fontSize.isDefined) s" ,font=\\${fontSize.get}" else ""}"
}
