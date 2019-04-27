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

import scalatikz.graphics.{ PGFPlot, TIKZPicture }
import scalatikz.graphics.pgf.plots._
import scalatikz.graphics.pgf.DataTypes._
import scalatikz.graphics.pgf.enums.AxisScale.{ LINEAR, LOG }
import scalatikz.graphics.pgf.enums.AxisSystem.{ CARTESIAN, POLAR }
import scalatikz.graphics.pgf.enums.GridStyle.{ BOTH, MAJOR, MINOR }
import scalatikz.graphics.pgf.enums.LineSize.THIN
import scalatikz.graphics.pgf.enums.LineStyle.SOLID
import scalatikz.graphics.pgf.enums.Mark.NONE
import scalatikz.graphics.pgf.enums.Pattern.PLAIN
import scalatikz.graphics.pgf.enums._

final class Figure private (
    val axis: Axis,
    colorIterator: Iterator[Color],
    override val name: String,
    override val graphics: Seq[PGFPlot],
    axisType: AxisSystem) extends TIKZPicture[PGFPlot] with PGFPlot {

  // An iterator over available colors in case user does not specify one.
  private[this] var currentColor: Color = Color.values.head
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
  def havingName(name: String): Figure = new Figure(axis, colorIterator, name, graphics, axisType)

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
  def havingLogLogAxis: Figure =
    new Figure(axis.copy(xMode = LOG, yMode = LOG), colorIterator, name, graphics, axisType)

  /**
    * @return a Figure having X log scale axis
    */
  def havingLogXAxis: Figure =
    new Figure(axis.copy(xMode = LOG, yMode = LINEAR), colorIterator, name, graphics, axisType)

  /**
    * @return a Figure having Y log scale axis
    */
  def havingLogYAxis: Figure =
    new Figure(axis.copy(xMode = LINEAR, yMode = LOG), colorIterator, name, graphics, axisType)

  /**
    * @return a Figure having minor grid enabled
    */
  def havingMinorGridOn: Figure =
    new Figure(axis.copy(grid = Some(MINOR)), colorIterator, name, graphics, axisType)

  /**
    * @return a Figure having major grid enabled
    */
  def havingMajorGridOn: Figure =
    new Figure(axis.copy(grid = Some(MAJOR)), colorIterator, name, graphics, axisType)

  /**
    * @return a Figure having both major and minor grids enabled
    */
  def havingGridsOn: Figure =
    new Figure(axis.copy(grid = Some(BOTH)), colorIterator, name, graphics, axisType)

  /**
    * Sets a label on the X axis.
    *
    * @param label a label for the X axis
    * @return a Figure having the given label on the X axis
    */
  def havingXLabel(label: String): Figure =
    new Figure(axis.copy(xLabel = Some(label)), colorIterator, name, graphics, axisType)

  /**
    * Sets a label on the Y axis.
    *
    * @param label a label for the Y axis
    * @return a Figure having the given label on the Y axis
    */
  def havingYLabel(label: String): Figure =
    new Figure(axis.copy(yLabel = Some(label)), colorIterator, name, graphics, axisType)

  /**
    * Sets the bounds of the X axis.
    *
    * @param min X lower bound
    * @param max X upper bound
    * @return a Figure having the given bounds on the X axis
    */
  def havingXLimits(min: Double, max: Double): Figure =
    new Figure(axis.copy(xMin = Some(min), xMax = Some(max)), colorIterator, name, graphics, axisType)

  /**
    * Sets the bounds of the Y axis.
    *
    * @param min Y lower bound
    * @param max Y upper bound
    * @return a Figure having the given bounds on the Y axis
    */
  def havingYLimits(min: Double, max: Double): Figure =
    new Figure(axis.copy(yMin = Some(min), yMax = Some(max)), colorIterator, name, graphics, axisType)

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
    new Figure(axis.copy(xMin = Some(xMin), xMax = Some(xMax), yMin = Some(yMin), yMax = Some(yMax)), colorIterator, name, graphics, axisType)

  /**
    * Sets the figure title.
    *
    * @param title a figure title
    * @return a Figure having the given title
    */
  def havingTitle(title: String): Figure =
    new Figure(axis.copy(header = Some(title)), colorIterator, name, graphics, axisType)

  /**
    * Sets the font size.
    *
    * @param size a size
    * @return a Figure having the given font size
    */
  def havingFontSize(size: FontSize): Figure =
    new Figure(axis.copy(fontSize = Some(size)), colorIterator, name, graphics, axisType)

  /**
    * Sets the axis background color
    *
    * @see [[scalatikz.graphics.pgf.enums.Color]]
    *
    * @param color a color
    * @return a Figure having the given background color
    */
  def havingBackgroundColor(color: Color): Figure =
    new Figure(axis.copy(backgroundColor = color), colorIterator, name, graphics, axisType)

  /**
    * Sets the legends of the data sequences.
    *
    * @param legends a sequence of legends
    * @return a Figure having the given legends
    */
  def havingLegends(legends: String*): Figure =
    new Figure(axis.copy(legends = legends), colorIterator, name, graphics, axisType)

  /**
    * Sets the legends position.
    *
    * @see [[scalatikz.graphics.pgf.enums.LegendPos]]
    * @param pos a legend position
    * @return a Figure having the given legend position
    */
  def havingLegendPos(pos: LegendPos): Figure =
    new Figure(axis.copy(legendPos = pos), colorIterator, name, graphics, axisType)

  /**
    * Sets the position of the X axis.
    *
    * @see [[scalatikz.graphics.pgf.enums.AxisLinePos]]
    *
    * @param pos an axis position
    * @return a Figure having the given position on the X axis
    */
  def havingXAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(axis.copy(xAxisLinePos = pos), colorIterator, name, graphics, axisType)

  /**
    * Sets the position of the Y axis.
    *
    * @see [[scalatikz.graphics.pgf.enums.AxisLinePos]]
    *
    * @param pos an axis position
    * @return a Figure having the given position on the Y axis
    */
  def havingYAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(axis.copy(yAxisLinePos = pos), colorIterator, name, graphics, axisType)

  /**
    * Hides the ticks appearing in the X axis.
    *
    * @return a Figure having hidden ticks in the X axis
    */
  def hideXAxisTicks: Figure =
    new Figure(axis.copy(xAxisHideTicks = true), colorIterator, name, graphics, axisType)

  /**
    * Hides the ticks appearing in the Y axis.
    *
    * @return a Figure having hidden ticks in the Y axis
    */
  def hideYAxisTicks: Figure =
    new Figure(axis.copy(yAxisHideTicks = true), colorIterator, name, graphics, axisType)

  /**
    * Changes the X axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given X axis tick labels
    */
  def havingAxisXLabels(labels: Seq[String]): Figure =
    new Figure(axis.copy(xTickLabels = labels), colorIterator, name, graphics, axisType)

  /**
    * Changes the Y axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given Y axis tick labels
    */
  def havingAxisYLabels(labels: Seq[String]): Figure =
    new Figure(axis.copy(yTickLabels = labels), colorIterator, name, graphics, axisType)

  /**
    * Rotates the X axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the X ticks rotated
    */
  def rotateXTicks(degrees: Int): Figure =
    new Figure(axis.copy(rotateXTicks = degrees), colorIterator, name, graphics, axisType)

  /**
    * Rotates the Y axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the Y ticks rotated
    */
  def rotateYTicks(degrees: Int): Figure =
    new Figure(axis.copy(rotateYTicks = degrees), colorIterator, name, graphics, axisType)

  /*
   * =====================================
   *
   * ========: Polar functions
   *
   * =====================================
   */

  def polar(data: Data): Figure = polar()(data)

  def polar(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 2,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      smooth: Boolean = false)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Line(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      smooth), POLAR
    )

  def polarScatter(data: Data): Figure = polarScatter()(data)

  def polarScatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 2)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Scatter(
      data.coordinates,
      marker,
      markStrokeColor,
      markFillColor,
      markSize), POLAR
    )

  /*
   * =====================================
   *
   * ========: Bar functions
   *
   * =====================================
   */

  /**
    * Plots a data sequence as a bar graph of the data in Y coordinate
    * versus the corresponding X coordinate.
    *
    * @param data sequence of x, y points in the Euclidean space
    * @return a figure containing the bar graph
    */
  def bar(data: Data): Figure = bar()(data)

  /**
    * Plots a data sequence as a bar graph of the data in Y coordinate
    * versus the corresponding X coordinate.
    *
    * @param color line color (default random color)
    * @param pattern a pattern to fill the bars (default is plain color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param opacity opacity of the bars (default is 1)
    * @param barWidth the bars width (default is 0.5 pt)
    * @param data sequence of x, y points in the Euclidean space
    */
  def bar(
      color: Color = nextColor,
      pattern: Pattern = PLAIN,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      opacity: Double = 1,
      barWidth: Double = 0.5)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ yBar(
      data.coordinates,
      color,
      pattern,
      lineStyle,
      lineSize,
      opacity,
      barWidth), axisType
    )

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
    * @param markSize mark size (default is 1 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param smooth true for a smooth line (default is false)
    * @param data sequence of x, y points in the Euclidean space
    */
  def plot(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      smooth: Boolean = false,
      sparse: Boolean = false)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Line(
      if (sparse) data.sparse.coordinates else data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      smooth), axisType
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
    * @param markSize mark size (default is 1 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param pattern the pattern to fill the area (default is plain color)
    * @param opacity opacity of the area under curve (default is 0.5)
    * @param smooth true for a smooth line (default is false)
    * @param constant true for a constant area (default is false)
    * @param data sequence of x, y points in the Euclidean space
    */
  def area(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      pattern: Pattern = PLAIN,
      opacity: Double = 0.5,
      smooth: Boolean = false,
      constant: Boolean = false)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Area(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      pattern,
      opacity,
      smooth,
      constant), axisType
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
    * @param markSize mark size (default is 1 pt)
    * @param data sequence of x, y points in the Euclidean space
    */
  def stem(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Stem(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize), axisType
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
    * @param markSize mark size (default is 1 pt)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param data sequence of x, y points in the Euclidean space
    */
  def stair(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Stair(
      data.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize), axisType
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
    * @param markSize mark size (default is 1 pt)
    * @param data sequence of x, y points in the Euclidean space
    */
  def scatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1)(data: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ Scatter(
      data.coordinates,
      marker,
      markStrokeColor,
      markFillColor,
      markSize), axisType
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
  def errorBar(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      smooth: Boolean = false)(data: Data)(error: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ ErrorBar(
      data.coordinates,
      error.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      smooth), axisType
    )

  /*
   * =====================================
   *
   * ========: ErrorArea functions
   *
   * =====================================
   */

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along an error area around the data points.
    *
    * @param data sequence of x, y points in the Euclidean space along
    *             a sequence of x-error, y-error points.
    */
  def errorArea(data: Data)(error: Data): Figure = errorArea()(data)(error)

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along an error area around the data points.
    *
    * @param color line color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    * @param opacity opacity of the error area
    * @param smooth true in case the line is smooth
    * @param data sequence of x, y points in the Euclidean space along
    *             a sequence of x-error, y-error points.
    */
  def errorArea(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      opacity: Double = 0.2,
      smooth: Boolean = false)(data: Data)(error: Data): Figure =
    new Figure(axis, colorIterator, name, graphics :+ ErrorArea(
      data.coordinates,
      error.coordinates,
      color,
      marker,
      markStrokeColor,
      markFillColor,
      markSize,
      lineStyle,
      lineSize,
      opacity,
      smooth), axisType
    )

  override def toString: String =
    s"\\begin{$axisType}[" +
      s"$axis] " +
      s"${graphics.mkString("\n")} " +
      s"\\end{$axisType}"
}

final class FigureArray private[graphics] (
    override val name: String,
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
    new Figure(Axis(), Iterator.continually(Color.values).flatten, name, Seq.empty[PGFPlot], CARTESIAN)

  def apply(name: String, N: Int, M: Int): FigureArray =
    new FigureArray(name, Seq.fill(N * M)(Figure("")), N, M)
}
