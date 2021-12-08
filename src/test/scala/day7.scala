import org.junit.Test
import org.junit.Assert.*

class Day7:
  @Test def test_example(): Unit =
    val input = "16,1,2,0,4,2,7,1,2,14"
    assertEquals(
      List(16,1,2,0,4,2,7,1,2,14),
      parseCrabLine(input)
    )

  @Test def test_move_to_position(): Unit =
    val crabs = List(16,1,2,0,4,2,7,1,2,14)
    assertEquals(
      37,
      moveCrabs(crabs, 2)
    )

  @Test def test_get_cheapest_move(): Unit =
    val crabs = List(16,1,2,0,4,2,7,1,2,14)
    val (position, fuel) = findCheapestMove(crabs)
    assertEquals(
      2,
      position
    )

    assertEquals(
      37,
      fuel
    )
