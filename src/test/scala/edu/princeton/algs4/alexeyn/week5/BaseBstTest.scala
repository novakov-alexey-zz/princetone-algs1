package edu.princeton.algs4.alexeyn.week5

import java.lang.Iterable

import edu.princeton.cs.algs4.{Point2D, RectHV}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * @author Alexey Novakov
  */
abstract class BaseBstTest extends FlatSpec with Matchers {

  trait BstTree {
    def isEmpty: Boolean

    def size(): Int

    def insert(p: Point2D): Unit

    def contains(p: Point2D): Boolean

    def draw(): Unit

    def range(rect: RectHV): Iterable[Point2D]

    def nearest(p: Point2D): Point2D
  }

  def fixture = {
    new {
      val points = p(.7, .2) :: p(.5, .4) :: p(.2, .3) :: p(.4, .7) :: p(.9, .6) :: p(.1, .1) :: Nil
      val tree = implementation
      points foreach tree.insert
    }
  }

  it should "return size" in {
    //when - then
    fixture.tree.size should be(6)
    //given
    val tree = implementation
    //then
    tree.size should be(0)
    tree.isEmpty should be(true)
    //when
    tree.insert(new Point2D(.3, .2))
    tree.insert(new Point2D(.3, .3))
    //then
    tree.size should be(2)
  }

  def implementation: BstTree

  it should "return a nearest neighbor in the set to point p" in {
    //when
    val nearest = fixture.tree.nearest(new Point2D(0, 0))
    //then
    nearest should be(new Point2D(.1, .1))
  }

  it should "contain inserted points in the tree" in {
    //given
    val tree = fixture.tree
    //then
    tree.isEmpty should be(false)
    tree.size should be(6)
    fixture.points.foreach(tree.contains(_) should be(true))
    tree.contains(p(.1, .2)) should be(false)
  }

  it should "not count non-unique points" in {
    //given
    val tree = fixture.tree
    //when
    tree.insert(p(.7, .2))
    //then
    tree.size should be(6)
  }

  it should "return null range when empty tree" in {
    //given
    val tree = implementation
    //when
    val range = tree.range(new RectHV(0, 0, 1, 1)).asScala.toList
    //then
    range should be(empty)
  }

  it should "return range of the points contained in the rectangle" in {
    //given
    val tree = fixture.tree
    //when
    val range = tree.range(new RectHV(0, 0, 1, 1)).asScala.toList
    //then
    range.size should be(fixture.points.size)
    range should contain theSameElementsAs fixture.points
  }

  it should "return range of points contained in the smaller rectangle" in {
    //given
    val tree = fixture.tree
    //when
    val range = tree.range(new RectHV(0, 0, .5, .5)).asScala.toList
    //then
    range should contain theSameElementsAs p(.5, .4) :: p(.2, .3) :: p(.1, .1) :: Nil
  }

  def p(x: Double, y: Double) = new Point2D(x, y)
}
