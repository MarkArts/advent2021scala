import scala.io.Source
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await

val day1File = "./inputs/day1.txt"
val day2File = "./inputs/day2.txt"
val day3File = "./inputs/day3.txt"
val day4File = "./inputs/day4.txt"
val day5File = "./inputs/day5.txt"
val day6File = "./inputs/day6.txt"

@main def day1: Unit =
  var inputs = Source.fromFile(day1File).getLines.map(l => l.toInt).toList

  println(countIncreased(inputs))
  println(countSlidingIncrease(inputs))

@main def day2: Unit =
  var inputs = Source.fromFile(day2File).getLines.toList

  val sub = Submarine()
  for (i <- inputs.map(stringToVector))
    sub.move(i)

  println(List(sub.position.x, sub.position.y, sub.position.x * sub.position.y))

  val sub2 = Submarine2()
  for (i <- inputs.map(stringToInputs))
    sub2.input(i)

  println(
    List(sub2.position.x, sub2.position.y, sub2.position.x * sub2.position.y)
  )

@main def day3: Unit =
  val inputs = Source.fromFile(day3File).getLines.toList

  val diagnostic = inputToDiagnostic(inputs)

  val epsilonRate = getEpsilonRate(diagnostic)
  val gammaRate = getGammaRate(diagnostic)
  println(
    List(
      epsilonRate,
      gammaRate,
      epsilonRate * gammaRate
    )
  )

  val oxygenRating = getOxygenRating(diagnostic)
  val co2Rating = getCO2Rating(diagnostic)
  println(
    List(
      oxygenRating,
      co2Rating,
      oxygenRating * co2Rating
    )
  )

@main def day4: Unit =
  val input = Source.fromFile(day4File).getLines.toList
  val (numbers, boards) = parseInput(input, 5)

  println(getWinningScore(boards, numbers))
  println(getLosingScore(boards, numbers))

@main def day5: Unit =
  val input = Source.fromFile(day5File).getLines
  val lines = input.map(parseLine).toList
  val straight = lines.filter(isStraight)

  println(getIntersections(drawGrid(straight), 2))

  val diag = lines.filter(x => isStraight(x) || isDiagonal(x))
  println(getIntersections(drawGrid(diag), 2))

@main def day5Picture: Unit =
  val input = Source.fromFile(day5File).getLines
  val lines = input.map(parseLine).toList

  var grid = drawGrid(lines.filter(x => isStraight(x) || isDiagonal(x)))
  var out = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
  for (x <- grid) {
    out.setRGB(x._1.x.toInt, x._1.y.toInt, (x._2 * 60 * 65536) + (x._2 * 40 * 256) + x._2 * 12)
  }
  ImageIO.write(out, "jpg", new File("images/diagonal.jpg"))


  grid = drawGrid(lines.filter(isStraight))
  out = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
  for (x <- grid) {
    out.setRGB(x._1.x.toInt, x._1.y.toInt, (x._2 * 21 * 65536 * 2) + (x._2 * 4 * 256 * 2) + x._2 * 50 * 2)
  }
  ImageIO.write(out, "jpg", new File("images/straight.jpg"))


  var gridC = drawColorGrid(lines)
  out = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
  for (x <- gridC) {
    out.setRGB(x._1.x.toInt, x._1.y.toInt, x._2.getRGB)
  }
  ImageIO.write(out, "jpg", new File("images/drawColor.jpg"))

@main def day6: Unit =
  val input = Source.fromFile(day6File).getLines
  val fish = input.map(parseFishLine).toList(0)


  time {
    println(sleeps(fish, 80).length)
  }

  time {
    val part1 = sleeps2(fish, 80) andThen {
      case Success(f) => println(f)
      case Failure(e) => throw e
    }

    Await.ready(part1, scala.concurrent.duration.Duration.Inf)
  }

  time {
    println(sleeps3(fish, 80).sum)
  }

  time {
    println(sleeps3(fish, 256).sum)
  }

def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
}
