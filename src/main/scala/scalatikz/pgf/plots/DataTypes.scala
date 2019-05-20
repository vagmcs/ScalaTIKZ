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

import Numeric.Implicits._

object DataTypes {

  // Point types in the Euclidean space
  type Point = Double
  type Point2D = (Point, Point)
  type Point3D = (Point, Point, Point)

  // Point sequence types
  type DataSeq = Seq[Point]
  type Coordinates2D = Seq[Point2D]
  type Coordinates3D = Seq[Point3D]

  /**
    * Data is a construct that automatically converts various inputs into the
    * underlying data sequences required by the plot functions.
    *
    * It enables a form of syntactic sugar.
    *
    * @example Data input examples: {{{
    *
    *          Y sequence only:
    *
    *          val y = Seq(18, 24, 5, 9, 0, 1, 7)
    *
    *          in the above case, a X sequence holding the indexes of the
    *          sequence above is going to be mapped to y in order to create
    *          a sequence of coordinates:
    *
    *          Seq(1, 2, 3, 4, 5, 6, 7) -> y
    *
    *          X, Y data sequences:
    *
    *          Seq(1, 2, 3) -> Seq(7, 9, 12)
    *
    *          Sequence of tuple:
    *
    *          Seq(12, 24, 3) zip Seq(7, 9, 12)
    *
    *          Sequence to function:
    *
    *          (-math.Pi to math.Pi by 0.1) -> math.sin _
    * }}}
    */
  sealed trait Data {
    val coordinates: Coordinates2D
    private lazy val last = coordinates.last

    def sparse: Data = coordinates.foldLeft(Seq.empty[Point2D]) {
      case (data, point) =>
        if (data.isEmpty || point == last || data.last != point) data :+ point
        else data
    }
  }

  object Data {
    implicit def fromY[Y: Numeric](y: Seq[Y]): Data = new Data {
      val coordinates: Coordinates2D = (1 to y.length).map(_.toDouble) zip y.map(_.toDouble)
    }

    implicit def fromXY[X: Numeric, Y: Numeric](xy: Seq[(X, Y)]): Data = new Data {
      val coordinates: Coordinates2D = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromFunction[X: Numeric, Y: Numeric](xf: (Seq[X], X => Y)): Data = xf match {
      case (seq, f) => new Data { val coordinates: Coordinates2D = seq.map(x => x.toDouble -> f(x).toDouble) }
    }

    implicit def fromDoubleFunction[X: Numeric, Y: Numeric](xf: (Seq[X], Double => Y)): Data = xf match {
      case (seq, f) => new Data { val coordinates: Coordinates2D = seq.map(x => x.toDouble -> f(x.toDouble).toDouble) }
    }
  }
}
