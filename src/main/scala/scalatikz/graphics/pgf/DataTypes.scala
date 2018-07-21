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

object DataTypes {

  // Point types in the Euclidean space
  type Point = Double
  type Point2D = (Point, Point)
  type Point3D = (Point, Point)

  // Point sequence types
  type DataSeq = Seq[Point]
  type Coordinates = Seq[Point2D]

  /**
    * Data is a construct that automatically converts various inputs into the underlying data
    * sequences required by the plot functions in order to enable a form of syntactic sugar.
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
    *          Seq(1,2,3) -> Seq(7,9,12)
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
    val coordinates: Coordinates
  }

  object Data {

    implicit def fromIntY(y: Seq[Int]): Data = new Data {
      val coordinates: Coordinates = (1 to y.length).map(_.toDouble) zip y.map(_.toDouble)
    }

    implicit def fromLongY(y: Seq[Long]): Data = new Data {
      val coordinates: Coordinates = (1 to y.length).map(_.toDouble) zip y.map(_.toDouble)
    }

    implicit def fromY(y: DataSeq): Data = new Data {
      val coordinates: Coordinates = (1 to y.length).map(_.toDouble) zip y
    }

    implicit def fromIntXIntY(xy: Seq[(Int, Int)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromLongXLongY(xy: Seq[(Long, Long)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromIntXLongY(xy: Seq[(Int, Long)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromLongXIntY(xy: Seq[(Long, Int)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromIntXDoubleY(xy: Seq[(Int, Double)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y) }
    }

    implicit def fromDoubleXIntY(xy: Seq[(Double, Int)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x, y.toDouble) }
    }

    implicit def fromLongXDoubleY(xy: Seq[(Long, Double)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x.toDouble, y) }
    }

    implicit def fromDoubleXLongY(xy: Seq[(Double, Long)]): Data = new Data {
      val coordinates: Coordinates = xy.map { case (x, y) => (x, y.toDouble) }
    }

    implicit def fromXY(xy: (DataSeq, DataSeq)): Data = xy match {
      case (x, y) => new Data { val coordinates: Coordinates = x zip y }
    }

    implicit def fromCoordinates(c: Coordinates): Data = new Data {
      val coordinates: Coordinates = c
    }

    implicit def fromFunction(xf: (DataSeq, Double => Double)): Data = xf match {
      case (x, f) => new Data { val coordinates: Coordinates = x zip x.map(f) }
    }
  }
}
