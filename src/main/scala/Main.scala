import scala.io.Source

val day1File = "./inputs/day1.txt"
val day2File = "./inputs/day2.txt"
val day3File = "./inputs/day3.txt"
val day4File = "./inputs/day4.txt"
val day5File = "./inputs/day5.txt"

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
  val lines = input.map(parseLine).filter(isStraight).toList

  println(getIntersections(drawGrid(lines), 2))