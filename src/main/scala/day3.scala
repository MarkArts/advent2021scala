import util.control.Breaks._

type Bytes = List[Int]
type Diagnostic = List[Bytes]

def inputToDiagnostic(input: List[String]): Diagnostic =
  input.map(l => l.map(c => c.toString.toInt).toList)

def bytesToInt(bytes: Bytes): Int =
  Integer.parseInt(bytesToString(bytes), 2)

def bytesToString(bytes: Bytes): String =
  bytes.map(b => b.toString).foldLeft("")((acc, x) => acc+x)


def unique[T](xs: List[T]): Map[T, Int] =
  xs.foldLeft(Map[T,Int]())( (acc, x) =>
    acc get x match {
      case Some(v) => acc + (x -> (v + 1))
      case None => acc + (x -> 1)
  })

def filterBitsByIndex(compare: (Int, Int) => Boolean, default: Int)(i: Int, diagnostic: Diagnostic): Option[Int] =
  if (i > diagnostic(0).length -1 || i < 0)
    return None

  Some({
    val uniques = unique(diagnostic.map(d => d(i)))
    val sorted = uniques.toList.sortWith((a,b) => compare(a._2, b._2))
    if (sorted.length > 1 && sorted(0)._2 == sorted(1)._2 )
      default
    else
      sorted(0)._1
  })

def getMostCommonBit(i: Int, diagnostic: Diagnostic): Option[Int] =
  filterBitsByIndex((a,b) => a > b, 1)(i, diagnostic)

def getLeastCommonBit(i: Int, diagnostic: Diagnostic): Option[Int] =
  filterBitsByIndex((a,b) => a < b, 0)(i, diagnostic)


def getRate(input: Diagnostic, filterBitsByIndex: (i: Int, diagnostic: Diagnostic) => Option[Int] ): Int =
  val fixedLength = input(0).length // this assumes each line is the same length
  var bytes = Range(0, fixedLength).map( (i) => filterBitsByIndex(i, input) )

  // Throw error if any lookup was None
  var lift = bytes.map((x) => x match {
    case Some(v) => v
    case None => throw new RuntimeException("Array length of diagnostic to short")
  })

  bytesToInt(lift.toArray.toList)

def getGammaRate(input: Diagnostic): Int =
  getRate(input, getMostCommonBit)

def getEpsilonRate(input: Diagnostic): Int =
  getRate(input, getLeastCommonBit)


def getFilterredRating(input: Diagnostic, filterBitsByIndex: (i: Int, diagnostic: Diagnostic) => Option[Int], default: Int ): Int =
  val fixedLength = input(0).length // this assumes each line is the same length

  var filterred = input
  breakable {
    for (i <- 0 to fixedLength)
      val filterbit = filterBitsByIndex(i, filterred) match {
        case None => throw RuntimeException(s"Bit index ${i} out of range of bytes ${filterred}")
        case Some(v) => v
      }

      filterred = filterred.filter(b => b(i) == filterbit)
      if (filterred.length == 1) break
  }
  if (filterred.length != 1) throw RuntimeException("No single value found for input")

  bytesToInt(filterred(0))

def getOxygenRating(input: Diagnostic): Int =
  getFilterredRating(input, getMostCommonBit, 1)

def getCO2Rating(input: Diagnostic): Int =
  getFilterredRating(input, getLeastCommonBit, 0)