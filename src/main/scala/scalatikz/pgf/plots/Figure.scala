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

import scalatikz.pgf.TIKZPicture
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }
import scalatikz.pgf.enums.LineSize.THIN
import scalatikz.pgf.enums.LineStyle.SOLID
import scalatikz.pgf.plots.types._
import scalatikz.pgf.plots.DataTypes._
import scalatikz.pgf.plots.enums.AxisScale.{ LINEAR, LOG }
import scalatikz.pgf.plots.enums.AxisSystem.{ CARTESIAN, POLAR }
import scalatikz.pgf.plots.enums.GridStyle.{ BOTH, MAJOR, MINOR }
import scalatikz.pgf.plots.enums.LineType._
import scalatikz.pgf.plots.enums.Mark.NONE
import scalatikz.pgf.plots.enums.Pattern.PLAIN
import scalatikz.pgf.plots.enums._

class Figure private (
    colorIterator: Iterator[Color],
    override val name: String,
    private[plots] val axis: Axis,
    private[plots] val axisType: AxisSystem,
    private[plots] val graphics: List[PGFPlot],
    private[plots] val secondary: Option[Figure]) extends TIKZPicture {

  override protected val libraries: String =
    """
      |\usetikzlibrary{plotmarks}
      |\usetikzlibrary{patterns}
      |\usetikzlibrary{pgfplots.polar}
      |\usepgfplotslibrary{fillbetween}
      |""".stripMargin

  // An iterator over available colors in case user does not specify one.
  private[this] var currentColor: Color = Color.values.head
  private[this] def nextColor: Color = {
    currentColor = colorIterator.next
    currentColor
  }

  /*
   * =====================================
   *
   * ========: TIKZ picture functions
   *
   * =====================================
   */

  /**
    * Rename the figure.
    *
    * @param name a name for the figure
    * @return a Figure having the given name
    */
  def havingName(name: String): Figure = new Figure(colorIterator, name, axis, axisType, graphics, secondary)

  /*
   * =====================================
   *
   * ========: Axis functions
   *
   * =====================================
   */

  /**
    * @param centimeters the height in centimeters
    * @return a Figure having the given height
    */
  def havingHeight(centimeters: Double): Figure =
    new Figure(colorIterator, name, axis.copy(height = Some(centimeters)), axisType, graphics, secondary)

  /**
    * @param centimeters the width in centimeters
    * @return a Figure having the given width
    */
  def havingWidth(centimeters: Double): Figure =
    new Figure(colorIterator, name, axis.copy(width = Some(centimeters)), axisType, graphics, secondary)

  /**
    * @return a Figure having both X and Y log scale axis
    */
  def havingLogLogAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LOG, yMode = LOG), axisType, graphics, secondary)

  /**
    * @return a Figure having X log scale axis
    */
  def havingLogXAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LOG, yMode = LINEAR), axisType, graphics, secondary)

  /**
    * @return a Figure having Y log scale axis
    */
  def havingLogYAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LINEAR, yMode = LOG), axisType, graphics, secondary)

  /**
    * @return a Figure having minor grid enabled
    */
  def havingMinorGridOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(MINOR)), axisType, graphics, secondary)

  /**
    * @return a Figure having major grid enabled
    */
  def havingMajorGridOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(MAJOR)), axisType, graphics, secondary)

  /**
    * @return a Figure having adjacent bars instead of stacked
    */
  def havingAdjacentBars: Figure =
    new Figure(colorIterator, name, axis.copy(stackedBars = false), axisType, graphics, secondary)

  /**
    * @return a Figure having both major and minor grids enabled
    */
  def havingGridsOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(BOTH)), axisType, graphics, secondary)

  /**
    * Sets a label on the X axis.
    *
    * @param label a label for the X axis
    * @return a Figure having the given label on the X axis
    */
  def havingXLabel(label: String): Figure =
    new Figure(colorIterator, name, axis.copy(xLabel = Some(label)), axisType, graphics, secondary)

  /**
    * Sets a label on the Y axis.
    *
    * @param label a label for the Y axis
    * @return a Figure having the given label on the Y axis
    */
  def havingYLabel(label: String): Figure =
    new Figure(colorIterator, name, axis.copy(yLabel = Some(label)), axisType, graphics, secondary)

  /**
    * Sets the bounds of the X axis.
    *
    * @param min X lower bound
    * @param max X upper bound
    * @return a Figure having the given bounds on the X axis
    */
  def havingXLimits(min: Double, max: Double): Figure =
    new Figure(colorIterator, name, axis.copy(xMin = Some(min), xMax = Some(max)), axisType, graphics, secondary)

  /**
    * Sets the bounds of the Y axis.
    *
    * @param min Y lower bound
    * @param max Y upper bound
    * @return a Figure having the given bounds on the Y axis
    */
  def havingYLimits(min: Double, max: Double): Figure =
    new Figure(colorIterator, name, axis.copy(yMin = Some(min), yMax = Some(max)), axisType, graphics, secondary)

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
    new Figure(colorIterator, name, axis.copy(xMin = Some(xMin), xMax = Some(xMax), yMin = Some(yMin), yMax = Some(yMax)), axisType, graphics, secondary)

  /**
    * Sets the figure title.
    *
    * @param title a figure title
    * @return a Figure having the given title
    */
  def havingTitle(title: String): Figure =
    new Figure(colorIterator, name, axis.copy(header = Some(title)), axisType, graphics, secondary)

  /**
    * Sets the font size.
    *
    * @param size a size
    * @return a Figure having the given font size
    */
  def havingFontSize(size: FontSize): Figure =
    new Figure(colorIterator, name, axis.copy(fontSize = Some(size)), axisType, graphics, secondary)

  /**
    * Sets the axis background color
    *
    * @param color a color
    * @return a Figure having the given background color
    */
  def havingBackgroundColor(color: Color): Figure =
    new Figure(colorIterator, name, axis.copy(backgroundColor = color), axisType, graphics, secondary)

  /**
    * Sets the axis color map.
    *
    * @param map a color map
    * @return a Figure having the given color map
    */
  def havingColorMap(map: ColorMap): Figure =
    new Figure(colorIterator, name, axis.copy(colorMap = Some(map)), axisType, graphics, secondary)

  /**
    * Sets the legends of the data sequences.
    *
    * @param legends a sequence of legends
    * @return a Figure having the given legends
    */
  def havingLegends(legends: String*): Figure =
    new Figure(colorIterator, name, axis.copy(legends = legends), axisType, graphics, secondary)

  /**
    * Sets the legends position.
    *
    * @see [[scalatikz.pgf.plots.enums.LegendPos]]
    * @param pos a legend position
    * @return a Figure having the given legend position
    */
  def havingLegendPos(pos: LegendPos): Figure =
    new Figure(colorIterator, name, axis.copy(legendPos = pos), axisType, graphics, secondary)

  /**
    * Sets the legend columns.
    *
    * @param cols the number of adjacent legend entries
    * @return a Figure having the given legend columns
    */
  def havingLegendColumns(cols: Int): Figure =
    new Figure(colorIterator, name, axis.copy(legendColumns = cols), axisType, graphics, secondary)

  /**
    * Sets the position of the X axis.
    *
    * @see [[scalatikz.pgf.plots.enums.AxisLinePos]]
    * @param pos an axis position
    * @return a Figure having the given position on the X axis
    */
  def havingXAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(colorIterator, name, axis.copy(xAxisLinePos = pos), axisType, graphics, secondary)

  /**
    * Sets the position of the Y axis.
    *
    * @see [[scalatikz.pgf.plots.enums.AxisLinePos]]
    * @param pos an axis position
    * @return a Figure having the given position on the Y axis
    */
  def havingYAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(colorIterator, name, axis.copy(yAxisLinePos = pos), axisType, graphics, secondary)

  /**
    * Hides the ticks appearing in the X axis.
    *
    * @return a Figure having hidden ticks in the X axis
    */
  def hideXAxisTicks: Figure =
    new Figure(colorIterator, name, axis.copy(xAxisHideTicks = true), axisType, graphics, secondary)

  /**
    * Hides the ticks appearing in the Y axis.
    *
    * @return a Figure having hidden ticks in the Y axis
    */
  def hideYAxisTicks: Figure =
    new Figure(colorIterator, name, axis.copy(yAxisHideTicks = true), axisType, graphics, secondary)

  /**
    * Changes the X axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given X axis tick labels
    */
  def havingAxisXLabels(labels: Seq[String]): Figure =
    new Figure(colorIterator, name, axis.copy(xTickLabels = labels), axisType, graphics, secondary)

  /**
    * Changes the Y axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given Y axis tick labels
    */
  def havingAxisYLabels(labels: Seq[String]): Figure =
    new Figure(colorIterator, name, axis.copy(yTickLabels = labels), axisType, graphics, secondary)

  /**
    * Rotates the X axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the X ticks rotated
    */
  def rotateXTicks(degrees: Int): Figure =
    new Figure(colorIterator, name, axis.copy(rotateXTicks = degrees), axisType, graphics, secondary)

  /**
    * Rotates the Y axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the Y ticks rotated
    */
  def rotateYTicks(degrees: Int): Figure =
    new Figure(colorIterator, name, axis.copy(rotateYTicks = degrees), axisType, graphics, secondary)

  /*
   * =====================================
   *
   * ========: Custom plots
   *
   * =====================================
   */

  /**
    * Plots the given custom pgf plot.
    *
    * @note You can extend [[scalatikz.pgf.plots.types.PGFPlot]] in order
    *       to create custom pgf plots.
    *
    * @param pgf a pgf plot instance
    */
  def customPlot(pgf: PGFPlot): Figure =
    new Figure(colorIterator, name, axis, axisType, pgf :: graphics, secondary)

  /*
   * =====================================
   *
   * ========: Line plots
   *
   * =====================================
   */

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def plot(data: Data2D): Figure = plot()(data)

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y.
    *
    * @param lineColor line color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param smooth plot a smooth line instead of a sharp (default is false)
    * @param sparse plot only distinct points (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def plot(
      lineColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      smooth: Boolean = false,
      sparse: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.compress.coordinates else data.coordinates,
        if (smooth) SMOOTH else SHARP,
        lineColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        PLAIN,
        None,
        0.0
      ) :: graphics, secondary
    )

  /**
    * Plots a 2D mesh line of the data in X against the corresponding values in Y.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def mesh(data: Data2D): Figure = mesh()(data)

  /**
    * Plots a 2D mesh line of the data in X against the corresponding values in Y.
    *
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def mesh(
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Mesh(
        data.coordinates,
        lineStyle,
        lineSize
      ) :: graphics, secondary
    )

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorPlot(data: Data2D)(error: Data2D): Figure = errorPlot()(data)(error)

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param lineColor line color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param smooth plot a smooth line instead of a sharp (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorPlot(
      lineColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      smooth: Boolean = false)(data: Data2D)(error: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      ErrorLine(
        data.coordinates,
        error.coordinates,
        if (smooth) SMOOTH else SHARP,
        lineColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Area plots
   *
   * =====================================
   */

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y
    * and fills the area under the curve.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def area(data: Data2D): Figure = area()(data)

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y
    * and fills the area under the curve.
    *
    * @param lineColor line color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param pattern a fill pattern for the area under the curve (default is plain color)
    * @param fillColor pattern color
    * @param opacity opacity of the area under the curve
    * @param smooth plot a smooth line instead of a sharp (default is false)
    * @param sparse plot only distinct points (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def area(
      lineColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      pattern: Pattern = PLAIN,
      fillColor: Color = currentColor,
      opacity: Double = 0.5,
      smooth: Boolean = false,
      sparse: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.compress.coordinates else data.coordinates,
        if (smooth) SMOOTH else SHARP,
        lineColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        pattern,
        Some(fillColor),
        opacity
      ) :: graphics, secondary
    )

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y
    * along an error area around the 2D line.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorArea(data: Data2D)(error: Data2D): Figure = errorArea()(data)(error)

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y
    * and fills the area under the curve.
    *
    * @param lineColor line color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param fillColor color of the error area
    * @param opacity opacity of the error area
    * @param smooth plot a smooth line instead of a sharp (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorArea(
      lineColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      fillColor: Color = currentColor,
      opacity: Double = 0.5,
      smooth: Boolean = false)(data: Data2D)(error: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      ErrorArea(
        data.coordinates,
        error.coordinates,
        if (smooth) SMOOTH else SHARP,
        lineColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        fillColor,
        opacity
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Stem plots
   *
   * =====================================
   */

  /**
    * Plots stems of the given data extending from the X-axis to their corresponding
    * y values. The data values along the Y-axis are indicated by marks terminating each stem.
    *
    * @note You can also create horizontal stems extending from Y-axis to X values.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def stem(data: Data2D): Figure = stem()(data)

  /**
    * Plots stems of the given data extending from the X-axis to their corresponding
    * y values. The data values along the Y-axis are indicated by marks terminating each stem.
    *
    * @note You can also create horizontal stems extending from Y-axis to X values.
    *
    * @param lineColor line color (default random color)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param nodesNearCoords depict nodes near coords (default is false)
    * @param horizontal horizontal stems extending from Y-axis to X values (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def stem(
      lineColor: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      nodesNearCoords: Boolean = false,
      horizontal: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Stem(
        data.coordinates,
        lineColor,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        nodesNearCoords,
        horizontal
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Steps plots
   *
   * =====================================
   */

  /**
    * Plots the data in X against the corresponding values in Y as constant steps.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def steps(data: Data2D): Figure = steps()(data)

  /**
    * Plots the data in X against the corresponding values in Y as constant steps.
    *
    * @param lineColor line color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param sparse plot only distinct points (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def steps(
      lineColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      sparse: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.compress.coordinates else data.coordinates,
        CONST,
        lineColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        PLAIN,
        None,
        0.5
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Scatter functions
   *
   * =====================================
   */

  /**
    * Plots a scatter of data points.
    *
    * @note Scatter plot is also called a bubble plot.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def scatter(data: Data2D): Figure = scatter()(data)

  /**
    * Plots a scatter of data points.
    *
    * @note Scatter plot is also called a bubble plot.
    *
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param nodesNearCoords depict nodes near coords (default is false)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def scatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      nodesNearCoords: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Scatter(
        data.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        nodesNearCoords
      ) :: graphics, secondary
    )

  /**
    * Plots a scatter mesh of data points.
    *
    * @note Scatter plot is also called a bubble plot.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def scatterMesh(data: Data2D): Figure = scatterMesh()(data)

  /**
    * Plots a scatter mesh of data points.
    *
    * @note Scatter plot is also called a bubble plot.
    *
    * @param marker mark style (default is DOT)
    * @param markSize mark size (default is 1 pt)
    * @param data sequence of X, Y points in the Euclidean space
    */
  def scatterMesh(
      marker: Mark = Mark.DOT,
      markSize: Double = 1)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      MeshScatter(
        data.coordinates,
        marker,
        markSize
      ) :: graphics, secondary
    )

  /**
    * Creates a scatter of data points along vertical and/or horizontal
    * error bars at each data point.
    *
    * @note Scatter plot is also called a bubble plot.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y points in the Euclidean space
    */
  def errorScatter(data: Data2D)(error: Data2D): Figure = errorScatter()(data)(error)

  /**
    * Plots a data sequence as a scatter at the locations specified by the
    * data sequence. The type of the graph is also called a bubble plot.
    *
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y points in the Euclidean space
    */
  def errorScatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1)(data: Data2D)(error: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      ErrorScatter(
        data.coordinates,
        error.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Bar plots
   *
   * =====================================
   */

  /**
    * Plots 2D bars of the data in X against the corresponding values in Y.
    *
    * @param data sequence of X, Y points in the Euclidean space
    */
  def bar(data: Data2D): Figure = bar()(data)

  /**
    * Plots 2D bars of the data in X against the corresponding values in Y.
    *
    * @param barColor bar color (default random color)
    * @param lineStyle line style (default is solid)
    * @param lineSize line size (default is thin)
    * @param marker mark style (default none)
    * @param markStrokeColor mark stroke color (optional)
    * @param markFillColor mark fill color (optional)
    * @param markSize mark size (default is 1 pt)
    * @param pattern a pattern to fill the bars (default is plain color)
    * @param opacity opacity of the bars (default is 1)
    * @param barWidth the bars width (default is 0.5 pt)
    * @param nodesNearCoords depict nodes near coords
    * @param horizontal horizontal stems extending from Y-axis to X values
    * @param data sequence of X, Y points in the Euclidean space
    */
  def bar(
      barColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      pattern: Pattern = PLAIN,
      opacity: Double = 1,
      barWidth: Double = 0.25,
      nodesNearCoords: Boolean = false,
      horizontal: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Bar(
        data.coordinates,
        barColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        pattern,
        opacity,
        barWidth,
        nodesNearCoords,
        horizontal
      ) :: graphics, secondary
    )

  /**
    * Creates 2D bars of the data in X against the corresponding values in Y
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorBar(data: Data2D)(error: Data2D): Figure = errorBar()(data)(error)

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param barColor bar color
    * @param marker mark style
    * @param markStrokeColor mark stroke color
    * @param markFillColor mark fill color
    * @param markSize mark size
    * @param lineStyle line style
    * @param lineSize line size
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorBar(
      barColor: Color = nextColor,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 1,
      pattern: Pattern = PLAIN,
      opacity: Double = 1,
      barWidth: Double = 0.25,
      horizontal: Boolean = false)(data: Data2D)(error: Data2D): Figure =
    new Figure(colorIterator, name, axis, axisType,
      ErrorBar(
        data.coordinates,
        error.coordinates,
        barColor,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        PLAIN,
        opacity,
        barWidth,
        horizontal
      ) :: graphics, secondary
    )

  /*
   * =====================================
   *
   * ========: Polar functions
   *
   * =====================================
   */

  def polar(data: Data2D): Figure = polar()(data)

  def polar(
      color: Color = nextColor,
      marker: Mark = NONE,
      markStrokeColor: Color = currentColor,
      markFillColor: Color = currentColor,
      markSize: Double = 2,
      lineStyle: LineStyle = SOLID,
      lineSize: LineSize = THIN,
      smooth: Boolean = false)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, POLAR,
      Line(
        data.coordinates,
        if (smooth) SMOOTH else SHARP,
        color,
        lineStyle,
        lineSize,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        PLAIN,
        None,
        0.5
      ) :: graphics, secondary
    )

  def polarScatter(data: Data2D): Figure = polarScatter()(data)

  def polarScatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 2)(data: Data2D): Figure =
    new Figure(colorIterator, name, axis, POLAR,
      Scatter(
        data.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        nodesNearCoords = false
      ) :: graphics, secondary
    )

  def secondaryAxis(f: Figure => Figure): Figure = {

    val transformed = f(secondary.getOrElse(
      Figure(Figure.UNNAMED).havingYAxisLinePos(AxisLinePos.RIGHT)))

    if (transformed.secondary.nonEmpty)
      fatal("Secondary axis cannot itself has another secondary axis. Recursion is bad.")

    val _transformed = if (transformed.axis.yAxisLinePos != AxisLinePos.RIGHT) {
      logger.warn("y axis line position should be right. Changing to right.")
      transformed.havingYAxisLinePos(AxisLinePos.RIGHT)
    } else transformed

    val updatedAxis = if (axis.yAxisLinePos != AxisLinePos.LEFT) {
      axis.copy(yAxisLinePos = AxisLinePos.LEFT)
    } else axis

    new Figure(colorIterator, name, updatedAxis, axisType, graphics, Some(_transformed))
  }

  override def toString: String =
    raw"""
      |${if (secondary.nonEmpty) """\pgfplotsset{set layers}""".stripMargin}
      |\${Symbol("begin").name}{$axisType}[
      |$axis
      |]
      |${graphics.mkString("\n")}
      |\end{$axisType}
      |${if (secondary.nonEmpty) secondary.get.toString}
    """.stripMargin
}

