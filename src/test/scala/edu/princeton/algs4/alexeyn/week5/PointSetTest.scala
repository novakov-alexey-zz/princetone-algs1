package edu.princeton.algs4.alexeyn.week5

/**
  * @author Alexey Novakov
  */
class PointSetTest extends BaseBstTest {
  behavior of "PointSET"

  override def implementation: BstTree = new PointSET with BstTree
}
