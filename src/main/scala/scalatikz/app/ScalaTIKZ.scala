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

package scalatikz.app

import java.io.File
import scala.util.{ Failure, Success }
import scalatikz.app.GraphType._
import scalatikz.common.CSV
import scalatikz.pgf.plots.DataTypes._
import scalatikz.pgf.plots.enums._
import scalatikz.pgf.plots.enums.AxisLinePos.BOX
import scalatikz.pgf.plots.enums.FontSize.NORMAL
import scalatikz.pgf.plots.enums.LineSize.THIN
import scalatikz.pgf.plots.enums.LineStyle.SOLID
import scalatikz.pgf.plots.enums.Mark.{ CIRCLE, NONE }
import scalatikz.pgf.plots.enums.Pattern.PLAIN
import scalatikz.pgf.Compiler

object ScalaTIKZ extends AppCLI[Conf]("scalatikz") {

  // An iterator over available colors in case user does not specify one.
  private val colorIterator = Iterator.continually(Color.values).flatten
  private var currentColor: Color = Color.values.head

  private def nextColor: Color = {
    currentColor = colorIterator.next
    currentColor
  }

  // General options

  note("General options:\n".underlined.cyan.bold)

  opt[String]('n', "name".underlined).valueName("<string>".bold).optional.unbounded
    .action { (name, conf) =>
      if (conf.figure.name != DEFAULT_NAME) fatal(s"Name is already defined to '${conf.figure.name}'")
      else conf.copy(figure = conf.figure.havingName(name))
    }.text("Specify a name for the figure (default is 'result').\n")

  opt[String]('o', "output".underlined).valueName("<output path>".bold).optional.unbounded
    .action { (path, conf) =>
      if (conf.output != System.getProperty("user.dir")) fatal(s"Output path is already defined to '${conf.output}'")
      else conf.copy(output = path)
    }.validate { path =>
      if (new File(path).isDirectory) success
      else failure(s"Path '$path' is not a valid directory.")
    }.text("Specify a path for the output directory (default is the current directory).\n")

  opt[String]('F', "format".underlined).valueName("<PDF|PNG|JPEG|TEX>".bold)
    .action((format, conf) => conf.copy(format = format))
    .text("Output format (default is PDF).")
    .validate {
      case "PDF" | "PNG" | "JPEG" | "TEX" => success
      case _ => failure("Format should be PDF or PNG or JPEG or TEX.")
    }

  // Plot types

  note("\nPlot types:\n".underlined.cyan.bold)

