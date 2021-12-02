@main def hello: Unit = 
  println("Hello world!")
  println(msg)

@main def bye: Unit = 
  println("bye world!")
  println(msg)


def msg = "I was compiled by Scala 3. :)"
