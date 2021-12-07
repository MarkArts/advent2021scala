import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

type LanternFish = Int

def parseFishLine(input: String): List[LanternFish] =
  input.split(",").map(x => x.toInt).toList

def sleep(fish: List[LanternFish]): List[LanternFish] =
  fish.flatMap(f =>
    if(f-1 < 0)
      List(6, 8)
    else
      List(f-1)
  )

def sleeps(fish: List[LanternFish], days: Int): List[LanternFish] =
  var _fish = fish
  for (x <- 0 until days)
    _fish = sleep(_fish)
    println(s"day: ${x}, count: ${_fish.length}")

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


def calculateChildren(fish: LanternFish, days: Int): Int =
  Math.floor((days-fish) / 6).toInt