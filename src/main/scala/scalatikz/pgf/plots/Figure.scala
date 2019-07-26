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
import scalatikz.pgf.plots.types._
import scalatikz.pgf.plots.DataTypes._
import scalatikz.pgf.plots.enums.AxisScale.{ LINEAR, LOG }
import scalatikz.pgf.plots.enums.AxisSystem.{ CARTESIAN, POLAR }
import scalatikz.pgf.plots.enums.GridStyle.{ BOTH, MAJOR, MINOR }
import scalatikz.pgf.plots.enums.LineSize.THIN
import scalatikz.pgf.plots.enums.LineStyle.SOLID
import scalatikz.pgf.plots.enums.LineType._
import scalatikz.pgf.plots.enums.Mark.NONE
import scalatikz.pgf.plots.enums.Pattern.PLAIN
import scalatikz.pgf.plots.enums._

final class Figure private (
    colorIterator: Iterator[Color],
    override val name: String,
    private[plots] val axis: Axis,
    private[plots] val axisType: AxisSystem,
    private[plots] val graphics: List[PGFPlot]) extends TIKZPicture {

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
  def havingName(name: String): Figure = new Figure(colorIterator, name, axis, axisType, graphics)

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
    new Figure(colorIterator, name, axis.copy(height = Some(centimeters)), axisType, graphics)

  /**
    * @param centimeters the width in centimeters
    * @return a Figure having the given width
    */
  def havingWidth(centimeters: Double): Figure =
    new Figure(colorIterator, name, axis.copy(width = Some(centimeters)), axisType, graphics)

  /**
    * @return a Figure having both X and Y log scale axis
    */
  def havingLogLogAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LOG, yMode = LOG), axisType, graphics)

  /**
    * @return a Figure having X log scale axis
    */
  def havingLogXAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LOG, yMode = LINEAR), axisType, graphics)

  /**
    * @return a Figure having Y log scale axis
    */
  def havingLogYAxis: Figure =
    new Figure(colorIterator, name, axis.copy(xMode = LINEAR, yMode = LOG), axisType, graphics)

  /**
    * @return a Figure having minor grid enabled
    */
  def havingMinorGridOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(MINOR)), axisType, graphics)

  /**
    * @return a Figure having major grid enabled
    */
  def havingMajorGridOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(MAJOR)), axisType, graphics)

  /**
    * @return a Figure having both major and minor grids enabled
    */
  def havingGridsOn: Figure =
    new Figure(colorIterator, name, axis.copy(grid = Some(BOTH)), axisType, graphics)

  /**
    * Sets a label on the X axis.
    *
    * @param label a label for the X axis
    * @return a Figure having the given label on the X axis
    */
  def havingXLabel(label: String): Figure =
    new Figure(colorIterator, name, axis.copy(xLabel = Some(label)), axisType, graphics)

  /**
    * Sets a label on the Y axis.
    *
    * @param label a label for the Y axis
    * @return a Figure having the given label on the Y axis
    */
  def havingYLabel(label: String): Figure =
    new Figure(colorIterator, name, axis.copy(yLabel = Some(label)), axisType, graphics)

  /**
    * Sets the bounds of the X axis.
    *
    * @param min X lower bound
    * @param max X upper bound
    * @return a Figure having the given bounds on the X axis
    */
  def havingXLimits(min: Double, max: Double): Figure =
    new Figure(colorIterator, name, axis.copy(xMin = Some(min), xMax = Some(max)), axisType, graphics)

  /**
    * Sets the bounds of the Y axis.
    *
    * @param min Y lower bound
    * @param max Y upper bound
    * @return a Figure having the given bounds on the Y axis
    */
  def havingYLimits(min: Double, max: Double): Figure =
    new Figure(colorIterator, name, axis.copy(yMin = Some(min), yMax = Some(max)), axisType, graphics)

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
    new Figure(colorIterator, name, axis.copy(xMin = Some(xMin), xMax = Some(xMax), yMin = Some(yMin), yMax = Some(yMax)), axisType, graphics)

  /**
    * Sets the figure title.
    *
    * @param title a figure title
    * @return a Figure having the given title
    */
  def havingTitle(title: String): Figure =
    new Figure(colorIterator, name, axis.copy(header = Some(title)), axisType, graphics)

  /**
    * Sets the font size.
    *
    * @param size a size
    * @return a Figure having the given font size
    */
  def havingFontSize(size: FontSize): Figure =
    new Figure(colorIterator, name, axis.copy(fontSize = Some(size)), axisType, graphics)

  /**
    * Sets the axis background color
    *
    * @see [[scalatikz.pgf.plots.enums.Color]]
    * @param color a color
    * @return a Figure having the given background color
    */
  def havingBackgroundColor(color: Color): Figure =
    new Figure(colorIterator, name, axis.copy(backgroundColor = color), axisType, graphics)

  /**
    * Sets the legends of the data sequences.
    *
    * @param legends a sequence of legends
    * @return a Figure having the given legends
    */
  def havingLegends(legends: String*): Figure =
    new Figure(colorIterator, name, axis.copy(legends = legends), axisType, graphics)

  /**
    * Sets the legends position.
    *
    * @see [[scalatikz.pgf.plots.enums.LegendPos]]
    * @param pos a legend position
    * @return a Figure having the given legend position
    */
  def havingLegendPos(pos: LegendPos): Figure =
    new Figure(colorIterator, name, axis.copy(legendPos = pos), axisType, graphics)

  /**
    * Sets the position of the X axis.
    *
    * @see [[scalatikz.pgf.plots.enums.AxisLinePos]]
    * @param pos an axis position
    * @return a Figure having the given position on the X axis
    */
  def havingXAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(colorIterator, name, axis.copy(xAxisLinePos = pos), axisType, graphics)

  /**
    * Sets the position of the Y axis.
    *
    * @see [[scalatikz.pgf.plots.enums.AxisLinePos]]
    * @param pos an axis position
    * @return a Figure having the given position on the Y axis
    */
  def havingYAxisLinePos(pos: AxisLinePos): Figure =
    new Figure(colorIterator, name, axis.copy(yAxisLinePos = pos), axisType, graphics)

  /**
    * Hides the ticks appearing in the X axis.
    *
    * @return a Figure having hidden ticks in the X axis
    */
  def hideXAxisTicks: Figure =
    new Figure(colorIterator, name, axis.copy(xAxisHideTicks = true), axisType, graphics)

  /**
    * Hides the ticks appearing in the Y axis.
    *
    * @return a Figure having hidden ticks in the Y axis
    */
  def hideYAxisTicks: Figure =
    new Figure(colorIterator, name, axis.copy(yAxisHideTicks = true), axisType, graphics)

  /**
    * Changes the X axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given X axis tick labels
    */
  def havingAxisXLabels(labels: Seq[String]): Figure =
    new Figure(colorIterator, name, axis.copy(xTickLabels = labels), axisType, graphics)

  /**
    * Changes the Y axis tick labels.
    *
    * @param labels a list of labels
    * @return a Figure having the given Y axis tick labels
    */
  def havingAxisYLabels(labels: Seq[String]): Figure =
    new Figure(colorIterator, name, axis.copy(yTickLabels = labels), axisType, graphics)

  /**
    * Rotates the X axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the X ticks rotated
    */
  def rotateXTicks(degrees: Int): Figure =
    new Figure(colorIterator, name, axis.copy(rotateXTicks = degrees), axisType, graphics)

  /**
    * Rotates the Y axis ticks by the given degrees.
    *
    * @param degrees the degrees to rotate the ticks
    * @return a Figure having the Y ticks rotated
    */
  def rotateYTicks(degrees: Int): Figure =
    new Figure(colorIterator, name, axis.copy(rotateYTicks = degrees), axisType, graphics)

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
    new Figure(colorIterator, name, axis, axisType, pgf :: graphics)

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
  def plot(data: Data): Figure = plot()(data)

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
      sparse: Boolean = false)(data: Data): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.sparse.coordinates else data.coordinates,
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
      ) :: graphics
    )

  /**
    * Plots a 2D line of the data in Y versus the corresponding values in X
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorPlot(data: Data)(error: Data): Figure = errorPlot()(data)(error)

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
      smooth: Boolean = false)(data: Data)(error: Data): Figure =
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
      ) :: graphics
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
  def area(data: Data): Figure = area()(data)

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
      sparse: Boolean = false)(data: Data): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.sparse.coordinates else data.coordinates,
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
      ) :: graphics
    )

  /**
    * Plots a 2D line of the data in X against the corresponding values in Y
    * along an error area around the 2D line.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorArea(data: Data)(error: Data): Figure = errorArea()(data)(error)

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
      smooth: Boolean = false)(data: Data)(error: Data): Figure =
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
      ) :: graphics
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
  def stem(data: Data): Figure = stem()(data)

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
      horizontal: Boolean = false)(data: Data): Figure =
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
      ) :: graphics
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
  def steps(data: Data): Figure = steps()(data)

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
      sparse: Boolean = false)(data: Data): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Line(
        if (sparse) data.sparse.coordinates else data.coordinates,
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
      ) :: graphics
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
  def scatter(data: Data): Figure = scatter()(data)

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
      nodesNearCoords: Boolean = false)(data: Data): Figure =
    new Figure(colorIterator, name, axis, axisType,
      Scatter(
        data.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        nodesNearCoords
      ) :: graphics
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
  def errorScatter(data: Data)(error: Data): Figure = errorScatter()(data)(error)

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
      markSize: Double = 1)(data: Data)(error: Data): Figure =
    new Figure(colorIterator, name, axis, axisType,
      ErrorScatter(
        data.coordinates,
        error.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize
      ) :: graphics
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
  def bar(data: Data): Figure = bar()(data)

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
      barWidth: Double = 0.5,
      nodesNearCoords: Boolean = false,
      horizontal: Boolean = false)(data: Data): Figure =
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
      ) :: graphics
    )

  /**
    * Creates 2D bars of the data in X against the corresponding values in Y
    * along vertical and/or horizontal error bars at each data point.
    *
    * @param data sequence of X, Y points in the Euclidean space
    * @param error sequence of X, Y error points
    */
  def errorBar(data: Data)(error: Data): Figure = errorBar()(data)(error)

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
      barWidth: Double = 0.5,
      horizontal: Boolean = false)(data: Data)(error: Data): Figure =
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
      ) :: graphics
    )

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
      ) :: graphics
    )

  def polarScatter(data: Data): Figure = polarScatter()(data)

  def polarScatter(
      marker: Mark = NONE,
      markStrokeColor: Color = nextColor,
      markFillColor: Color = currentColor,
      markSize: Double = 2)(data: Data): Figure =
    new Figure(colorIterator, name, axis, POLAR,
      Scatter(
        data.coordinates,
        marker,
        markStrokeColor,
        markFillColor,
        markSize,
        nodesNearCoords = false
      ) :: graphics
    )

  override def toString: String =
    raw"""
         |\${'begin.name}{$axisType}[
         |$axis
         |]
         |${graphics.mkString("\n")}
         |\end{$axisType}
    """.stripMargin
}

