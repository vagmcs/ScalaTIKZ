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
import scalatikz.pgf.enums.LineSize.THIN
import scalatikz.pgf.enums.LineStyle.SOLID
import scalatikz.pgf.plots.enums.LineType.SHARP
import scalatikz.pgf.plots.enums.Mark.{ CIRCLE, NONE }
import scalatikz.pgf.plots.enums.Pattern.PLAIN
import scalatikz.pgf.Compiler
import scalatikz.pgf.enums.{ Color, LineSize, LineStyle }

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

  opt[String]('n', "name").valueName("<string>".bold).optional.unbounded
    .action { (name, conf) =>
      if (conf.figure.name != DEFAULT_NAME) fatal(s"Name is already defined to '${conf.figure.name}'")
      else conf.copy(figure = conf.figure.havingName(name))
    }.text("Specify a name for the figure (default is 'result').\n")

  opt[String]('o', "output").valueName("<output path>".bold).optional.unbounded // TODO remove underlined
    .action { (path, conf) =>
      if (conf.output != System.getProperty("user.dir")) fatal(s"Output path is already defined to '${conf.output}'")
      else conf.copy(output = path)
    }.validate { path =>
      if (new File(path).isDirectory) success
      else failure(s"Path '$path' is not a valid directory.")
    }.text("Specify a path for the output directory (default is the current directory).\n")

  opt[String]('F', "format").valueName("<PDF|PNG|JPEG|TEX>".bold)
    .action((format, conf) => conf.copy(format = format))
    .text("Output format (default is PDF).\n")
    .validate {
      case "PDF" | "PNG" | "JPEG" | "TEX" => success
      case _ => failure("Format should be PDF or PNG or JPEG or TEX.")
    }

  opt[Compiler]("compiler").valueName("<compiler>".bold).optional.unbounded
    .action((c, conf) => conf.copy(compiler = c))
    .text(s"Change the underlying compiler (default is pdflatex)." +
      s"\n\t${"Available compilers:".green.bold} ${Compiler.values.mkString(", ")}")

  // Plot types

  note("\nPlot types:\n".underlined.cyan.bold)

  opt[Unit]('P', "plot").unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(PLOT)))
    .text("Plots a 2D line of the data in Y versus the corresponding values in X.")

  opt[Unit]('S', "scatter").unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(SCATTER)))
    .text("Plots a scatter of points at the locations specified by the data sequence.")

  opt[Unit]('M', "mesh").unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(MESH)))
    .text("Plots a scatter mesh of the points at the locations specified by the data sequence.")

  opt[Unit]('T', "stem").unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(STEM)))
    .text("Plots a data sequence as stems emerging from a baseline along the x-axis.")

  opt[Unit]('B', "bar").unbounded
    .action((_, conf) => conf.copy(graphics = conf.graphics :+ GraphicConf(BAR)))
    .text("Plots a data sequence as a 2D bars at each data point.")

  // Input data options

  note("\nInput data options:\n".underlined.cyan.bold)

  opt[String]('i', "input").valueName("<csv file>".bold).required.unbounded
    .action((f, conf) => conf.copy(inputs = conf.inputs :+ f))
    .text("Specify an input CSV data file (required).\n")
    .validate { csv =>
      if (new File(csv).isFile) success
      else failure(s"File '$csv' does not exists.")
    }

  opt[Char]("delim").valueName("<character>".bold).optional.unbounded
    .action((d, conf) => conf.copy(delimiters = conf.delimiters :+ d))
    .text("Specify the delimiter of the input csv data file (default is comma)." +
      "\n\tNote: ".green.bold + "In case you need to specify TAB as a delimiter, just type $'\\t'.\n")

  opt[String]('x', "x-column").valueName("<index or name>".bold).optional.unbounded
    .action { (xColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xColumn = Some(xColumn)))
    }.text("X column name or column index (default is the indexes of the y values)." +
      s"\n\t${"Note:".green.bold} Indices start at 0.\n")

  opt[String]('y', "y-column").valueName("<index or name>".bold).required.unbounded
    .action { (yColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(yColumn = Some(yColumn)))
    }.text("Y column name or column index (required)." +
      s"\n\t${"Note:".green.bold} Indices start at 0.\n")

  opt[String]("x-error-column").abbr("ex").valueName("<index or name>".bold).optional.unbounded
    .action { (xErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a x error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(xErrorColumn = Some(xErrorColumn)))
    }.text("X error column index or column name (optional)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.\n")

  opt[String]("y-error-column").abbr("ey").valueName("<index or name>".bold).optional.unbounded
    .action { (yErrorColumn, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a y error column option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(yErrorColumn = Some(yErrorColumn)))
    }.text("Y error column index or column name (optional)." +
      s"\n\t${"Note:".green.bold} Indexes start from 0.")

  // Plot options

  note("\nPlot options:".underlined.cyan.bold)
  note("CAUTION: ".red + "These options should appear only after a plot type option.\n")

  opt[Color]('c', "color").valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineColor = Some(color)))
    }.text(s"Line color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol '#'. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Color]("fill-color").abbr("fc").valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(fillColor = Some(color)))
    }.text(s"Line color. Available fill colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol '#'. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Mark]('m', "marker").valueName("<mark>".bold).unbounded.optional
    .action { (mark, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a marker option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(marker = Some(mark)))
    }.text(s"Point marker. Available markers: ${Mark.values.tail.mkString(", ")}\n")

  opt[Color]("mark-stroke").abbr("ms").valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark stroke color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markStrokeColor = Some(color)))
    }.text(s"Mark stroke color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol '#'. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Color]("mark-fill").abbr("mf").valueName("<color>".bold).unbounded.optional
    .action { (color, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark fill color option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markFillColor = Some(color)))
    }.text(s"Mark fill color. Available line colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol '#'. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  opt[Double]("mark-size").abbr("mz").valueName("<double>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a mark size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(markSize = size))
    }.text("Marker size (default is 1 pt).\n")

  opt[LineType]("line-type").abbr("lt").valueName("<type>".bold).unbounded.optional
    .action { (typ, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineType = Some(typ)))
    }.text(s"Set line size (default is sharp)." +
      s"\n\t${"Available line types:".green.bold} ${LineType.values.mkString(", ")}\n")

  opt[LineSize]("line-size").abbr("lz").valueName("<size>".bold).unbounded.optional
    .action { (size, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line size option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineSize = Some(size)))
    }.text(s"Set line size (default is thin)." +
      s"\n\t${"Available line sizes:".green.bold} ${LineSize.values.mkString(", ")}\n")

  opt[LineStyle]("line-style").abbr("ls").valueName("<style>".bold).unbounded.optional
    .action { (style, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a line style option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(lineStyle = Some(style)))
    }.text(s"Set line style (default is solid)." +
      s"\n\t${"Available line styles:".green.bold} ${LineStyle.values.mkString(", ")}\n")

  opt[Pattern]('p', "pattern").valueName("<pattern>".bold).unbounded.optional
    .action { (pattern, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a pattern option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(pattern = Some(pattern)))
    }.text(s"Set pattern (default is plain)." +
      s"\n\t${"Available patterns:".green.bold} ${Pattern.values.mkString(", ")}\n")

  opt[Double]("bar-width").abbr("bw").valueName("<double>".bold).unbounded.optional
    .action { (width, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before a bar width option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(barWidth = width))
    }.text("Bar width (default is 0.5 pt).\n")

  opt[Double]('o', "opacity").valueName("<double>".bold).unbounded.optional
    .action { (opacity, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before an opacity option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(opacity = opacity))
    }.text("Set the opacity of the area under a curve or the bars.\n")

  opt[Unit]("horizontal-bars").abbr("hb").optional.unbounded
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type before an alignment option.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(horizontalAlignment = true))
    }.text("Place a bar plot in horizontal alignment (optional).\n")

  opt[Unit]("adjacent-bars").abbr("ab").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingAdjacentBars))
    .text("Place multiple bar plots adjacent to each other instead of stacking them (optional).\n")

  opt[Unit]("nodes-near-coords").abbr("nnr").optional.unbounded
    .action { (_, conf) =>
      if (conf.graphics.isEmpty) fatal("You must define a plot type in order to enable coordinate text nodes.")
      else conf.copy(graphics = conf.graphics.init :+ conf.graphics.last.copy(nodesNearCoords = true))
    }.text("Place text nodes near every coordinate (optional).")

  // Figure options

  note("\nFigure options:\n".underlined.cyan.bold)

  opt[String]('t', "title").valueName("<string>".bold).optional.unbounded
    .action { (title, conf) =>
      conf.copy(figure = conf.figure.havingTitle(title))
    }.text("Specify a title for the figure (optional).\n")

  opt[String]('X', "X-label").valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingXLabel(label))
    }.text("Specify a X label (optional).\n")

  opt[String]('Y', "Y-label").valueName("<string>".bold).optional.unbounded
    .action { (label, conf) =>
      conf.copy(figure = conf.figure.havingYLabel(label))
    }.text("Specify a Y label (optional).\n")

  opt[FontSize]("font-size").abbr("fz").valueName("<size>".bold).optional.unbounded
    .action { (size, conf) =>
      conf.copy(figure = conf.figure.havingFontSize(size))
    }.text(s"Set figure's font size (default is $NORMAL). " +
      s"\n\t${"Available font sizes:".green.bold} ${FontSize.values.mkString(", ")}\n")

  opt[Unit]("minor-grid-on").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingMinorGridOn))
    .text("Enables minor grid (optional).\n")

  opt[Unit]("major-grid-on").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingMajorGridOn))
    .text("Enables major grid (optional).\n")

  opt[Unit]("both-grids-on").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingGridsOn))
    .text("Enables both minor and major grids (optional).\n")

  opt[Seq[Double]]("axis").valueName("<double,double,double,double>".bold).optional.unbounded
    .action((limits, conf) => conf.copy(figure = conf.figure.havingLimits(limits.head, limits(1), limits(2), limits(3))))
    .text("Set X and Y axis limits as comma separated values: x minimum,x maximum,y minimum,y maximum.\n")
    .validate { seq =>
      if (seq.length == 4) success
      else failure("Axis limits should be exactly 4.")
    }

  opt[Seq[Double]]("x-lim").valueName("<double,double>".bold).optional.unbounded
    .action((limits, conf) => conf.copy(figure = conf.figure.havingXLimits(limits.head, limits.last)))
    .text("Set X axis limits as comma separated values: x minimum,x maximum.\n")
    .validate { seq =>
      if (seq.length == 2) success
      else failure("x-axis limits should be exactly 2.")
    }

  opt[Seq[Double]]("y-lim").valueName("<double,double>".bold).optional.unbounded
    .action((limits, conf) => conf.copy(figure = conf.figure.havingYLimits(limits.head, limits.last)))
    .text("Set Y axis limits as comma separated values: x minimum,x maximum.\n")
    .validate { seq =>
      if (seq.length == 2) success
      else failure("y-axis limits should be exactly 2.")
    }

  opt[AxisLinePos]("x-axis-position").valueName("<position>".bold).optional.unbounded
    .action { (position, conf) =>
      conf.copy(figure = conf.figure.havingXAxisLinePos(position))
    }.text(s"Set figure's X axis position (default is $BOX). " +
      s"\n\t${"Available axis positions:".green.bold} ${AxisLinePos.values.mkString(", ")}\n")

  opt[AxisLinePos]("y-axis-position").valueName("<position>".bold).optional.unbounded
    .action { (position, conf) =>
      conf.copy(figure = conf.figure.havingYAxisLinePos(position))
    }.text(s"Set figure's Y axis position (default is $BOX). " +
      s"\n\t${"Available axis positions:".green.bold} ${AxisLinePos.values.mkString(", ")}\n")

  opt[Seq[Int]]('r', "rotate-ticks").valueName("<int,int>".bold).optional.unbounded
    .action((degrees, conf) => conf.copy(figure = conf.figure.rotateXTicks(degrees.head).rotateYTicks(degrees.last)))
    .text("Rotate X and Y axis ticks: x axis degrees, y axis degrees.\n")
    .validate { seq =>
      if (seq.length == 2) success
      else failure("Axis degree values should be exactly 2.")
    }

  opt[Seq[String]]("x-tick-labels").valueName("<comma separated labels>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingAxisXLabels(x)))
    .text("Comma separated labels for the X axis ticks.\n")

  opt[Seq[String]]("y-tick-labels").valueName("<comma separated labels>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingAxisYLabels(x)))
    .text("Comma separated labels for the Y axis ticks.\n")

  opt[Unit]("hide-x-ticks").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.hideXAxisTicks))
    .text("Hides the ticks on the X axis (default is false).\n")

  opt[Unit]("hide-y-ticks").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.hideYAxisTicks))
    .text("Hides the ticks on the Y axis (default is false).\n")

  opt[Unit]("scale-x-ticks").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.scaleXTicks))
    .text("Scales the ticks on the X axis (default is false).\n")

  opt[Unit]("scale-y-ticks").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.scaleYTicks))
    .text("Scales the ticks on the Y axis (default is false).\n")

  opt[Seq[String]]("legends").abbr("lg").valueName("<comma separated legends>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingLegends(x: _*)))
    .text("Comma separated legends for the plotted data.\n")

  opt[LegendPos]("legend-pos").abbr("lp").valueName("<position>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingLegendPos(x)))
    .text(s"Change legend panel position (default is outer north east)." +
      s"\n\t${"Available legend positions:".green.bold} ${LegendPos.values.mkString(", ")}\n")

  opt[Int]("legend-cols").abbr("lc").valueName("<int>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingLegendColumns(x)))
    .text("Set the number of columns in the legend.\n")
    .validate { x =>
      if (x > 0) success
      else failure("Number of columns should be a positive number.")
    }

  opt[FontSize]("legend-font-size").abbr("lf").valueName("<size>".bold).optional.unbounded
    .action { (size, conf) =>
      conf.copy(figure = conf.figure.havingLegendFontSize(size))
    }.text(s"Set legend font size (default is $NORMAL). " +
      s"\n\t${"Available font sizes:".green.bold} ${FontSize.values.mkString(", ")}\n")

  opt[Unit]("x-log-scale").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingLogXAxis))
    .text("Enables logarithmic X scale (default is linear).\n")

  opt[Unit]("y-log-scale").optional.unbounded
    .action((_, conf) => conf.copy(figure = conf.figure.havingLogYAxis))
    .text("Enables logarithmic Y scale (default is linear).\n")

  opt[Double]("height").valueName("<double>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingHeight(x)))
    .text(s"Set the figure height.\n")
    .validate { x =>
      if (x > 0) success
      else failure("Height should be positive.")
    }

  opt[Double]("width").valueName("<double>".bold).optional.unbounded
    .action((x, conf) => conf.copy(figure = conf.figure.havingWidth(x)))
    .text(s"Set the figure width.\n")
    .validate { x =>
      if (x > 0) success
      else failure("Width should be positive.")
    }

  opt[ColorMap]("color-map").abbr("cmap").valueName("<color map>".bold).optional.unbounded
    .action((colorMap, conf) => conf.copy(figure = conf.figure.havingColorMap(colorMap)))
    .text(s"Color map. Available color maps: ${ColorMap.values.mkString(", ")}\n")

  opt[Color]("background-color").abbr("bc").valueName("<color>".bold).unbounded.optional
    .action((color, conf) => conf.copy(figure = conf.figure.havingBackgroundColor(color)))
    .text(s"Background color. Available colors: ${Color.values.mkString(", ")}" +
      "\n\tNote: ".green.bold + "Colors can also be mixed by using the symbol '#'. " +
      s"For instance ${"red#20#green".underlined} defines a color that has 20% red and 80% green.\n")

  help("help").text("Print usage options.\n")

  version("version").text("Display the version.")

  /*
   * Parse arguments
   */
  parse(args, Conf()) match {
    case Some(conf) if conf.graphics.nonEmpty =>

      var resultedFigure = conf.figure

        /**
          * A helper function for printing info messages and returning the
          * last entry of the given sequence.
          *
          * @param seq a sequence of filenames
          * @param index the index of the graphic
          * @tparam T the type of the indexed sequence
          * @return the last entry in the given sequence
          */
        @inline
        def notFound[T](seq: IndexedSeq[T])(index: Int): T = {
          logger.info(s"CSV file not found for plot type ${index + 1}. Using '${seq.last}'.")
          seq.last
        }

      conf.graphics.zipWithIndex.foreach {
        case (graphic, i) =>

          // parse input CSV and read coordinates
          val coordinates: Coordinates2D =
            (CSV.parseColumns(
              new File(conf.inputs.applyOrElse(i, i => notFound(conf.inputs)(i))),
              conf.delimiters.applyOrElse(i, (_: Int) => ','), // the default delimiter is the comma
              Seq(graphic.xColumn, graphic.yColumn).flatten: _*): @unchecked) match {

              case Success(y :: Nil) => (1 to y.length).map(_.toDouble) zip y
              case Success(x :: y :: Nil) => x zip y
              case Failure(ex) => fatal(s"${ex.getMessage}")
            }

          // parse input CSV and read error coordinates
          val error: Option[Coordinates2D] =
            (CSV.parseColumns(
              new File(conf.inputs.applyOrElse(i, i => notFound(conf.inputs)(i))),
              conf.delimiters.applyOrElse(i, (_: Int) => ','), // the default delimiter is the comma
              Seq(graphic.xErrorColumn, graphic.yErrorColumn).flatten: _*): @unchecked) match {

              case Success(Nil) => None // both zero x and y error

              case Success(y :: Nil) if graphic.xErrorColumn.isEmpty => Some( // zero x error
                Seq.fill(coordinates.length)(0.0) zip y
              )

              case Success(x :: Nil) if graphic.yErrorColumn.isEmpty => Some( // zero y error
                x zip Seq.fill(coordinates.length)(0.0)
              )

              case Success(x :: y :: Nil) => Some(x zip y)
              case Failure(ex) => fatal(s"${ex.getMessage}")
            }

          graphic.graph match {
            case PLOT =>

              resultedFigure =
                if (error.isDefined) {
                  if (graphic.fillColor.isEmpty) resultedFigure.errorPlot(
                    lineType        = graphic.lineType.getOrElse(SHARP),
                    lineColor       = graphic.lineColor.getOrElse(nextColor),
                    lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                    lineSize        = graphic.lineSize.getOrElse(THIN),
                    marker          = graphic.marker.getOrElse(NONE),
                    markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                    markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                    markSize        = graphic.markSize
                  )(coordinates)(error.get)
                  else resultedFigure.errorArea(
                    lineType        = graphic.lineType.getOrElse(SHARP),
                    lineColor       = graphic.lineColor.getOrElse(nextColor),
                    lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                    lineSize        = graphic.lineSize.getOrElse(THIN),
                    marker          = graphic.marker.getOrElse(NONE),
                    markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                    markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                    markSize        = graphic.markSize,
                    fillColor       = graphic.fillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                    opacity         = graphic.opacity
                  )(coordinates)(error.get)
                } else if (graphic.fillColor.isEmpty && graphic.pattern.isEmpty) resultedFigure.plot(
                  lineType        = graphic.lineType.getOrElse(SHARP),
                  lineColor       = graphic.lineColor.getOrElse(nextColor),
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize
                )(coordinates)
                else resultedFigure.plot(
                  lineType        = graphic.lineType.getOrElse(SHARP),
                  lineColor       = graphic.lineColor.getOrElse(nextColor),
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  pattern         = graphic.pattern.getOrElse(PLAIN),
                  fillColor       = graphic.fillColor.getOrElse(currentColor),
                  opacity         = graphic.opacity
                )(coordinates)

            case SCATTER =>

              resultedFigure =
                resultedFigure.scatter(
                  marker          = graphic.marker.getOrElse(CIRCLE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  nodesNearCoords = graphic.nodesNearCoords
                )(coordinates)

            case MESH =>

              resultedFigure =
                resultedFigure.scatterMesh(
                  marker   = graphic.marker.getOrElse(CIRCLE),
                  markSize = graphic.markSize
                )(coordinates)

            case STEM =>

              resultedFigure =
                resultedFigure.stem(
                  lineColor       = graphic.lineColor.getOrElse(nextColor),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  nodesNearCoords = graphic.nodesNearCoords,
                  horizontal      = graphic.horizontalAlignment
                )(coordinates)

            case BAR =>

              resultedFigure =
                if (error.isDefined) resultedFigure.errorBar(
                  barColor        = graphic.lineColor.getOrElse(nextColor),
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  pattern         = graphic.pattern.getOrElse(PLAIN),
                  opacity         = graphic.opacity,
                  barWidth        = graphic.barWidth,
                  horizontal      = graphic.horizontalAlignment
                )(coordinates)(error.get)
                else resultedFigure.bar(
                  barColor        = graphic.lineColor.getOrElse(nextColor),
                  lineStyle       = graphic.lineStyle.getOrElse(SOLID),
                  lineSize        = graphic.lineSize.getOrElse(THIN),
                  marker          = graphic.marker.getOrElse(NONE),
                  markStrokeColor = graphic.markStrokeColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markFillColor   = graphic.markFillColor.getOrElse(graphic.lineColor.getOrElse(currentColor)),
                  markSize        = graphic.markSize,
                  pattern         = graphic.pattern.getOrElse(PLAIN),
                  opacity         = graphic.opacity,
                  barWidth        = graphic.barWidth,
                  nodesNearCoords = graphic.nodesNearCoords,
                  horizontal      = graphic.horizontalAlignment
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
