case class Step(left: Int, right: Int)
def createSteps(xs: List[Int]): List[Step] = xs match {
  case head :: head2 :: tail => List(Step(head, head2)) ++ createSteps(head2 :: tail)
  case _ => List[Step]()
}

def countIncreased(measurements: List[Int]): Int =
  createSteps(measurements)
    .map( x => x.left < x.right)
    .filter(x => x == true)
    .length
  
case class Triptych(first: Int, second: Int, third: Int)
def createTriptychs(xs: List[Int]): List[Triptych] = xs match {
  case first :: second :: third :: tail => List(Triptych(first, second, third)) ++ createTriptychs(second :: third :: tail)
  case _ => List[Triptych]()
}


def countSlidingIncrease(measurements: List[Int]): Int =
  val windows = createTriptychs(measurements)
    .map(t => t.first + t.second + t.third)
  
  createSteps(windows)
    .map(x => x.left < x.right)
    .filter(x => x == true)
    .length