import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable._


type LanternFish = Int

def parseFishLine(input: String): List[LanternFish] =
  input.split(",").map(x => x.toInt).toList

def sleep(fish: ParSeq[LanternFish]): ParSeq[LanternFish] =
  fish.par.flatMap(f =>
    if(f-1 < 0)
      List(6, 8)
    else
      List(f-1)
  )

def sleeps(fish: List[LanternFish], days: Int): ParSeq[LanternFish] =
  var _fish = fish.par
  for (x <- 0 until days)
    _fish = sleep(_fish)
    // println(s"1: day: ${x}, count: ${_fish.length}")

  _fish

implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
def sleep2(fish: LanternFish, days: Int, fishes: Int = 1): Future[Int] =
    if (days <= 0)
      Future { fishes }
    else
      val nextDay = days - 1
      if (fish == 0)
        for {
            a <- sleep2(8, nextDay, fishes)
            b <- sleep2(6, nextDay, 1)
        } yield (a + b)
      else
        sleep2(fish -1, nextDay, fishes)

def sleeps2(fish: List[LanternFish], days: Int): Future[Int] =
  Future.sequence(
    fish.map( f => sleep2(f, days))
  ).map( (a) => a.fold(0)( (acc,a) => acc+a))


// School is state->amount of fish in that state
type Schools = Array[Long]
def putInSchool(fish: List[LanternFish]): Schools =
  var school:Schools = new Array(9)
  for (x <- 0 to 8)
    school(x) = fish.filter((f) => f == x).length
  school

def sleeps3(fish: List[LanternFish], days: Int): Schools =
  var oldSchools = putInSchool(fish)
  for (d <- 0 until days)
    val births = oldSchools(0)
    val newSchool:Schools = new Array(9)
    for (i <- 0 to 8)
      if(i != 0)
        newSchool(i-1) = oldSchools(i)

    newSchool(8) += births // newborns
    newSchool(6) += births // oldborns

    // println(s"2: day: ${d}, count: ${oldSchools.sum}")
    oldSchools = newSchool.clone

  oldSchools