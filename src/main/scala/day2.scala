import scala.languageFeature.postfixOps


case class Vector2(x:Int, y:Int) {

  def +(vec: Vector2): Vector2 =
    return Vector2(
      x+vec.x,
      y+vec.y
    )
}


class Submarine() {
  var position = Vector2(0,0)

  def move(vec: Vector2): Unit =
    position += vec
}

def stringToVector(command: String): Vector2 =
  command.split(" ") match {
    case Array("forward", s) => Vector2(s.toInt, 0)
    case Array("down", s) => Vector2(0, s.toInt)
    case Array("up", s) => Vector2(0, -s.toInt)
    case Array("back", s) => Vector2(-s.toInt, 0)
  }


object Commands extends Enumeration {
  type Command = Value

  val Aim, Move = Value
}


case class Input(command: Commands.Command, value: Int) 

class Submarine2() {
  var position = Vector2(0,0)
  var aim = 0

  def aim(value: Int): Unit =
    aim += value

  def move(value: Int): Unit =
    position = Vector2(
      position.x + value,
      position.y + aim * value
    )

  def input(c: Input): Unit = c.command match {
    case Commands.Aim => aim(c.value)
    case Commands.Move => move(c.value)
  }
}

def stringToInputs(s: String): Input =
  s.split(" ") match {
    case Array("forward", s) => Input(Commands.Move, s.toInt)
    case Array("down", s) => Input(Commands.Aim, s.toInt)
    case Array("up", s) => Input(Commands.Aim, -s.toInt)
    case Array("back", s) => Input(Commands.Aim, -s.toInt)
  }