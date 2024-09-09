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

package scalatikz.examples

import math._
import scala.util.Random
import scalatikz.pgf.plots.Figure
import scalatikz.pgf.plots.enums.AxisLinePos.{ BOTTOM, LEFT }
import scalatikz.pgf.enums.Color._
import scalatikz.pgf.plots.enums.ColorMap
import scalatikz.pgf.plots.enums.FontSize.{ FOOTNOTE, LARGE }
import scalatikz.pgf.plots.enums.LegendPos.{ NORTH_EAST, SOUTH_WEST }
import scalatikz.pgf.enums.LineSize.{ VERY_THICK, VERY_THIN }
import scalatikz.pgf.enums.LineStyle.DASHED
import scalatikz.pgf.plots.enums.LineType.SMOOTH
import scalatikz.pgf.plots.enums.Mark.{ CIRCLE, DOT }
import scalatikz.pgf.plots.enums.Pattern.CROSSHATCH

object PGFPlotExamples extends App {

  /*
   * Bars for the function y = x^2
   */
  Figure("bar")
    .havingAdjacentBars
    .bar(barColor = CYAN ! 75 ! BLACK, pattern = CROSSHATCH)((0 to 5).map(x => x -> x * x))
    .bar(barColor = GREEN ! 75 ! BLACK, lineSize = VERY_THICK, opacity = 0)((0 to 5).map(x => x -> (5 - x) * (5 - x)))
    .havingYLimits(0, 30)
    .havingFontSize(LARGE)
    .havingAxisXLabels(Seq("A", "B", "C", "D", "E", "F"))
    .saveAsPNG("images")

  /*
   * Sine vs Cosine plot
   */
  val domain = BigDecimal(-2 * Pi) to BigDecimal(2 * Pi) by 0.1

  Figure("sine_vs_cosine")
    .plot(domain -> sin _)
    .plot(lineStyle = DASHED)(domain -> cos _)
    .havingLegends("$\\sin(x)$", "$\\cos(x)$")
    .havingLegendPos(SOUTH_WEST)
    .havingXLabel("$x$")
    .havingYLabel("$y$")
    .havingTitle("$\\sin(x)$ vs $\\cos(x)$")
    .havingFontSize(LARGE)
    .saveAsPNG("images")

  /*
   * Gaussian distributions
   */
  def gaussian(mean: Double, variance: Double)(x: Double): Double =
    1 / sqrt(2 * Pi * variance) * exp(-pow(x - mean, 2) / (2 * variance))

  val x = BigDecimal(-5) to BigDecimal(5) by 0.1

  Figure("gaussian")
    .plot(lineColor = BLUE ! 75 ! BLACK, lineType = SMOOTH)(x -> gaussian(0, 0.2) _)
    .plot(lineColor = RED ! 75 ! BLACK)(x -> gaussian(0, 1) _)
    .plot(lineColor = YELLOW ! 70 ! BLACK)(x -> gaussian(0, 5) _)
    .plot(lineColor = GREEN ! 75 ! BLACK)(x -> gaussian(-2, 0.5) _)
    .havingXLabel("$x$")
    .havingLimits(-5, 5, 0, 1)
    .havingMajorGridOn
    .havingTitle("Normal Distribution")
    .havingLegends(
      "$\\mu=0{,}\\,\\sigma=0.2$",
      "$\\mu=0{,}\\,\\sigma=1$",
      "$\\mu=0{,}\\,\\sigma=5$",
      "$\\mu=-2{,}\\,\\sigma=0.5$"
    )
    .havingLegendPos(NORTH_EAST)
    .havingLegendFontSize(FOOTNOTE)
    .havingFontSize(LARGE)
    .saveAsPNG("images")

  /*
   * Area plot
   */
  val xx = BigDecimal(0) to BigDecimal(1) by 0.01

  Figure("area")
    .plot(lineSize = VERY_THIN, opacity = 0.2) {
      xx -> ((x: Double) => sin(4 * Pi * x) * exp(-5 * x))
    }
    .havingFontSize(LARGE)
    .saveAsPNG("images")

  /*
   * Spline plot
   */
  val xs = BigDecimal(0) to BigDecimal(2) * Pi by 0.1

