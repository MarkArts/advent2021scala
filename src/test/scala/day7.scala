import org.junit.Test
import org.junit.Assert.*

class Day7:
  @Test def test_input(): Unit =
    val input = "16,1,2,0,4,2,7,1,2,14"
    assertEquals(
      List(16,1,2,0,4,2,7,1,2,14),
      parseCrabLine(input)
    )

  @Test def test_get_cheapest_move(): Unit =
    val crabs = List(16,1,2,0,4,2,7,1,2,14)
    val (position, fuel) = findCheapestMove(crabs, day7Part1.moveCrabs)
    assertEquals(
      2,
      position
    )

    assertEquals(
      37,
      fuel
    )

    val (position2, fuel2) = findCheapestMove(crabs, day7Part2.moveCrabs)
    assertEquals(
      5,
      position2
    )

    assertEquals(
      168,
      fuel2
    )

class Day7Part1:
  @Test def test_move_to_position(): Unit =
    val crabs = List(16,1,2,0,4,2,7,1,2,14)
    assertEquals(
      37,
      day7Part1.moveCrabs(crabs, 2)
    )

class Day7Part2:

  @Test def test_move_to_position(): Unit =
    val crabs = List(16,1,2,0,4,2,7,1,2,14)
    assertEquals(
      206,
      day7Part2.moveCrabs(crabs, 2)
    )