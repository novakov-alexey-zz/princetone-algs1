package edu.princeton.algs4.alexeyn.week4

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

/**
  * @author Alexey Novakov
  */
class SolverTest extends FlatSpec with Matchers {

  behavior of "Solver"

  it should "return solution list" in {
    //given
    val board = new Board(Array(
      Array(0, 1, 3),
      Array(4, 2, 5),
      Array(7, 8, 6)
    ))
    //when
    val solver = new Solver(board)
    //then
    solver.isSolvable should be(true)
    solver.solution.asScala.toList should not be empty
    solver.moves should be > 1
  }

  it should "return 0 moves when board is goal" in {
    //given
    val board = new Board(Array(
      Array(1, 2, 3),
      Array(4, 5, 6),
      Array(7, 8, 0)
    ))
    //when
    val solver = new Solver(board)
    //then
    solver.isSolvable should be(true)
    solver.moves should be(0)
  }

  it should "throw exception when board is null" in {
    intercept[NullPointerException] {
      new Solver(null)
    }
  }

  it should "return unsolveable for next board" in {
    //given
    val unsolveableBoard = new Board(Array(
      Array(1, 2, 3),
      Array(4, 5, 6),
      Array(8, 7, 0)
    ))
    //when
    val solver = new Solver(unsolveableBoard)
    //then
    solver.isSolvable should be(false)
  }
}
