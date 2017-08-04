/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
 */

package scalatikz.graphics.pgf

import scalatikz.graphics.{PGFPlot, TIKZPicture}
import scalatikz.graphics.pgf.Color._
import scalatikz.graphics.pgf.LineSize._
import scalatikz.graphics.pgf.LineStyle._
import scalatikz.graphics.pgf.Mark._
import scalatikz.graphics.pgf.plots._
import scalatikz.graphics.pgf.AxisLinePos._
import scalatikz.graphics.pgf.DataTypes._
import scalatikz.graphics.pgf.FontSize._
import scalatikz.graphics.pgf.LegendPos._
import scalatikz.graphics.pgf.AxisStyle._
import scalatikz.graphics.pgf.GridStyle._

final class Figure private(val axis: Axis,
                           override val name: String,
                           override val graphics: Seq[PGFPlot]) extends TIKZPicture[PGFPlot] with PGFPlot {

  // An iterator over available colors in case user does not specify one.
  private[this] val colorIterator = Iterator.continually(colors).flatten
  private[this] var currentColor: Color = colors.head

  private[this] def nextColor: Color = {
    currentColor = colorIterator.next
    currentColor
  }

  /**
    * Rename the figure.
    *
    * @param name a name for the figure
    * @return a Figure having the given name
    */
  def havingName(name: String): Figure = new Figure(axis, name, graphics)

  /*
   * =====================================
   *
   * ========: Axis functions
   *
   * =====================================
   */

  /**
    * @return a Figure having both X and Y log scale axis
    */
  def havingLogLogAxis: Figure = new Figure(axis.copy(xMode = LOG, yMode = LOG), name, graphics)

  /**
    * @return a Figure having X log scale axis
    */
  def havingLogXAxis: Figure = new Figure(axis.copy(xMode = LOG, yMode = LINEAR), name, graphics)

  /**
    * @return a Figure having Y log scale axis
    */
  def havingLogYAxis: Figure = new Figure(axis.copy(xMode = LINEAR, yMode = LOG), name, graphics)

  /**
    * @return a Figure having minor grid enabled
    */
  def havingMinorGridOn: Figure = new Figure(axis.copy(grid = Some(MINOR)), name, graphics)

  /**
    * @return a Figure having major grid enabled
    */
  def havingMajorGridOn: Figure = new Figure(axis.copy(grid = Some(MAJOR)), name, graphics)

  /**
    * @return a Figure having both major and minor grids enabled
    */
  def havingGridsOn: Figure = new Figure(axis.copy(grid = Some(BOTH)), name, graphics)

  /**
    * Sets a label on the X axis.
    *
    * @param label a label for the X axis
    * @return a Figure having the given label on the X axis
    */
  def havingXLabel(label: String): Figure = new Figure(axis.copy(xLabel = Some(label)), name, graphics)

  /**
    * Sets a label on the Y axis.
    *
    * @param label a label for the Y axis
    * @return a Figure having the given label on the Y axis
    */
  def havingYLabel(label: String): Figure = new Figure(axis.copy(yLabel = Some(label)), name, graphics)

  /**
    * Sets the bounds of the X axis.
    *
    * @param min X lower bound
    * @param max X upper bound
    * @return a Figure having the given bounds on the X axis
    */
  def havingXLimits(min: Double, max: Double): Figure =
    new Figure(axis.copy(xMin = Some(min), xMax = Some(max)), name, graphics)

  /**
    * Sets the bounds of the Y axis.
    *
    * @param min Y lower bound
    * @param max Y upper bound
    * @return a Figure having the given bounds on the Y axis
    */
  def havingYLimits(min: Double, max: Double): Figure =
    new Figure(axis.copy(yMin = Some(min), yMax = Some(max)), name, graphics)

  /**
    * Sets the bounds for both X and Y axis.
    *
    * @param xMin X lower bound
    * @param xMax X upper bound
    * @param yMin Y lower bound
    * @param yMax Y upper bound
    * @return a Figure having the given bounds on X and Y axis
    */
  def havingLimits(xMin: Double, xMax: Double, yMin: Double, yMax: Double): Figure =
    new Figure(axis.copy(xMin = Some(xMin), xMax = Some(xMax), yMin = Some(yMin), yMax = Some(yMax)), name, graphics)

  /**
    * Sets the figure title.
    *
    * @param title a figure title
    * @return a Figure having the given title
    */
  def havingTitle(title: String): Figure = new Figure(axis.copy(header = Some(title)), name, graphics)

  /**
    * Sets the font size.
    *
    * @param size a size
    * @return a Figure having the given font size
    */
  def havingFontSize(size: FontSize): Figure = new Figure(axis.copy(fontSize = Some(size)), name, graphics)

  /**
    * Sets the axis background color
    *
    * @see [[scalatikz.graphics.pgf.Color]]
    *
    * @param color a color
    * @return a Figure having the given background color
    */
  def havingBackgroundColor(color: Color): Figure = new Figure(axis.copy(backgroundColor = color), name, graphics)

  /**
    * Sets the legends of the data sequences.
    *
    * @param legends a sequence of legends
    * @return a Figure having the given legends
    */
  def havingLegends(legends: String *): Figure = new Figure(axis.copy(legends = legends), name, graphics)

  /**
    * Sets the legends position.
    *
    * @see [[scalatikz.graphics.pgf.LegendPos]]
    * @param pos a legend position
    * @return a Figure having the given legend position
    */
  def havingLegendPos(pos: LegendPos): Figure = new Figure(axis.copy(legendPos = pos), name, graphics)

  /**
    * Sets the position of the X axis.
    *
    * @see [[scalatikz.graphics.pgf.AxisLinePos]]
    *
    * @param pos an axis position
    * @return a Figure having the given position on the X axis
    */
  def havingXAxisLinePos(pos: AxisLinePos): Figure = new Figure(axis.copy(xAxisLinePos = pos), name, graphics)

  /**
    * Sets the position of the Y axis.
    *
    * @see [[scalatikz.graphics.pgf.AxisLinePos]]
    *
    * @param pos an axis position
    * @return a Figure having the given position on the Y axis
    */
  def havingYAxisLinePos(pos: AxisLinePos): Figure = new Figure(axis.copy(yAxisLinePos = pos), name, graphics)


  /*
   * =====================================
   *
   * ========: Plot functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as a 2D line of the data in Y coordinate
    * versus the corresponding X coordinate.
    *
    * @param data sequence of x, y points in the Euclidean space
    */
  def plot(data: Data): Figure = plot()(data)

  /**
    * Plots a data sequence as a 2D line of the data in Y coordinate
    * versus the corresponding X coordinate.
    *
    * @param color line color (default random color)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 2 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param smooth true for a smooth line (default is false)
    * @param data sequence of x, y points in the Euclidean space
    */
  def plot(color: Color = nextColor,
           marker: Mark = NONE,
           markStrokeColor: Color = currentColor,
           markFillColor: Color = currentColor,
           markSize: Double = 2,
           lineStyle: LineStyle = SOLID,
           lineSize: LineSize = THIN,
           smooth: Boolean = false)(data: Data): Figure =
    new Figure(axis, name, graphics :+ Line(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      smooth)
    )


  /*
   * =====================================
   *
   * ========: Area functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as a 2D line of the data in Y coordinate
    * versus the corresponding X coordinate and fills the area beneath the curve.
    *
    * @param data sequence of x, y points in the Euclidean space
    */
  def area(data: Data): Figure = area()(data)

  /**
    * Plots a data sequence as a 2D line of the data in Y coordinate
    * versus the corresponding X coordinate and fills the area beneath the curve.
    *
    * @param color line color (default is a random color)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 2 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param opacity opacity of the area under curve (default is 0.5)
    * @param smooth true for a smooth line (default is false)
    * @param constant true for a constant area (default is false)
    * @param data sequence of x, y points in the Euclidean space
    */
  def area(color: Color = nextColor,
           marker: Mark = NONE,
           markStrokeColor: Color = currentColor,
           markFillColor: Color = currentColor,
           markSize: Double = 2,
           lineStyle: LineStyle = SOLID,
           lineSize: LineSize = THIN,
           opacity: Double = 0.5,
           smooth: Boolean = false,
           constant: Boolean = false)(data: Data): Figure =
    new Figure(axis, name, graphics :+ Area(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      opacity,
      smooth,
      constant)
    )


  /*
   * =====================================
   *
   * ========: Stem functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as stems that extend from a baseline along the
    * x-axis. The data values are indicated by marks terminating each stem.
    *
    * @param data sequence of x, y points in the Euclidean space
    */
  def stem(data: Data): Figure = stem()(data)

  /**
    * Plots a data sequence as stems that extend from a baseline along the
    * x-axis. The data values are indicated by marks terminating each stem.
    *
    * @param color line color (default random color)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 2 pt)
    * @param data sequence of x, y points in the Euclidean space
    */
  def stem(color: Color = nextColor,
           marker: Mark = NONE,
           markStrokeColor: Color = currentColor,
           markFillColor: Color = currentColor,
           markSize: Double = 2)(data: Data): Figure =
    new Figure(axis, name, graphics :+ Stem(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize)
    )


  /*
   * =====================================
   *
   * ========: Stair functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as a stair step of the data in Y versus
    * the corresponding values in X.
    *
    * @param data sequence of x, y points in the Euclidean space
    */
  def stair(data: Data): Figure = stair()(data)

  /**
    * Plots a data sequence as a stair step of the data in Y versus
    * the corresponding values in X.
    *
    * @param color line color (default random color)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 2 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param data sequence of x, y points in the Euclidean space
    */
  def stair(color: Color = nextColor,
           marker: Mark = NONE,
           markStrokeColor: Color = currentColor,
           markFillColor: Color = currentColor,
           markSize: Double = 2,
           lineStyle: LineStyle = SOLID,
           lineSize: LineSize = THIN)(data: Data): Figure =
    new Figure(axis, name, graphics :+ Stair(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize)
    )


  /*
   * =====================================
   *
   * ========: Scatter functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as a scatter at the locations specified by the
    * data sequence. The type of the graph is also called a bubble plot.
    *
    * @param data sequence of x, y points in the Euclidean space
    */
  def scatter(data: Data): Figure = scatter()(data)

  /**
    * Plots a data sequence as a scatter at the locations specified by the
    * data sequence. The type of the graph is also called a bubble plot.
    *
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 2 pt)
    * @param data sequence of x, y points in the Euclidean space
    */
  def scatter(marker: Mark = NONE,
              markStrokeColor: Color = nextColor,
              markFillColor: Color = currentColor,
              markSize: Double = 2)(data: Data): Figure =
    new Figure(axis, name, graphics :+ Scatter(
      data.coordinates,
      marker,
      markStrokeColor,
      markFillColor,
      markSize)
    )


  /*
   * =====================================
   *
   * ========: ErrorBar functions
   *
   * =====================================
   */

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param data sequence of x, y points in the Euclidean space along
    *             a sequence of x-error, y-error points.
    */
  def errorBar(data: Data)(error: Data): Figure = errorBar()(data)(error)

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param color line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    * @param smooth true in case the line is smooth
    * @param data sequence of x, y points in the Euclidean space along
    *             a sequence of x-error, y-error points.
    */
  def errorBar(color: Color = nextColor,
               marker: Mark = NONE,
               markStrokeColor: Color = currentColor,
               markFillColor: Color = currentColor,
               markSize: Double = 2,
               lineStyle: LineStyle = SOLID,
               lineSize: LineSize = THIN,
               smooth: Boolean = false)(data: Data)(error: Data): Figure =
    new Figure(axis, name, graphics :+ ErrorBar(
      data.coordinates,
      error.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      smooth)
    )

  override def toString: String =
    raw"""
       | \begin{axis}[
       |  $axis]
       |  ${graphics.mkString("\n")}
       | \end{axis}
    """.stripMargin
}