class FigureArray private[pgf] (
    override val name: String,
    val graphics: IndexedSeq[Figure],
    N: Int, M: Int) extends TIKZPicture {

  override protected val libraries: String =
    """
      |\usepackage{tikz,pgfplots}
      |\usetikzlibrary{plotmarks}
      |\usetikzlibrary{patterns}
      |\usetikzlibrary{pgfplots.polar}
      |\usepgfplotslibrary{fillbetween}
      |""".stripMargin

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
      logger.warn(s"Position [$i, $j] does not exist. Figure array has dimensions [$N x $M].\nIgnoring command.")
      this
    }

  override def toString: String = {
    raw"""
      | \matrix {
      | ${graphics.grouped(N).map(_.filter(_.graphics.nonEmpty)).map(_.mkString("\n&\n")).mkString("\n \\\\[0.5cm]")}
      | \\
      | };
    """.stripMargin
  }
}

object Figure {

  private val UNNAMED = ""

  /**
    * Creates an empty figure.
    *
    * @param name a name for the figure
    * @return a Figure instance
    */
  def apply(name: String): Figure =
    new Figure(Iterator.continually(Color.values).flatten, name, Axis(), CARTESIAN, List.empty[PGFPlot], None)

  /**
    * Creates an empty figure array.
    *
    * @param name a name for the figure
    * @param N number of rows
    * @param M number of columns
    * @return a FigureArray instance
    */
  def apply(name: String, N: Int, M: Int): FigureArray =
    new FigureArray(name, IndexedSeq.fill(N * M)(Figure(UNNAMED)), N, M)
}
