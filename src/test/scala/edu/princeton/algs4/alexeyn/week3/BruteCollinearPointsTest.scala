package edu.princeton.algs4.alexeyn.week3

/**
 * @author Alexey Novakov
 */
class BruteCollinearPointsTest extends BaseCollinearPointsTest {
  override def implementation(points: Array[Point]) = {
    new BruteCollinearPoints(points) with CollinearPointsSolution
  }

}
