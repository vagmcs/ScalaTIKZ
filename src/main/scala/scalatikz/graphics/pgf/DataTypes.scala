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

object DataTypes {

  // Point types in the Euclidean space
  type Point = Double
  type Point2D = (Point, Point)
  type Point3D = (Point, Point)

  // Point sequence types
  type DataSeq = Seq[Point]
  type Coordinates = Seq[Point2D]

  implicit def intSeq2PointSeq(seq: Seq[Int]): DataSeq = seq.map(_.toDouble)

  implicit def longSeq2PointSeq(seq: Seq[Long]): DataSeq = seq.map(_.toDouble)

  implicit def intDoubleSeq2Coordinates(seq: Seq[(Int, Double)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y) }

  implicit def doubleIntSeq2Coordinates(seq: Seq[(Double, Int)]): Coordinates =
    seq.map { case (x, y) => (x, y.toDouble) }

  implicit def intIntSeq2Coordinates(seq: Seq[(Int, Int)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y.toDouble) }

  implicit def longDoubleSeq2Coordinates(seq: Seq[(Long, Double)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y) }

  implicit def doubleLongSeq2Coordinates(seq: Seq[(Double, Long)]): Coordinates =
    seq.map { case (x, y) => (x, y.toDouble) }

  implicit def longLongSeq2Coordinates(seq: Seq[(Long, Long)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y.toDouble) }

  implicit def intLongSeq2Coordinates(seq: Seq[(Int, Long)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y.toDouble) }

  implicit def longIntSeq2Coordinates(seq: Seq[(Long, Int)]): Coordinates =
    seq.map { case (x, y) => (x.toDouble, y.toDouble) }

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

    implicit def fromY(y: DataSeq): Data = new Data {
      val coordinates: Coordinates = (1 to y.length) zip y
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
