package edu.princeton.algs4.alexeyn.week3

import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Alexey Novakov
 */
abstract class BaseCollinearPointsTest extends FlatSpec with Matchers {
  //line1
  val pointA = new Point(1, 1)
  val pointB = new Point(2, 1)
  val pointC = new Point(3, 1)
  val pointD = new Point(4, 1)
  val line1 = Array[Point](pointA, pointB, pointC, pointD)
  //line2
  val pointE = new Point(2, 0)
  val pointF = new Point(4, 2)
  val pointG = new Point(5, 3)
  val twoLines = Array[Point](pointE, pointF, pointG) ++ line1

  trait CollinearPointsSolution {
    def numberOfSegments: Int

    def segments: Array[LineSegment]
  }

  def implementation(points: Array[Point]): CollinearPointsSolution

  behavior of "BruteCollinearPoints"

  it should "return one line segment when points are collinear" in {
    //when
    val bruteCollinearPoints = implementation(line1)

    //then
    bruteCollinearPoints.numberOfSegments should be(1)

    //then
    val segment: LineSegment = bruteCollinearPoints.segments.apply(0)
    segment.getP should be(pointA)
    segment.getQ should be(pointD)
  }

  it should "return two line segment when two sets of points are colinear" in {
    //when
    val bruteCollinearPoints = implementation(twoLines)

    //then
    bruteCollinearPoints.numberOfSegments should be(2)

    //then
    val segment1: LineSegment = bruteCollinearPoints.segments.apply(1)
    segment1.getP should be(pointA)
    segment1.getQ should be(pointD)

    //then
    val segment2: LineSegment = bruteCollinearPoints.segments.apply(0)
    segment2.getP should be(pointE)
    segment2.getQ should be(pointG)
  }

  it should "throw a NullPointerException when the argument to the constructor is null" in {
    intercept[NullPointerException] {
      implementation(null)
    }
  }

  it should "throw a NullPointerException if any point in the array is null." in {
    intercept[NullPointerException] {
      implementation(Array(new Point(1, 1), null, new Point(2, 2)))
    }
  }

  it should "throw a IllegalArgumentException if the argument to the constructor contains a repeated point" in {
    intercept[IllegalArgumentException] {
      implementation(Array(new Point(0, 0), new Point(1, 1), new Point(1, 1)))
    }
  }
}
