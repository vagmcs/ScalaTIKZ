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

  sealed trait Data[T] {

    val coordinates: Seq[T]

    lazy val head: T = coordinates.head
    lazy val last: T = coordinates.last
    lazy val tail: Seq[T] = coordinates.tail

    /**
      * Removes all learning zeros.
      *
      * @return the data having leading zeros removed
      */
    def pruneLeadingZeros: Data[T] = new Data[T] {
      val coordinates: Seq[T] = this.coordinates.dropWhile(_ == 0)
    }

    /**
      * Removes all trailing zeros.
      *
      * @return the data having trailing zeros removed
      */
    def pruneTrailingZeros: Data[T] = new Data[T] {
      val coordinates: Seq[T] = this.coordinates.reverse.dropWhile(_ == 0).reverse
    }

    /**
      * Compresses the data coordinates in order to be represented by
      * less data points but yielding the plot result.
      *
      * @example {{{
      *   The sequence 1 1 1 2 2 5 5 5 should be compressed as 1 1 2 2 5 5
      * }}}
      *
      * @return the data having compressed coordinates
      */
    def compress: Data[T] = this

    override def toString: String = coordinates.toString
  }

  /**
    * Data2D is a construct that automatically converts various inputs into the
    * underlying data sequences required by the plot functions for 2D space.
    *
    * It enables a form of syntactic sugar.
    *
    * @example Data2D input examples: {{{
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
  trait Data2D extends Data[Point2D] {

    val coordinates: Coordinates2D

    /**
      * Compresses the data coordinates in order to be represented by
      * less data points but yielding the plot result.
      *
      * @example {{{
      *   The sequence 1 1 1 2 2 5 5 5 should be compressed as 1 1 2 2 5 5
      * }}}
      *
      * @return the data having compressed coordinates
      */
    override def compress: Data2D = tail.sliding(2).foldLeft(Seq(head)) {
      case (data, seq) =>
        val (pointA @ (_, yA), pointB @ (_, yB)) = (seq.head, seq.last)
        if (pointB == last) data :+ pointB
        else if (yA != yB) data :+ pointA :+ pointB
        else data
    }
  }

  object Data2D {
    implicit def fromY[Y: Numeric](y: Seq[Y]): Data2D = new Data2D {
      val coordinates: Coordinates2D = (1 to y.length).map(_.toDouble) zip y.map(_.toDouble)
    }

    implicit def fromXY[X: Numeric, Y: Numeric](xy: Seq[(X, Y)]): Data2D = new Data2D {
      val coordinates: Coordinates2D = xy.map { case (x, y) => (x.toDouble, y.toDouble) }
    }

    implicit def fromXSeqYSeq[X: Numeric, Y: Numeric](xy: (Seq[X], Seq[Y])): Data2D = new Data2D {
      val coordinates: Coordinates2D = xy._1.map(_.toDouble) zip xy._2.map(_.toDouble)
    }

    implicit def fromFunction[X: Numeric, Y: Numeric](xf: (Seq[X], X => Y)): Data2D = xf match {
      case (seq, f) => new Data2D { val coordinates: Coordinates2D = seq.map(x => x.toDouble -> f(x).toDouble) }
    }

    implicit def fromDoubleFunction[X: Numeric, Y: Numeric](xf: (Seq[X], Double => Y)): Data2D = xf match {
      case (seq, f) => new Data2D { val coordinates: Coordinates2D = seq.map(x => x.toDouble -> f(x.toDouble).toDouble) }
    }
  }

  /**
    * Data3D is a construct that automatically converts various inputs into the
    * underlying data sequences required by the plot functions for 3D space.
    *
    * It enables a form of syntactic sugar.
    *
    * @example Data3D input examples: {{{
    *
    *          Z sequence only:
    *
    *          val z = Seq(18, 24, 5, 9, 0, 1, 7)
    *
    *          in the above case, the X, Y sequences holding the indexes of the
    *          sequence above is going to be mapped to z in order to create
    *          a sequence of coordinates:
    *
    *          Seq((1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7)) -> z
    *
    *          X, Y, Z data sequences:
    *
    *          Seq(1, 2, 3) -> Seq(7, 9, 12)
    *
    *          Sequence of tuple:
    *
    *          Seq(12, 24, 3) zip Seq(7, 9, 12) zip Seq(5, 0, 28)
    *
    *          Sequence to function:
    *
    *          (-math.Pi to math.Pi by 0.1) zip (-math.Pi to math.Pi by 0.1) -> math.sin _
    * }}}
    */
  trait Data3D extends Data[Point3D] {

    val coordinates: Coordinates3D

    /**
      * Compresses the data coordinates in order to be represented by
      * less data points but yielding the plot result.
      *
      * @example {{{
      *   The sequence 1 1 1 2 2 5 5 5 should be compressed as 1 1 2 2 5 5
      * }}}
      *
      * @return the data having compressed coordinates
      */
    override def compress: Data3D = tail.sliding(2).foldLeft(Seq(head)) {
      case (data, seq) =>
        val (pointA @ (_, _, zA), pointB @ (_, _, zB)) = (seq.head, seq.last)
        if (pointB == last) data :+ pointB
        else if (zA != zB) data :+ pointA :+ pointB
        else data
    }
  }

  object Data3D {

    implicit def fromZ[Z: Numeric](z: Seq[Z]): Data3D = new Data3D {
      val coordinates: Coordinates3D = ((1 to z.length) zip z.map(_.toDouble)).map {
        case (idx, z) => (idx.toDouble, idx.toDouble, z)
      }
    }

    implicit def fromXYZ[X: Numeric, Y: Numeric, Z: Numeric](xyz: Seq[(X, Y, Z)]): Data3D = new Data3D {
      val coordinates: Coordinates3D = xyz.map { case (x, y, z) => (x.toDouble, y.toDouble, z.toDouble) }
    }

    implicit def fromXYtoZ[X: Numeric, Y: Numeric, Z: Numeric](xyz: Seq[((X, Y), Z)]): Data3D = new Data3D {
      val coordinates: Coordinates3D = xyz.map { case ((x, y), z) => (x.toDouble, y.toDouble, z.toDouble) }
    }

    implicit def fromFunction[X: Numeric, Y: Numeric, Z: Numeric](xyf: (Seq[(X, Y)], (X, Y) => Z)): Data3D = xyf match {
      case (seq, f) => new Data3D {
        val coordinates: Coordinates3D = seq.map { case (x, y) => (x.toDouble, y.toDouble, f(x, y).toDouble) }
      }
    }

    implicit def fromDoubleFunction[X: Numeric, Y: Numeric, Z: Numeric](xyf: (Seq[(X, Y)], (Double, Double) => Z)): Data3D =
      xyf match {
        case (seq, f) => new Data3D {
          val coordinates: Coordinates3D = seq.map { case (x, y) => (x.toDouble, y.toDouble, f(x.toDouble, y.toDouble).toDouble) }
        }
      }
  }
}
