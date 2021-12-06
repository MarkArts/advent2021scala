import org.junit.Test
import org.junit.Assert.*

class Day3:
  @Test def test_unique(): Unit =
    val input = List(1,0,1,1,0,0,1)
    assertEquals(Map(1 -> 4, 0 -> 3), unique(input))

  @Test def test_bytesToInt(): Unit =
    assertEquals(0, bytesToInt(List(0)))
    assertEquals(1, bytesToInt(List(1)))
    assertEquals(2, bytesToInt(List(1, 0)))
    assertEquals(3, bytesToInt(List(1, 1)))
    assertEquals(4, bytesToInt(List(1, 0, 0)))

  @Test def test_most_common_bit(): Unit =
    val input = List(
      List(1,0,1,0,1),
      List(0,0,1,0,1),
      List(0,1,0,0,1),
      List(1,0,0,1,0),
      List(1,0,0,1,0),
    )
    assertEquals(Some(1), getMostCommonBit(0, input))
    assertEquals(Some(0), getMostCommonBit(1, input))
    assertEquals(Some(0), getMostCommonBit(2, input))
    assertEquals(Some(0), getMostCommonBit(3, input))
    assertEquals(Some(1), getMostCommonBit(4, input))

  @Test def test_least_common_bit(): Unit =
    val input = List(
      List(1,0,1,0,1),
      List(0,0,1,0,1),
      List(0,1,0,0,1),
      List(1,0,0,1,0),
      List(1,0,0,1,0),
    )
    assertEquals(Some(0), getLeastCommonBit(0, input))
    assertEquals(Some(1), getLeastCommonBit(1, input))
    assertEquals(Some(1), getLeastCommonBit(2, input))
    assertEquals(Some(1), getLeastCommonBit(3, input))
    assertEquals(Some(0), getLeastCommonBit(4, input))

  @Test def test_bit_tie(): Unit =
    val input = List(
      List(1,0,1,0,1),
      List(0,1,0,1,0),
    )

    for (i <- 0 until input(0).length)
      assertEquals(Some(1), getMostCommonBit(i, input))

    for (i <- 0 until input(0).length)
      assertEquals(Some(0), getLeastCommonBit(i, input))

  @Test def test_bit_out_of_range(): Unit =
    val input = List(
      List(1,0,1,0,1)
    )

    assertEquals(None, getMostCommonBit(-1, input))
    assertEquals(None, getMostCommonBit(5, input))

  @Test def test_input_to_diagnostic(): Unit =
    val input = List(
      "00100",
      "11110",
      "10110",
      "10111",
    )
    val expected = List(
      List(0,0,1,0,0),
      List(1,1,1,1,0),
      List(1,0,1,1,0),
      List(1,0,1,1,1)
    )

    assertEquals(expected, inputToDiagnostic(input))

  @Test def test_gamma_epsilon(): Unit =
    val input = List(
      "00100",
      "11110",
      "10110",
      "10111",
      "10101",
      "01111",
      "00111",
      "11100",
      "10000",
      "11001",
      "00010",
      "01010"
    )
    val diagnostic = inputToDiagnostic(input)

    assertEquals(22, getGammaRate(diagnostic))
    assertEquals(9, getEpsilonRate(diagnostic))
    assertEquals(198, getGammaRate(diagnostic) * getEpsilonRate(diagnostic))

  @Test def test_oxygen_co2(): Unit =
    val input = List(
      "00100",
      "11110",
      "10110",
      "10111",
      "10101",
      "01111",
      "00111",
      "11100",
      "10000",
      "11001",
      "00010",
      "01010"
    )
    val diagnostic = inputToDiagnostic(input)

    assertEquals(23, getOxygenRating(diagnostic))
    assertEquals(10, getCO2Rating(diagnostic))
    assertEquals(230, getOxygenRating(diagnostic) * getCO2Rating(diagnostic))