  opt[Unit]('P', "plot".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(PLOT)))
    .text("Plots a 2D line of the data in Y versus the corresponding values in X.")

  opt[Unit]('M', "stem".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(STEM)))
    .text("Plots a data sequence as stems emerging from a baseline along the x-axis.")

  opt[Unit]('A', "area".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(AREA)))
    .text("Plots a data sequence as a 2D line and fills the area beneath the curve.")

  opt[Unit]('C', "scatter".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(SCATTER)))
    .text("Plots a scatter of points at the locations specified by the data sequence.")

  opt[Unit]('T', "stair".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(STAIR)))
    .text("Plots a data sequence as a 2D stair step.")

  opt[Unit]('W', "error-area".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(ERROR_AREA)))
    .text("Plots a data sequence as a 2D line along an error area around the data points.")

  opt[Unit]('R', "error-bar".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(ERROR_BAR)))
    .text("Plots a data sequence as a 2D line along vertical and/or horizontal error bars at each data point.")

  opt[Unit]('B', "bar".underlined).unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(BAR)))
    .text("Plots a data sequence as a 2D bars at each data point.")

  // Input data options

  note("\nInput data options:\n".underlined.cyan.bold)

  opt[String]('i', "input".underlined).valueName("<csv file>".bold).required.unbounded
    .action((f, conf) => conf.copy(inputs = conf.inputs :+ f))
    .text("Specify an input CSV data file (required).\n")
    .validate { csv =>
      if (new File(csv).isFile) success
      else failure(s"File '$csv' does not exists.")
    }

  opt[Char]('d', "del".underlined).valueName("<character>".bold).optional.unbounded
    .action((d, conf) => conf.copy(delimiters = conf.delimiters :+ d))
    .text("Specify the delimiter of the input csv data file (default is comma)." +
      "\n\tNote: ".green.bold + "In case you need to specify TAB as a delimiter, please just type $'\\t'\n")

  opt[String]('x', "x-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (xColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xColumn = Some(xColumn)))
    }.text("X column name or column index (default is the indexes of the y values)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('y', "y-column".underlined).valueName("<index or name>".bold).required.unbounded
    .action { (yColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(yColumn = Some(yColumn)))
    }.text("Y column name or column index (required)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('e', "x-error-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (xErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xErrorColumn = Some(xErrorColumn)))
    }.text("X error column index or column name (optional)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]('E', "y-error-column".underlined).valueName("<index or name>".bold).optional.unbounded
    .action { (yErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(yErrorColumn = Some(yErrorColumn)))
    }.text("Y error column index or column name (optional)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.")

  // Plot options

  note("\nPlot options:".underlined.cyan.bold)
  note("CAUTION: ".red + "These options should appear only after a plot type option.\n")

  opt[Color]('c', "color".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineColor = Some(color)))
    }.text(s"Line color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Mark]('m', "marker".underlined).valueName("<mark>".bold).unbounded.optional
    .action { (mark, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a marker option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(marker = Some(mark)))
    }.text(s"Point marker. Available markers: ${Mark.values.tail.mkString(", ")}\n")

  opt[Color]('k', "mark-stroke".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark stroke color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markStrokeColor = Some(color)))
    }.text(s"Mark stroke color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Color]('f', "mark-fill".underlined).valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark fill color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markFillColor = Some(color)))
    }.text(s"Mark fill color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol #. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Double]('s', "mark-size".underlined).valueName("<double>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markSize = size))
    }.text("Marker size (default is 1 pt).\n")

  opt[LineSize]('S', "line-size".underlined).valueName("<size>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineSize = Some(size)))
    }.text(s"Set line size (default is thin)." +
      s"\n\t${"Available line sizes:".green.bold} ${LineSize.values.mkString(", ")}\n")

  opt[LineStyle]('b', "line-style".underlined).valueName("<style>".bold).unbounded.optional
    .action { (style, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line style option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineStyle = Some(style)))
    }.text(s"Set line style (default is solid)." +
      s"\n\t${"Available line styles:".green.bold} ${LineStyle.values.mkString(", ")}\n")

  opt[Pattern]('U', "pattern".underlined).valueName("<pattern>".bold).unbounded.optional
    .action { (pattern, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a pattern option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(pattern = Some(pattern)))
    }.text(s"Set pattern (default is plain)." +
      s"\n\t${"Available patterns:".green.bold} ${Pattern.values.mkString(", ")}\n")

  opt[Double]('w', "bar-width".underlined).valueName("<double>".bold).unbounded.optional
    .action { (width, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a bar width option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(barWidth = width))
    }.text("Bar width (default is 0.5 pt).\n")

  opt[Unit]('h', "smooth".underlined).unbounded.optional
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a smooth option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(smooth = true))
    }.text("Smooth lines.\n")

  opt[Double]('O', "opacity".underlined).valueName("<double>".bold).unbounded.optional
    .action { (opacity, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before an opacity option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(opacity = opacity))
    }.text("Set the opacity of the area under a curve or the bars.\n")

  opt[Unit]('r', "constant-area".underlined).unbounded.optional
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a constant area option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(constant = true))
    }.text("Enable constant area plots.")

  // Figure options

  note("\nFigure options:\n".underlined.cyan.bold)

  opt[String]('t', "title".underlined).valueName("<string>".bold).optional.unbounded
    .action { (title, conf) =>
      conf.copy(figure = conf.figure.havingTitle(title))
    }.text("Specify a title for the figure (optional).\n")

  opt[String]('X', "X-label".underlined).valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingXLabel(label))
    }.text("Specify a X label (optional).\n")

  opt[String]('Y', "Y-label".underlined).valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingYLabel(label))
    }.text("Specify a Y label (optional).\n")

  opt[Unit]('J', "majorGridOn".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingMajorGridOn))
    .text("Enables major grid (optional).\n")

  opt[Unit]('N', "minorGridOn").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingMinorGridOn))
    .text("Enables minor grid (optional).\n")

  opt[Unit]('G', "bothGridOn".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingGridsOn))
    .text("Enables both minor and major grids (optional).\n")

  opt[FontSize]('u', "font-size".underlined).valueName("<size>".bold).optional.unbounded
    .action { (size, conf) =>
      conf.copy(figure = conf.figure.havingFontSize(size))
    }.text(s"Set figure's font size (default is $NORMAL). " +
      s"\n\t${"Available font sizes:".green.bold} ${FontSize.values.mkString(", ")}\n")

  opt[Seq[Double]]('a', "axis".underlined).valueName("<double,double,double,double>".bold).optional.unbounded
    .action((limits, conf) => conf.copy(figure = conf.figure.havingLimits(limits.head, limits(1), limits(2), limits(3))))
    .text("Set X and Y axis limits as comma separated values: x minimum,x maximum,y minimum,y maximum.\n")
    .validate { seq =>
      if (seq.length == 4) success
      else failure("Axis limits should be exactly 4.")
    }

  opt[AxisLinePos]('q', "x-axis-position".underlined).valueName("<position>".bold).optional.unbounded
    .action { (position, conf) =>
      conf.copy(figure = conf.figure.havingXAxisLinePos(position))
    }.text(s"Set figure's X axis position (default is $BOX). " +
      s"\n\t${"Available axis positions:".green.bold} ${AxisLinePos.values.mkString(", ")}\n")

  opt[AxisLinePos]('Q', "y-axis-position".underlined).valueName("<position>".bold).optional.unbounded
    .action { (position, conf) =>
      conf.copy(figure = conf.figure.havingYAxisLinePos(position))
    }.text(s"Set figure's Y axis position (default is $BOX). " +
      s"\n\t${"Available axis positions:".green.bold} ${AxisLinePos.values.mkString(", ")}\n")

  opt[Seq[Int]]('j', "ticks-rotation".underlined).valueName("<int,int>".bold).optional.unbounded
    .action((degrees, conf) => conf.copy(figure = conf.figure.rotateXTicks(degrees.head).rotateYTicks(degrees.last)))
    .text("Rotate X and Y axis ticks: x axis degrees,y axis degrees.\n")
    .validate { seq =>
      if (seq.length == 2) success
      else failure("Axis degree values should be exactly 2.")
    }

  opt[Seq[String]]('I', "x-tick-labels".underlined).valueName("<comma separated labels>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingAxisXLabels(x)))
    .text("Comma separated labels for the X axis ticks.\n")

  opt[Seq[String]]('K', "y-tick-labels".underlined).valueName("<comma separated labels>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingAxisYLabels(x)))
    .text("Comma separated labels for the Y axis ticks.\n")

  opt[Unit]('z', "hideXTicks".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.hideXAxisTicks))
    .text("Hides the ticks on the X axis (default is false).\n")

  opt[Unit]('Z', "hideYTicks".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.hideYAxisTicks))
    .text("Hides the ticks on the Y axis (default is false).\n")

  opt[Seq[String]]('g', "legends".underlined).valueName("<comma separated legends>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingLegends(x: _*)))
    .text("Comma separated legends for the plotted data.\n")

  opt[LegendPos]('p', "legend-pos".underlined).valueName("<position>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingLegendPos(x)))
    .text(s"Change legend panel position (default is outer north east)." +
      s"\n\t${"Available legend positions:".green.bold} ${LegendPos.values.mkString(", ")}\n")

  opt[Unit]('l', "logX".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingLogXAxis))
    .text("Enables logarithmic X scale (default is linear).\n")

  opt[Unit]('L', "logY".underlined).optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingLogYAxis))
    .text("Enables logarithmic Y scale (default is linear).\n")

  opt[Compiler]('V', "compiler".underlined).valueName("<compiler>".bold).optional.unbounded
    .action((x, conf) => conf.copy(compiler = x))
    .text(s"Change the underlying compiler (default is pdflatex)." +
      s"\n\t${"Available compilers:".green.bold} ${Compiler.values.mkString(", ")}\n")

  help("help").text("Print usage options.\n")

  version("version").text("Display the version.")

  /*
   * Parse arguments
   */
  parse(args, Conf()) match {
    case Some(conf) if conf.graphics.nonEmpty =>

      var resultedFigure = conf.figure

        def notFound[T](seq: IndexedSeq[T])(index: Int): String = {
          logger.info(s"CSV not found for plot type ${index + 1}. Using '${conf.inputs.last}'.")
          conf.inputs.last
        }

      conf.graphics.zipWithIndex.foreach {
        case (graphic, i) =>

          val coordinates: Coordinates =
            (CSV.parseColumns(
              new File(conf.inputs.applyOrElse(i, notFound(conf.inputs))),
              conf.delimiters.applyOrElse(i, (_: Int) => ','),
              Seq(graphic.xColumn, graphic.yColumn).flatten: _*): @unchecked) match {

              case Success(y :: Nil) => (1 to y.length).map(_.toDouble) zip y
              case Success(x :: y :: Nil) => x zip y
              case Failure(ex) => fatal(s"${ex.getMessage}")
            }

          graphic.graph match {
            case PLOT =>

              resultedFigure =
                resultedFigure.plot(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  smooth          = graphic.smooth
                )(coordinates)

            case STEM =>

              resultedFigure =
                resultedFigure.stem(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize
                )(coordinates)

            case SCATTER =>

              resultedFigure =
                resultedFigure.scatter(
                  marker          = graphic.marker.getOrElse(CIRCLE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize
                )(coordinates)

            case AREA =>

              resultedFigure =
                resultedFigure.area(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  pattern         = graphic.pattern.getOrElse(PLAIN),
                  opacity         = graphic.opacity,
                  smooth          = graphic.smooth,
                  constant        = graphic.constant
                )(coordinates)

            case STAIR =>

              resultedFigure =
                resultedFigure.stair(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN)
                )(coordinates)

            case ERROR_AREA =>

              val error: Coordinates =
                (CSV.parseColumns(
                  new File(conf.inputs.applyOrElse(i, notFound(conf.inputs))),
                  conf.delimiters.applyOrElse(i, (_: Int) => ','),
                  Seq(graphic.xErrorColumn, graphic.yErrorColumn).flatten: _*): @unchecked) match {

                  case Success(Nil) =>
                    val zeroError = Seq.fill(coordinates.length)(0.0)
                    zeroError zip zeroError

                  case Success(y :: Nil) if graphic.xErrorColumn.isEmpty =>
                    Seq.fill(coordinates.length)(0.0) zip y

                  case Success(x :: Nil) if graphic.yErrorColumn.isEmpty =>
                    x zip Seq.fill(coordinates.length)(0.0)

                  case Success(x :: y :: Nil) => x zip y
                  case Failure(ex) => fatal(s"${ex.getMessage}")
                }

              resultedFigure =
                resultedFigure.errorArea(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  opacity         = graphic.opacity,
                  smooth          = graphic.smooth
                )(coordinates)(error)

            case ERROR_BAR =>

              val error: Coordinates =
                (CSV.parseColumns(
                  new File(conf.inputs.applyOrElse(i, notFound(conf.inputs))),
                  conf.delimiters.applyOrElse(i, (_: Int) => ','),
                  Seq(graphic.xErrorColumn, graphic.yErrorColumn).flatten: _*): @unchecked) match {

                  case Success(Nil) =>
                    val zeroError = Seq.fill(coordinates.length)(0.0)
                    zeroError zip zeroError

                  case Success(y :: Nil) if graphic.xErrorColumn.isEmpty =>
                    Seq.fill(coordinates.length)(0.0) zip y

                  case Success(x :: Nil) if graphic.yErrorColumn.isEmpty =>
                    x zip Seq.fill(coordinates.length)(0.0)

                  case Success(x :: y :: Nil) => x zip y
                  case Failure(ex) => fatal(s"${ex.getMessage}")
                }

              resultedFigure =
                resultedFigure.errorBar(
                  color           = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  smooth          = graphic.smooth
                )(coordinates)(error)

            case BAR =>

              resultedFigure =
                resultedFigure.bar(
                  color     = graphic.lineColor.getOrElse(nextColor),
                  pattern   = graphic.pattern.getOrElse(PLAIN),
                  lineStyle = graphic.lineStyle.getOrElse(SOLID),
                  lineSize  = graphic.lineSize.getOrElse(THIN),
                  opacity   = graphic.opacity,
                  barWidth  = graphic.barWidth
                )(coordinates)
          }
      }

      // Try to save the resulted figure in the requested FORMAT.
      val result = conf.format.toUpperCase match {
        case "PDF" => resultedFigure.saveAsPDF(conf.output, conf.compiler)
        case "PNG" => resultedFigure.saveAsPNG(conf.output, conf.compiler)
        case "JPEG" => resultedFigure.saveAsJPEG(conf.output, conf.compiler)
        case "TEX" => resultedFigure.saveAsTeX(conf.output)
      }
      result match {
        case Success(f) =>
          logger.info(s"${conf.format} successfully saved in $f.")
        case Failure(ex) =>
          fatal(s"${conf.format} cannot be saved: ${ex.getMessage}")
      }

    case Some(conf) if conf.graphics.isEmpty =>
      reportError("Please specify a plot type.")
      showTryHelp()

    case None => // do nothing, option parser should handle the case
  }
}
