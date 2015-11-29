package edu.princeton.algs4.alexeyn.week4

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Alexey Novakov
  */
class BoardTest extends FlatSpec with Matchers {
  behavior of "Board"

  def fixture =
    new {
      val matrix = Array(
        Array(3, 5, 7),
        Array(0, 4, 2),
        Array(1, 8, 6)
      )
      val board = new Board(matrix)

      val goalMatrix = Array(
        Array(1, 2, 3),
        Array(4, 5, 6),
        Array(7, 8, 0)
      )
    }

  it should "return dimension" in {
    fixture.board.dimension() should be(3)
  }

  it should "return Hamming count" in {
    fixture.board.hamming() should be(7)
  }

  it should "return Manhattan distance" in {
    fixture.board.manhattan() should be(2 + 1 + 4 + 1 + 2 + 2 + 1)
  }

  it should "return false when blocks on wrong places" in {
    fixture.board.isGoal should be(false)
  }

  it should "return true when blocks on their places" in {
    //when
    val board = new Board(fixture.goalMatrix)
    //then
    board.isGoal should be(true)
  }

  it should "return false when matrices do not match by blocks" in {
    val goalBoard = new Board(fixture.goalMatrix)
    fixture.board.equals(goalBoard) should be(false)
  }

  it should "return true when matrices match by blocks" in {
    fixture.board.equals(new Board(fixture.matrix)) should be(true)
  }

  it should "return non-equal board when twin generated" in {
    //when
    val twinBoard = fixture.board.twin()
    //then
    twinBoard.equals(fixture.board) should be(false)
    twinBoard.dimension() should be(fixture.board.dimension())
  }

  it should "return twin board with blank block at original place" in {
    val matrix = fixture.matrix
    val board = new Board(matrix)
    val blankPos = findBlankPos(matrix)
//
//    (1 to 10).foreach { i =>
//      val twinBlankPos = findBlankPos(board.twin.getBlocks)
//      blankPos should be(twinBlankPos)
//    }
  }

  it should "remain immutable when twin method is called" in {
    //given
    val board = fixture.board
    //when
    board.twin()
    //then
    board.equals(fixture.board) should be(true)
  }

  it should "return matrix string the following format" in {
    //given
    val expected = "3\n 3  5  7 \n 0  4  2 \n 1  8  6 \n"
    //when/then
    fixture.board.toString should be(expected)
  }

  it should "return 3 neighbors when blank is at the edge" in {
    import scala.collection.JavaConverters._
    //when
    val neighbors = fixture.board.neighbors.asScala.toSet
    //then
    val n1 = new Board(Array(
      Array(3, 5, 7),
      Array(4, 0, 2),
      Array(1, 8, 6)
    ))
    val n2 = new Board(Array(
      Array(0, 5, 7),
      Array(3, 4, 2),
      Array(1, 8, 6)
    ))
    val n3 = new Board(Array(
      Array(3, 5, 7),
      Array(1, 4, 2),
      Array(0, 8, 6)
    ))

    neighbors should have size 3
    neighbors should contain allOf(n1, n2, n3)
  }

  it should "return 4 neighbors when the blank is inside" in {
    import scala.collection.JavaConverters._
    //given
    val board = new Board(Array(
      Array(3, 1, 2),
      Array(6, 0, 8),
      Array(4, 7, 5)
    ))
    //when
    val neighbors = board.neighbors.asScala.toSet
    //then
    val n1 = new Board(Array(
      Array(3, 0, 2),
      Array(6, 1, 8),
      Array(4, 7, 5)
    ))
    val n2 = new Board(Array(
      Array(3, 1, 2),
      Array(6, 7, 8),
      Array(4, 0, 5)
    ))
    val n3 = new Board(Array(
      Array(3, 1, 2),
      Array(0, 6, 8),
      Array(4, 7, 5)
    ))
    val n4 = new Board(Array(
      Array(3, 1, 2),
      Array(6, 8, 0),
      Array(4, 7, 5)
    ))

    neighbors should have size 4
    neighbors should contain allOf(n1, n2, n3, n4)
  }

  def findBlankPos(blocks: Array[Array[Int]]): Array[Int] = {
    (for {i <- blocks.indices; j <- blocks.indices if blocks(i)(j) == 0} yield Array(i, j)).toList.head
  }
}
