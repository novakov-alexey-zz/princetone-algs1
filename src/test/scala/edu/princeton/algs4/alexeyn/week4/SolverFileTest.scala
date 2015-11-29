package edu.princeton.algs4.alexeyn.week4

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * @author Alexey Novakov
  */
class SolverFileTest extends FlatSpec with Matchers {
  behavior of "Solver based on the input file"

  it should "solve next file input boards" in {
    //given
    val board = readBoardFrom("puzzle01.txt")
    //when
    val solver = new Solver(board)
    //then
    solver.isSolvable should be(true)
  }

  it should "solve different size boards" in {
    //given
    val board = readBoardFrom("puzzle22.txt")
    //when
    val solver = new Solver(board)
    //then
    solver.isSolvable should be(true)
  }

  it should "test all input files based on file name to assert whether board is solvable " in {
    //given
    val files = getListOfFiles(new File("8puzzle"))

    files.foreach {
      case f if f.getName.contains(".txt") =>
        print(s"file $f ")
        //when
        val solver = new Solver(readBoardFrom(f.getName))
        //then
        solver.isSolvable should be (!f.getName.contains("unsolv"))
      case _ =>
    }
  }

  def readBoardFrom(file: String) = {
    val lines = Source.fromFile(s"8puzzle/$file").getLines.toList

    val n = lines.head.toInt
    val matrix = Array.ofDim[Int](n, n)

    for ((line, index) <- lines.tail.zipWithIndex if line.trim.nonEmpty) {
      matrix(index) = line.trim.replace("  ", " ").split(" ").map(_.toInt)
    }
    //printMatrix(matrix)
    new Board(matrix)
  }


  def printMatrix(matrix: Array[Array[Int]]) = {
    matrix foreach { a => a foreach { b => print(b.toString + " ") }; print('\n') }
  }

  def getListOfFiles(dir: File): List[File] = dir.listFiles.filter(_.isFile).toList
}
