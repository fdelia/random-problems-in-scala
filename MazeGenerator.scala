/**
  * Created by fabiodelia on 17/04/16.
  */

import scala.util.Random
import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer

class MazeGenerator {
  private val (wallTop, wallRight, wallBottom, wallLeft) = (1, 2, 4, 8)
  private var height: Int = 0
  private var wallsNodes: Array[Array[Int]] = Array()
  private var visitedNodes: Array[Array[Boolean]] = Array()


  //def DFS(wallsNodes: Array[Array[Int]], visitedNodes: Array[Array[Boolean]], currentNode: (Int, Int)): (Array[Array[Int]], Array[Array[Boolean]]) ={
  def DFS(currentNode: (Int, Int)): Unit = {
    val (currentX, currentY) = currentNode
    var nodeStack: Stack[List[Int]] = new Stack()

    // get all unvisited neighbors
    var unvisNeigh: ListBuffer[List[Int]] = ListBuffer()
    if (currentY > 0 && !visitedNodes(currentX)(currentY - 1)) unvisNeigh += List(currentX, currentY - 1)
    if (currentY < height - 1 && !visitedNodes(currentX)(currentY + 1)) unvisNeigh += List(currentX, currentY + 1)

    if (currentX > 0 && !visitedNodes(currentX - 1)(currentY)) unvisNeigh += List(currentX - 1, currentY)
    if (currentX < height - 1 && !visitedNodes(currentX + 1)(currentY)) unvisNeigh += List(currentX + 1, currentY)

    // randomize the neighbors
    unvisNeigh = Random.shuffle(unvisNeigh)

    // add them to stack
    unvisNeigh.foreach(
      f => {
        nodeStack.push(f)
        // set visited
        //        visitedNodes(f(0))(f(1)) = true
      })

    while (!nodeStack.isEmpty) {
      val temp = nodeStack.pop()
      val (newNodeX, newNodeY) = (temp(0), temp(1)) // bad way to do that

      if (!visitedNodes(newNodeX)(newNodeY)) {
        // set visited
        visitedNodes(newNodeX)(newNodeY) = true

        // move there
        val newWallsNodes = changeWalls(wallsNodes, currentNode, (newNodeX, newNodeY))
        DFS((newNodeX, newNodeY))
      }
    }

    //return (wallsNodes, visitedNodes)
  }

  def removeWall(node: (Int, Int), wall: Int): Unit = {
    //    val wallName = wall match {
    //      case 1 => "top"
    //      case 2 => "right"
    //      case 4 => "bottom"
    //      case 8 => "left"
    //    }
    //    println("  remove wall " + wallName + " on " + node.toString())

    wallsNodes(node._1)(node._2) -= wall
  }

  def changeWalls(wallsNodes: Array[Array[Int]], currentNode: (Int, Int), newNode: (Int, Int)): Array[Array[Int]] = {
    val diffX = newNode._1 - currentNode._1
    val diffY = newNode._2 - currentNode._2

//    println("move: " + currentNode.toString() + " -> " + newNode.toString())

    if (diffX == -1) {
      // move left, so remove left wall of currentNode and right wall of newNode
      removeWall(currentNode, wallLeft)
      removeWall(newNode, wallRight)
    }
    if (diffX == 1) {
      // move right, etc.
      removeWall(currentNode, wallRight)
      removeWall(newNode, wallLeft)
    }
    if (diffY == -1) {
      // move up
      removeWall(currentNode, wallTop)
      removeWall(newNode, wallBottom)
    }
    if (diffY == 1) {
      // move down
      removeWall(currentNode, wallBottom)
      removeWall(newNode, wallTop)
    }

    return wallsNodes
  }

  def drawMaze(): Unit = {
    for (y <- 0 to height - 1) {
      for (x <- 0 to height - 1) {
        val node = wallsNodes(x)(y)
        if (node % (wallTop*2) == 1){
          if (y == 0) print(9608.toChar.toString + 9608.toChar.toString + 9608.toChar.toString)
          else print(9608.toChar.toString + " " + 9608.toChar.toString)
        }
        else {
          if (y == 0) print(9608.toChar.toString + "  ")
          else print("   ")
        }
      }
      print(9608.toChar.toString + "\n")

      for (x <- 0 to height - 1) {
        val node = wallsNodes(x)(y)
        if (node - wallLeft >= 0) print(9608.toChar)
        else print(" ")
        print("  ")
      }

      print(9608.toChar.toString + "\n")


      //      println(wallsNodes(x))
    }

    for (x <- 0 to height - 1) {
      print(9608.toChar.toString + 9608.toChar.toString + 9608.toChar.toString)
    }
    print("\n")
  }

  def printMazeNumbers(): Unit = {
    for (y <- 0 to height - 1) {
      for (x <- 0 to height - 1) {
        var node = wallsNodes(x)(y)
        print (node + " ")
      }
      print("\n")
    }
  }

  def generate(height: Int): Unit = {
    /*
    attention: x=0, y=0 is top left, not bottom left

    state of a cell:
    top wall has: 1 bit
    right wall has: 2 bits
    bottom wall has: 4 bits
    left wall has: 8 bits
    e.g. 11 = 1 + 2 + 8, all walls except the bottom wall
    15 as end value is only theoretical (where all 4 walls are set)
    same with 0 (no walls)
     */

    this.height = height

    //    wallsNodes = Array.ofDim[Int](height,height)
    wallsNodes = Array.fill[Array[Int]](height)(Array.fill(height)(15)) // start with all 4 walls

    //    visitedNodes = Array.ofDim[Boolean](height,height)
    visitedNodes = Array.fill[Array[Boolean]](height)(Array.fill(height)(false))

    println("height: " + height)

    // random starting cell
    var (currentX, currentY): (Int, Int) = (Random.nextInt(height), Random.nextInt(height))
    visitedNodes(currentX)(currentY) = true

    println("starting node: " + currentX + " / " + currentY)

    //    (wallsNodes, visitedNodes) = DFS(wallsNodes, visitedNodes, (currentX, currentY))
    DFS((currentX, currentY))
    print("\n\n")
//    drawMaze()
    printMazeNumbers()

  }
}
