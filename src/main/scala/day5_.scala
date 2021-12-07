import java.awt.Color
type Line = (Vector2, Vector2)
type Grid[A] = Map[(Vector2), A]

def parseLine(line: String): Line =
    val split = line.split(" -> ")
    val a = split(0).split(",")
    val b = split(1).split(",")
    (
      Vector2( a(0).toInt, a(1).toInt),
      Vector2( b(0).toInt, b(1).toInt)
    )

def normalize(v: Vector2): Vector2 =
  val mag = Math.sqrt(v.x * v.x + v.y * v.y);
  if (mag == 0)
    throw new RuntimeException(s"Magntidue of vector ${v} was 0")

  Vector2(v.x / mag, v.y / mag)

def isStraight(line: Line): Boolean =
  line._1.x == line._2.x || line._1.y == line._2.y

def isDiagonal(line: Line): Boolean =
  degrees(line) % 45 == 0

def degrees(line: Line): Int =
  Math.round(
   radians(line) * (180 / Math.PI)
  ).toInt

def radians(line: Line): Double =
  Math.atan2(line._2.y - line._1.y, line._2.x - line._1.x)

def drawGrid(lines: List[Line]): Grid[Int] =
  lines
    .map(drawLine)
    .foldLeft(Map[(Vector2), Int]())
    (mergeGrid)

def mergeGrid(gridA: Grid[Int], gridB: Grid[Int]): Grid[Int] =
  var res = gridA
  for ( (k, v) <- gridB)
    res = res + (k -> (v + gridA.getOrElse(k, 0)) )

  res

def drawOnlyStraight(lines: List[Line]): Grid[Int] =
  drawGrid(lines.filter(isStraight))

def drawLine(lines: Line): Grid[Int] =
  var grid = Map[(Vector2), Int]()

  val start = lines._1
  val end = lines._2
  val direction = normalize(end - start)
  var current = start

  while(
    (if (direction.x > 0 )
      Math.round(current.x) <= end.x
    else
      Math.round(current.x) >= end.x) &&
    (if (direction.y > 0)
      Math.round(current.y) <= end.y
     else
      Math.round(current.y) >= end.y,
    )
  )
    grid = grid + ( Vector2(
        Math.round(current.x),
        Math.round(current.y),
      ) -> 1 )

    current += direction

  grid

def getIntersections(grid: Grid[Int], min: Int): Int =
  grid
    .filter( (_, v) => v >= min)
    .values.foldLeft(0)( (acc, _) => acc + 1 )


// bonus image drawing:
def drawColor(lines: Line): Grid[Color] =
  var grid = Map[(Vector2), Color]()

  val start = lines._1
  val end = lines._2
  val direction = normalize(end - start)
  var current = start

  var color = Color(
    (128 + ((start.x / 1000) * 255).toInt) % 256,
    (128 + ((start.y / 1000) * 255).toInt)  % 256,
    (128 + ((end.y / 1000) * 255).toInt) % 256,
  )

  while(
    (if (direction.x > 0 )
      Math.round(current.x) <= end.x
    else
      Math.round(current.x) >= end.x) &&
    (if (direction.y > 0)
      Math.round(current.y) <= end.y
     else
      Math.round(current.y) >= end.y,
    )
  )
    grid = grid + ( Vector2(
        Math.round(current.x),
        Math.round(current.y),
      ) -> color )

    color = Color(
      (color.getRed + Math.abs(direction.x)).toInt % 255,
      (color.getGreen + Math.abs(direction.y)).toInt % 255,
      (color.getRed + Math.abs(direction.y + direction.x)).toInt % 255
    )

    current += direction

  grid

def drawColorGrid(lines: List[Line]): Grid[Color] =
  lines
    .map(drawColor)
    .foldLeft(Map[(Vector2), Color]())
    (mergeColorGrid)

def mergeColorGrid(gridA: Grid[Color], gridB: Grid[Color]): Grid[Color] =
  var res = gridA
  for ( (k, v) <- gridB)
    res = res + (k -> sumColor(v, gridA.getOrElse(k, Color.black)) )

  res

def sumColor(a: Color, b: Color ): Color =
  Color(
    (a.getRed + b.getRed) % 255,
    (a.getGreen + a.getGreen) % 255,
    (a.getRed + a.getRed) % 255
  )