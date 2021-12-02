import scala.io.Source

val filename = "day1.txt"

@main def day1: Unit = 
  var inputs = Source.fromFile(filename).getLines.map(l => l.toInt).toList
  
  println(countIncreased(inputs))
  println(countSlidingIncrease(inputs))