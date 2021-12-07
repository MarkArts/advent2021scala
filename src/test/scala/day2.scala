import org.junit.Test
import org.junit.Assert.*

class Day2:
  @Test def test_submarine_1(): Unit =
    var input = List(
      "forward 5",
      "down 5",
      "forward 8",
      "up 3",
      "down 8",
      "forward 2"
    )

    var vectors = input.map(stringToVector)
    assertEquals(List(
        Vector2(5, 0),
        Vector2(0, 5),
        Vector2(8, 0),
        Vector2(0, -3),
        Vector2(0, 8),
        Vector2(2, 0),
    ), vectors)

    val sub = Submarine()
    for (v <- vectors)
        sub.move(v)

    assertEquals(15, sub.position.x, 0.1)
    assertEquals(10, sub.position.y, 0.1)
    assertEquals(150, sub.position.x * sub.position.y, 0.1)

  @Test def test_submarine_2(): Unit =
    var input = List(
      "forward 5",
      "down 5",
      "forward 8",
      "up 3",
      "down 8",
      "forward 2"
    )

    var inputs = input.map(stringToInputs)
    assertEquals(List(
        Input(Commands.Move, 5),
        Input(Commands.Aim, 5),
        Input(Commands.Move, 8),
        Input(Commands.Aim, -3),
        Input(Commands.Aim, 8),
        Input(Commands.Move, 2),
    ), inputs)

    val sub = Submarine2()
    for (i <- inputs)
        sub.input(i)

    assertEquals(15, sub.position.x, 0.1)
    assertEquals(60, sub.position.y, 0.1)
    assertEquals(900, sub.position.x * sub.position.y, 0.1)