final class BipolarFigure private[pgf] (
    override val name: String,
    graphics: Seq[Figure]) extends TIKZPicture {

  def left(f: Figure => Figure): BipolarFigure = {
    val transformed = f(graphics.head)
    val _transformed = if (transformed.axis.yAxisLinePos != AxisLinePos.LEFT) {
      logger.warn("y axis line position should be left. Changing to left.")
      transformed.havingYAxisLinePos(AxisLinePos.LEFT)
    } else transformed
    new BipolarFigure(name, graphics.updated(0, _transformed))
  }

  def right(f: Figure => Figure): BipolarFigure = {
    val transformed = f(graphics.last)
    val _transformed = if (transformed.axis.yAxisLinePos != AxisLinePos.RIGHT) {
      logger.warn("y axis line position should be right. Changing to left.")
      transformed.havingYAxisLinePos(AxisLinePos.RIGHT)
    } else transformed
    new BipolarFigure(name, graphics.updated(1, _transformed))
  }

  override def toString: String =
    raw"""
      |\pgfplotsset{set layers}
      |${graphics.head}
      |${graphics.last}
    """.stripMargin
}

final class FigureArray private[pgf] (
    override val name: String,
    val graphics: Seq[Figure],
    N: Int, M: Int) extends TIKZPicture {

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
    new Figure(Iterator.continually(Color.values).flatten, name, Axis(), CARTESIAN, List.empty[PGFPlot])

  def doubleAxis(name: String): BipolarFigure =
    new BipolarFigure(name, Seq(
      Figure("").havingXAxisLinePos(AxisLinePos.BOTTOM).havingYAxisLinePos(AxisLinePos.LEFT),
      Figure("").havingXAxisLinePos(AxisLinePos.TOP).havingYAxisLinePos(AxisLinePos.RIGHT)
    ))

  def apply(name: String, N: Int, M: Int): FigureArray =
    new FigureArray(name, Seq.fill(N * M)(Figure("")), N, M)
}