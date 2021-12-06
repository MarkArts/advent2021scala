import org.junit.Test
import org.junit.Assert.*
import scala.compiletime.ops.boolean


def assertMapEqual[A, B](as: Map[A, B], bs: Map[A, B]): Unit =
  as.map(a =>
    bs get a._1 match {
      case Some(b) => assertEquals(a._2, b)
      case None => assert(false, s"key ${a._1} from ${as} not found in map ${bs}")
    }
  )
  bs.map(b =>
    as get b._1 match {
      case Some(a) => assertEquals(b._2, a)
      case None => assert(false, s"key ${b._1} from ${bs} not found in map ${as}")
    }
  )

class Day5:
  @Test def test_parse_lines(): Unit =
    val input = List(
      "500,982 -> 442,982",
      "989,345 -> 445,345",
      "819,279 -> 819,68",
      "81,279 -> 819,68",
    )

    assertEquals(
      List(
        (Vector2(500,982), Vector2(442,982)),
        (Vector2(989,345), Vector2(445,345)),
        (Vector2(819,279), Vector2(819,68)),
        (Vector2(81,279), Vector2(819,68)),
      ), input.map(parseLine)
    )

  @Test def test_is_diagonal(): Unit =
    assertEquals(false, isStraight((Vector2(0,1), Vector2(1,2))))
    assertEquals(true, isStraight((Vector2(0,0), Vector2(2,0))))

  @Test def test_is_straight(): Unit =
    assertEquals(false, isStraight((Vector2(1,1), Vector2(3,3))))
    assertEquals(true, isStraight((Vector2(9,7), Vector2(7,9))))

  @Test def test_draw_diagonal(): Unit =
    val lines = List[Line](
      (Vector2(1,1), Vector2(3,3)),
      (Vector2(9,7), Vector2(7,9))
    )

    assertMapEqual(Map[Vector2, Int](
      Vector2(1,1) -> 1,
      Vector2(2,2) -> 1,
      Vector2(3,3) -> 1,
      Vector2(9,7) -> 1,
      Vector2(8,8) -> 1,
      Vector2(7,9) -> 1,
    ), drawGrid(lines))

  @Test def test_draw_line(): Unit =
    val right4 = (Vector2(1,2), Vector2(5,2))
    val down2 = (Vector2(2,4), Vector2(2,6))
    val left3 = (Vector2(5,6), Vector2(2,6))
    val up5 = (Vector2(2,5), Vector2(2,0))

    assertMapEqual(Map[Vector2, Int](
      Vector2(1,2) -> 1,
      Vector2(2,2) -> 1,
      Vector2(3,2) -> 1,
      Vector2(4,2) -> 1,
      Vector2(5,2) -> 1,
    ), drawLine(right4))

    assertMapEqual(Map[Vector2, Int](
      Vector2(2,4) -> 1,
      Vector2(2,5) -> 1,
      Vector2(2,6) -> 1,
    ), drawLine(down2))

    assertMapEqual(Map[Vector2, Int](
      Vector2(5,6) -> 1,
      Vector2(4,6) -> 1,
      Vector2(3,6) -> 1,
      Vector2(2,6) -> 1,
    ), drawLine(left3))

    assertMapEqual(Map[Vector2, Int](
      Vector2(2,5) -> 1,
      Vector2(2,4) -> 1,
      Vector2(2,3) -> 1,
      Vector2(2,2) -> 1,
      Vector2(2,1) -> 1,
      Vector2(2,0) -> 1,
    ), drawLine(up5))

  @Test def test_merge(): Unit =
    val a: Grid = Map[Vector2, Int](
      Vector2(1,2) -> 1,
      Vector2(1,3) -> 1,
      Vector2(1,4) -> 1,
    )

    val b: Grid = Map[Vector2, Int](
      Vector2(2,3) -> 1,
      Vector2(1,3) -> 1,
      Vector2(0,3) -> 1,
    )

    assertMapEqual(
      Map[Vector2, Int](
        Vector2(1,2) -> 1,
        Vector2(1,3) -> 2,
        Vector2(1,4) -> 1,
        Vector2(2,3) -> 1,
        Vector2(0,3) -> 1,
      ), mergeGrid(a, b)
    )

    val emptyA: Grid = Map[Vector2, Int]()

    assertMapEqual(
      Map[Vector2, Int](
        Vector2(2,3) -> 1,
        Vector2(1,3) -> 1,
        Vector2(0,3) -> 1,
      ), mergeGrid(emptyA, b)
    )

    val emptyB: Grid = Map[Vector2, Int]()

    assertMapEqual(
      Map[Vector2, Int](
        Vector2(1,2) -> 1,
        Vector2(1,3) -> 1,
        Vector2(1,4) -> 1,
      ), mergeGrid(a, emptyB)
    )

  @Test def test_grid(): Unit =
    val lines = List[Line](
      (Vector2(0,0), Vector2(0,4)),
      (Vector2(4,0), Vector2(4,2)),
      (Vector2(5,2), Vector2(0,2))
    )

    assertMapEqual(
      Map[Vector2, Int](
        Vector2(0,0) -> 1,
        Vector2(0,1) -> 1,
        Vector2(0,2) -> 2,
        Vector2(0,3) -> 1,
        Vector2(0,4) -> 1,

        Vector2(4,0) -> 1,
        Vector2(4,1) -> 1,
        Vector2(4,2) -> 2,

        Vector2(5,2) -> 1,
        Vector2(3,2) -> 1,
        Vector2(2,2) -> 1,
        Vector2(1,2) -> 1,

      ), drawGrid(lines))

  @Test def test_example(): Unit =
    val input = List[String](
      "0,9 -> 5,9",
      "8,0 -> 0,8",
      "9,4 -> 3,4",
      "2,2 -> 2,1",
      "7,0 -> 7,4",
      "6,4 -> 2,0",
      "0,9 -> 2,9",
      "3,4 -> 1,4",
      "0,0 -> 8,8",
      "5,5 -> 8,2"
    )
    val lines = input.map(parseLine).filter(isStraight)
    val grid = drawOnlyStraight(lines)

    for (x <- 0 to 9)
      println("")
      for (y <- 0 to 9)
        print(grid.getOrElse(Vector2(y,x), "."))
    println("")

    assertEquals(5, getIntersections(drawGrid(lines), 2))