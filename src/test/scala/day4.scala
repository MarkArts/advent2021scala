import org.junit.Test
import org.junit.Assert.*
import junit.framework.Assert

def assertBoardEqual[A](a: Array[Array[A]], b: Array[Array[A]]) =
  assertEquals(
    a.map(x => x.toSeq).toSeq,
    b.map(x => x.toSeq).toSeq,
  )

class Day4:
  @Test def test_parse_input(): Unit =
    val input = List(
      "1,2,3,4,5",
      "",
      "1  2 3",
      "1 2 3",
      "1 2 3",
      "",
      "4 5 6",
      "4 5 6",
      "4  5 6",
      "",
      "7 8  9",
      "7 8 9",
      "7 8 9"
    )

    var (numbers, boards) = parseInput(input, 3)

    assertEquals(List(1,2,3,4,5), numbers)
    assertBoardEqual(
      Array(
        Array(1,2,3),
        Array(1,2,3),
        Array(1,2,3),
      ), boards(0))
    assertBoardEqual(
      Array(
        Array(4,5,6),
        Array(4,5,6),
        Array(4,5,6),
      ), boards(1))
    assertBoardEqual(
      Array(
        Array(7,8,9),
        Array(7,8,9),
        Array(7,8,9),
      ), boards(2))

  @Test def test_rows(): Unit =
    val board = Array(
      Array(1,2,3),
      Array(4,5,6),
      Array(7,8,9),
    )
    assertBoardEqual(
      Array(
        Array(1,2,3),
        Array(4,5,6),
        Array(7,8,9),

        Array(1,4,7),
        Array(2,5,8),
        Array(3,6,9),
      ),
      getRows(board)
    )

  @Test def test_board_state(): Unit =
    val board = Array(
      Array(22, 13, 17, 11, 0),
      Array(8, 2, 23, 4, 24),
      Array(21, 9, 14, 16, 7),
      Array(6, 10, 3, 18, 5),
      Array(1, 12, 20, 15, 19)
    )
    val numbers = List(22, 2, 23, 16, 19, 1)

    var state = calculateBoardState(board, numbers)

    assertBoardEqual(
      Array(
        Array(true, false, false, false, false),
        Array(false, true, true, false, false),
        Array(false, false, false, true, false),
        Array(false, false, false, false, false),
        Array(true, false, false, false, true)
      ),
      state
    )

  @Test def test_horizontal(): Unit =
    val board = Array(
      Array(22, 13, 17, 11, 0),
      Array(8, 2, 23, 4, 24),
      Array(21, 9, 14, 16, 7),
      Array(6, 10, 3, 18, 5),
      Array(1, 12, 20, 15, 19)
    )
    val no = List(21, 9, 14, 16, 19)
    assertEquals(
      false,
      hasBoardWon(board, no)
    )
    val yes = List(21, 9, 14, 16, 7)
    assertEquals(
      true,
      hasBoardWon(board, yes)
    )
    val stillYes = List(12, 21, 9, 14, 16, 19, 7, 20)
    assertEquals(
      true,
      hasBoardWon(board, stillYes)
    )

  @Test def test_vertical(): Unit =
    val board = Array(
      Array(22, 13, 17, 11, 0),
      Array(8, 2, 23, 4, 24),
      Array(21, 9, 14, 16, 7),
      Array(6, 10, 3, 18, 5),
      Array(1, 12, 20, 15, 19)
    )
    val no = List(17, 4, 8, 16, 6, 15)
    assertEquals(
      false,
      hasBoardWon(board, no)
    )
    val yes = List(11, 4, 8, 16, 6, 15, 18, 15, 19)
    assertEquals(
      true,
      hasBoardWon(board, yes)
    )

  @Test def test_scoring(): Unit =
    val board = Array(
      Array(22, 13, 17),
      Array(8, 2, 23),
      Array(21, 9, 14),
    )
    val numbers = List(22, 13, 5, 1, 2, 94, 9)

    assertEquals(
      83 * 9,
      scoreBoard(board, numbers)
    )

  @Test def test_example(): Unit =
    val numbers = List(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24, 10, 16, 13, 6, 15,
      25, 12, 22, 18, 20, 8, 19, 3, 26, 1)

    val boards = List(
      Array(
        Array(22, 13, 17, 11, 0),
        Array(8, 2, 23, 4, 24),
        Array(21, 9, 14, 16, 7),
        Array(6, 10, 3, 18, 5),
        Array(1, 12, 20, 15, 19)
      ),
      Array(
        Array(3, 15, 0, 2, 22),
        Array(9, 18, 13, 17, 5),
        Array(19, 8, 7, 25, 23),
        Array(20, 11, 10, 24, 4),
        Array(14, 21, 16, 12, 6)
      ),
      Array(
        Array(14, 21, 17, 24, 4),
        Array(10, 16, 15, 9, 19),
        Array(18, 8, 23, 26, 20),
        Array(22, 11, 13, 6, 5),
        Array(2, 0, 12, 3, 7)
      )
    )

    assertEquals(4512, getWinningScore(boards, numbers))
    assertEquals(1924, getLosingScore(boards, numbers))
