import org.junit.Test
import org.junit.Assert.*
import scala.util.Success
import scala.util.Failure
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable._

class Day6:
  @Test def test_parse_inpit(): Unit =
    val input = "3,4,3,1,2"
    assertEquals(
      List(3,4,3,1,2),
      parseFishLine(input)
    )

  @Test def test_sleep: Unit =
    val day1 = List(3,4,3,1,2)
    val day2 = sleep(day1.par)
    assertEquals(
      List(2,3,2,0,1),
      day2.toList
    )

    val day3 = sleep(day2)
    assertEquals(
      List(1,2,1,6,8,0),
      day3.toList
    )

    val day4 = sleep(day3)
    assertEquals(
      List(0,1,0,5,7,6,8),
      day4.toList
    )

  @Test def test_sleep2: Unit =
    val fish = 1

    sleep2(fish, 1).onComplete({
      case Success(f) => assertEquals(1, f)
      case Failure(e) => throw e
    })

    sleep2(fish, 2).onComplete({
      case Success(f) => assertEquals(2, f)
      case Failure(e) => throw e
    })

  @Test def test_put_in_school: Unit =
    val input = List(3,4,3,1,2)
    assertEquals(
      List(0, 1, 1, 2, 1, 0, 0, 0, 0),
      putInSchool(input).toList
    )

    val input2 = List(0,0,5,6,8)
    assertEquals(
      List(2, 0, 0, 0, 0, 1, 1, 0, 1),
      putInSchool(input2).toList
    )

  @Test def test_sleep3: Unit =
    val day1 = List(1)
    assertEquals(
      1,
      sleeps3(day1, 1).sum
    )

    assertEquals(
      2,
      sleeps3(day1, 2).sum
    )

    assertEquals(
      3,
      sleeps3(day1, 9).sum
    )

  @Test def test_example: Unit =
    val day1 = List(3,4,3,1,2)
    assertEquals(
      List(0,1,0,5,7,6,8),
      sleeps(day1, 3).toList
    )

    assertEquals(
      26,
      sleeps(day1, 18).length
    )

    sleeps2(day1, 18).onComplete({
      case Success(f) => assertEquals(26, f)
      case Failure(e) => throw e
    })

    assertEquals(
      26,
      sleeps3(day1, 18).sum
    )

    assertEquals(
      5934,
      sleeps(day1, 80).length
    )

    sleeps2(day1, 80).onComplete({
      case Success(f) => assertEquals(5934, f)
      case Failure(e) => throw e
    })

    assertEquals(
      5934,
      sleeps3(day1, 80).sum
    )