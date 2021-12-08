import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable._

type Crab = Int

def parseCrabLine(input: String): List[LanternFish] =
  input.split(",").map(_.toInt).toList

def findCheapestMove(crabs: List[Crab], move: (List[Crab], Int) => Int): (Int, Int) =
  val upperBound = crabs.sorted.last
  val lowerBound = crabs.sorted.head

  (lowerBound to upperBound)
    .par
    .map( (i) => (i, move(crabs, i)) )
    .toList
    .sortBy(_._2)
    .head

package day7Part1:
  def moveCrabs(crabs: List[Crab], to: Int): Int =
    crabs
      .par
      .map(_ - to)
      .map(_.abs)
      .sum

package day7Part2:
  def moveCrabs(crabs: List[Crab], to: Int): Int =
    crabs
      .par
      .map(_ - to)
      .map(_.abs)
      .map( (x) => (0 to x).sum )
      .sum