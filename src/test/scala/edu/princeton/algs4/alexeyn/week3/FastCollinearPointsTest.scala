package edu.princeton.algs4.alexeyn.week3

/**
 * @author Alexey Novakov
 */
class FastCollinearPointsTest extends BaseCollinearPointsTest {
  override def implementation(points: Array[Point]) = {
    new FastCollinearPoints(points) with CollinearPointsSolution
  }

  it should "return segment for 5 collinear points properly" in {
    //given
    val pointH = new Point(5, 1)
    val line1 = Array[Point](pointA, pointB, pointC, pointD, pointH)

    //when
    val fastCollinearPoints = implementation(line1)

    //then
    fastCollinearPoints.numberOfSegments should be(1)

    //then
    val segment: LineSegment = fastCollinearPoints.segments.apply(0)
    segment.getP should be(pointA)
    segment.getQ should be(pointH)
  }
}
