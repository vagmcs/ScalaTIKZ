package scalatikz.examples

//import scalatikz.graphics.automaton.{Edge, FA, State}
import scalatikz.graphics.pgf.Figure
import scalatikz.graphics.pgf.enums.LineSize.VERY_THIN
import scalatikz.graphics.pgf.enums.{AxisLinePos, ColorMap}
import scalatikz.graphics.pgf.enums.Color._
import scalatikz.graphics.pgf.enums.LineType.{SMOOTH, STEPS}

import scala.math.{exp, log, sin}

object Test extends App {

//  val d = Seq[(Double, Double, Double)](
//    (0d,0d,0d), (1,0,0),   (2,0,0),   (3,0,0),
//      (0,1,0), (1,1,0.6), (2,1,0.7), (3,1,0.5),
//      (0,2,0), (1,2,0.7), (2,2,0.8), (3,2,0.5)
//  )
//
//  Figure("test").plot3()(d).show()

//  Figure("error_bar")
//    .errorBar(BLUE!50!BLACK) {
//      (0.0 to 1.0 by 0.1) -> ((x: Double) => x / 2.0)
//    } {
//      (1 to (0.0 to 1.0 by 0.1).length).map(_ => 0.0 -> scala.util.Random.nextDouble)
//    }
//    .havingTitle("Error bar")
//    .havingXAxisLinePos(AxisLinePos.BOTTOM)
//    .havingYAxisLinePos(AxisLinePos.LEFT)
//    .havingMajorGridOn
//    .havingXLimits(-0.1, 1.1)
//    .saveAsTeX(".")
//
//  sys.exit()

  val xx = -2*math.Pi to 2*math.Pi by 0.01

  Figure("test")
    .polar(lineSize = VERY_THIN, lineType = SMOOTH) {
      xx -> ((x: Double) => sin(x))
    }.show()

      sys.exit()

  Figure("test")
    .area(Seq(0,1,2,3))
    //.havingColorBar(ColorMap.BLUE_RED)
    .havingMinorGridOn
    .havingLegends("test")
    //.saveAsTeX(".")
      .show()
  sys.exit(0)

//  FA("test")
//    .state(State("q0").initial)
//    .state(State("q1").belowOf("q0"))
//    .state(State("q2").belowOf("q1").accepting)
//    .edge(Edge("q0", "q1", "a"))
//    .edge(Edge("q1", "q2", "b"))
//    .saveAsTeX(".")

  sys.exit(0)

  Figure.doubleAxis("test")
    .left { x =>
      x.plot((0.01 to 10.0 by 0.1) -> log _)
        //.havingXLimits(0.01, 10)
        //.havingYAxisLinePos(AxisLinePos.LEFT)
        .havingXAxisLinePos(AxisLinePos.BOTTOM)
    }
    .right { x =>
      x.plot((0.01 to 10.0 by 0.1) -> ((x: Double) => x))
        //.havingXLimits(0.01, 12)
        //.havingYAxisLinePos(AxisLinePos.RIGHT)
        .havingXAxisLinePos(AxisLinePos.TOP)
    }.show()//.saveAsTeX(".")

  // TODO I should enforce same x axis
  // TODO I should enforce left and right axis line pos
}