  Figure("spline")
    .plot(lineColor = BLACK, lineStyle = DASHED)(xs -> sin _)
    .scatter(markFillColor = RED, markSize = 1.5) {
      xs -> ((x: Double) => sin(x) + 0.1 * Random.nextGaussian)
    }
    .havingFontSize(LARGE)
    .saveAsPNG("images")

  /*
   * Dark background plot
   */
  val xxs = BigDecimal(0) to BigDecimal(6) by 0.1

  val figure = Figure("dark")
    .havingBackgroundColor(BLACK ! 50)

  (1 to 6)
    .foldLeft(figure) { case (fig, s) => fig.plot(marker = DOT, markSize = 1.5)(xxs -> ((x: Double) => sin(x + s))) }
    .saveAsPNG("images")

  /*
   * Random stem plot
   */
  val randomPoints = (1 to 20).map(_ => Random.nextDouble)

  Figure("stem")
    .stem(lineColor = BLUE ! 50 ! BLACK, marker = CIRCLE)(randomPoints)
    .plot(lineColor = GREEN ! 50 ! BLACK, lineStyle = DASHED, lineType = SMOOTH)(randomPoints)
    .saveAsPNG("images")

  /*
   * Error bar
   */
  Figure("error_bar")
    .errorPlot(lineColor = BLUE ! 50 ! BLACK) {
      (BigDecimal(0) to BigDecimal(1) by 0.1) -> ((x: Double) => x / 2.0)
    } {
      (1 to (BigDecimal(0) to BigDecimal(1) by 0.1).length).map(_ => 0 -> scala.util.Random.nextDouble / 5)
    }
    .havingTitle("Error bar")
    .havingXAxisLinePos(BOTTOM)
    .havingYAxisLinePos(LEFT)
    .havingMajorGridOn
    .havingXLimits(-0.1, 1.1)
    .saveAsPNG("images")

  /*
   * Mesh plot
   */
  val xc = BigDecimal(0) to BigDecimal(2) * Pi by 0.1

  Figure("mesh")
    .mesh(xc -> ((x: Double) => x + math.sin(x)))
    .havingColorMap(ColorMap.COOL)
    .saveAsPNG("images")

  /*
   * Scatter mesh
   */
  Figure("scatter_mesh")
    .scatterMesh((0 to 100) -> ((x: Double) => x + Random.nextInt(10)))
    .havingColorMap(ColorMap.ViRiDiS)
    .saveAsPNG("images")

  /*
   * Figure array
   */
  Figure("array", 2, 2)
    .subFigure(0, 0) { x =>
      x.plot((BigDecimal(0.01) to BigDecimal(10) by 0.1) -> log _)
        .havingXLabel("$x$")
        .havingYLabel("$\\log(x)$")
    }
    .subFigure(0, 1) { x =>
      x.plot(lineColor = GREEN ! 40 ! BLACK)((BigDecimal(-5) to BigDecimal(5) by 0.1) -> ((x: Double) => pow(x, 2)))
        .havingXLabel("$x$")
        .havingYLabel("$x^2$")
    }
    .subFigure(1, 0) { x =>
      x.plot(lineColor = YELLOW ! BLACK)((BigDecimal(0) to BigDecimal(1) by 0.1) -> ((x: Double) => x))
        .havingXLabel("$x$")
        .havingYLabel("$y$")
    }
    .subFigure(1, 1) { x =>
      x.plot(lineColor = BLUE)((BigDecimal(-5) to BigDecimal(5) by 0.1) -> ((x: Double) => pow(x, 3)))
        .havingXLabel("$x$")
        .havingYLabel("$x^3$")
    }
    .saveAsPNG("images")

  /*
   * Secondary axis plot
   */
  Figure("secondary_axis")
    .plot(lineColor = RED)((-5 to 5) -> ((x: Double) => 3 * x))
    .havingXLabel("$x$")
    .havingYLabel("$3x$")
    .secondaryAxis { x =>
      x
        .scatter(markStrokeColor = BLUE, markFillColor = BLUE)((-5 to 5) -> ((x: Double) => x * x))
        .havingYLabel("$x^2$")
    }
    .saveAsPNG("images")
}
