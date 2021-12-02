import scala.io.Source

val day1File = "./inputs/day1.txt"
val day2File = "./inputs/day2.txt"

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
  
  println(List(sub2.position.x, sub2.position.y, sub2.position.x * sub2.position.y))