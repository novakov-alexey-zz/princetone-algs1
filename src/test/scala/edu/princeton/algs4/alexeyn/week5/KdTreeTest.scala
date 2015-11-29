package edu.princeton.algs4.alexeyn.week5

/**
  * @author Alexey Novakov
  */
class KdTreeTest extends BaseBstTest {
  behavior of "KdTree"

  override def implementation: BstTree = new KdTree with BstTree
}
