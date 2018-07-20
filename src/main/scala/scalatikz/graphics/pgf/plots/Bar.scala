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

package scalatikz.graphics.pgf.plots

import scalatikz.graphics.PGFPlot
import scalatikz.graphics.pgf.Color.Color
import scalatikz.graphics.pgf.DataTypes.Coordinates
import scalatikz.graphics.pgf.LineSize.LineSize
import scalatikz.graphics.pgf.LineStyle.LineStyle
import scalatikz.graphics.pgf.Pattern._

final class xBar private (
    coordinates: Coordinates,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[xbar, $lineStyle, $lineSize, bar width=$barWidth, opacity=$opacity,
         | ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"color=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
  """.stripMargin
}

final class yBar private (
    coordinates: Coordinates,
    color: Color,
    pattern: Pattern,
    lineStyle: LineStyle,
    lineSize: LineSize,
    opacity: Double,
    barWidth: Double) extends PGFPlot {

  override def toString: String =
    raw"""
         | \addplot[ybar, $lineStyle, $lineSize, bar width = $barWidth,
         | ${if (pattern != PLAIN) s"pattern=$pattern, pattern color=$color" else s"color=$color"}] coordinates {
         |   ${coordinates.mkString("\n")}
         | };
  """.stripMargin
}

private[graphics] object xBar {

  def apply(
      coordinates: Coordinates,
      color: Color,
      pattern: Pattern,
      lineStyle: LineStyle,
      lineSize: LineSize,
      opacity: Double,
      barWidth: Double): xBar =
    new xBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}

private[graphics] object yBar {

  def apply(
      coordinates: Coordinates,
      color: Color,
      pattern: Pattern,
      lineStyle: LineStyle,
      lineSize: LineSize,
      opacity: Double,
      barWidth: Double): yBar =
    new yBar(coordinates, color, pattern, lineStyle, lineSize, opacity, barWidth)
}
