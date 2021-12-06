import scala.compiletime.ops.boolean
import scala.reflect.ClassTag

type Board = Array[Array[Int]]
type BoardState = Array[Array[Boolean]]

def parseInput(input: List[String], boardSize: Int): (List[Int], List[Board]) =
  val numbers = inputToNumbers(input.head)

  var boards = List[Board]()
  var row = 0
  for (l <- input.tail)
    if (l == "")
      boards = Array.ofDim[Int](boardSize, boardSize) :: boards
      row = 0
    else
      boards.head(row) = l.split(" ").filter(x => x != "") map(x => x.toInt)
      row = row +1

  (numbers, boards.reverse)


def inputToNumbers(numbersInput: String): List[Int] =
  numbersInput.split(",").map(s => s.toInt).toList

def inputToBoards(boardsInput: List[String]): List[Board] =
  List[Board]()

def calculateBoardState(board: Board, numbers: List[Int]): BoardState =
  var state = Array.fill(board.length, board(0).length)(false)

  for (x <- 0 until board.length)
    for (y <- 0 until board(x).length)
      state(x)(y) = numbers.contains(board(x)(y))

  state


def getRows[A:ClassTag](board: Array[Array[A]]): Array[Array[A]] =
  var res = Array.ofDim[A](board.length*2, board(0).length)

  for (x <- 0 until board.length)
    for (y <- 0 until board(x).length)
      res(x)(y) = board(x)(y)
      res(x+board.length)(y) = board(y)(x)

  res

def hasBoardWon(board: Board, numbers: List[Int]): Boolean =
  val state = calculateBoardState(board, numbers)
  val rows = getRows(state)

  rows.exists(r => r.forall(x => x == true))

def scoreBoard(board: Board, numbers: List[Int]): Int =
  val lastNumber = numbers.reverse.head
  val sum = board
    .map(r => r.filter((x) => !numbers.contains(x)))
    .flatten
    .fold(0)((acc, x) => acc+x)

  sum * lastNumber

def getWinners(boards: List[Board], numbers: List[Int]): List[Board] =
  boards.filter(b => hasBoardWon(b, numbers))

def getLosers(boards: List[Board], numbers: List[Int]): List[Board] =
  boards.filter(b => hasBoardWon(b, numbers) == false)

def getWinningScore(boards: List[Board], numbers: List[Int]): Int =
  for (n <- 1 to numbers.length)
    val winners = getWinners(boards, numbers.take(n))
    if (winners.length > 0)
      return scoreBoard(winners.head, numbers.take(n))

  return -1

def getLosingScore(boards: List[Board], numbers: List[Int]): Int =
  var lastWinner: (Board, Int) = (Array(), -1)
  var losers = boards
  for (n <- 1 to numbers.length)
    var winners = getWinners(losers, numbers.take(n))
    losers = getLosers(losers, numbers.take(n))
    if (winners.length > 0)
      lastWinner = (winners.head, n)

  return scoreBoard(lastWinner._1, numbers.take(lastWinner._2))