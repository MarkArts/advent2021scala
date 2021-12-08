import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable._

type Crab = Int

def parseCrabLine(input: String): List[LanternFish] =
  input.split(",").map(_.toInt).toList

def moveCrabs(crabs: List[Crab], to: Int): Int =
  crabs
    .par
    .map(_ - to)
    .map(_.abs)
    .sum

def findCheapestMove(crabs: List[Crab]): (Int, Int) =
  val upperBound = crabs.sorted.last
  val lowerBound = crabs.sorted.head

  (lowerBound to upperBound)
    .par
    .map( (i) => (i, moveCrabs(crabs, i)) )
    .toList
    .sortBy(_._2)
    .head