final class FigureArray private[graphics](override val name: String,
                                          override protected val graphics: Seq[Figure],
                                          N: Int, M: Int) extends TIKZPicture[Figure] {

  /**
    * Creates a sub-figure in the given position and transforms the figure
    * according to a given Figure function.
    *
    * @param i row index
    * @param j column index
    * @param f a transformation function over a Figure
    * @return a FigureArray having a sub-figure as defined by the given function
    */
  def subFigure(i: Int, j: Int)(f: Figure => Figure): FigureArray =
    if (i < N && j < M) new FigureArray(name, graphics.updated(M * i + j, f(graphics(M * i + j))), N, M)
    else {
      logger.warn {
        s"Given position [$i, $j] does not exist. Figure array has dimensions [$N x $M].\nIgnoring command."
      }
      this
    }

  override def toString: String =
    raw"""
       | \matrix {
       | ${graphics.grouped(N).map(_.filter(_.graphics.nonEmpty)).map(_.mkString("\n&\n")).mkString("\n \\\\[0.5cm]")}
       | \\
       | };
    """.stripMargin
}

object Figure {

  def apply(name: String): Figure =
    new Figure(Axis(), name, Seq.empty[PGFPlot])

  def apply(name: String, N: Int, M: Int): FigureArray =
    new FigureArray(name, Seq.fill(N * M)(Figure("")), N, M)
}
