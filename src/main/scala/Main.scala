import scala.io.Source

@main def day1: Unit = 
  val filename = "day1.txt"
  var inputs = Source.fromFile(filename).getLines.map(l => l.toInt).toList
  
  println(countIncreased(inputs))