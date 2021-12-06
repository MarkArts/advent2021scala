type Line = (Vector2, Vector2)
type Grid = Map[(Vector2), Int]

def parseLine(line: String): Line =
    val split = line.split(" -> ")
    val a = split(0).split(",")
    val b = split(1).split(",")
    (
      Vector2( a(0).toInt, a(1).toInt),
      Vector2( b(0).toInt, b(1).toInt)
    )

def isStraight(line: Line): Boolean =
  line._1.x == line._2.x || line._1.y == line._2.y

def isDiagonal(line: Line): Boolean =
  degrees(line) % 45 == 0

def degrees(line: Line): Int =
  Math.floor(
    Math.atan2(line._2.y - line._1.y, line._2.x - line._1.x) * (180 / Math.PI)
  ).toInt

def drawGrid(lines: List[Line]): Grid =
  lines
    .map(drawLine)
    .foldLeft(Map[(Vector2), Int]())
    (mergeGrid)

def drawOnlyStraight(lines: List[Line]): Grid =
  drawGrid(lines.filter(isStraight))

def drawLine(lines: Line): Grid =
  var grid = Map[(Vector2), Int]()

  val minX = math.min(lines._1.x, lines._2.x)
  val maxX = math.max(lines._1.x, lines._2.x)
  val minY = math.min(lines._1.y, lines._2.y)
  val maxY = math.max(lines._1.y, lines._2.y)

  val direction = Vector2(minX, minY)

  for ( x <- minX to maxX )
    for ( y <- minY to maxY )
      grid = grid + ( Vector2(x, y) -> 1 )

  grid

def mergeGrid(gridA: Grid, gridB: Grid): Grid =
  var res = gridA
  for ( (k, v) <- gridB)
    res = res + (k -> (v + gridA.getOrElse(k, 0)) )

  res

def getIntersections(grid: Grid, min: Int): Int =
  grid
    .filter( (_, v) => v >= min)
    .values.fold(0)( (acc, _) => acc + 1 )