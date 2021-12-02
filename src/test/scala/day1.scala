import org.junit.Test
import org.junit.Assert.*

class Day1:
  @Test def test_steps(): Unit =
    var input = List(1,2,3,4,5)
    assertEquals(List(Step(1,2), Step(2,3), Step(3,4), Step(4,5)), createSteps(input))
  @Test def test_countIncreased(): Unit = 
    var input = List(199, 200, 208, 210 ,200, 207, 240, 269, 260, 263)
    assertEquals(7, countIncreased(input))
  @Test def test_countIncreased_empty(): Unit = 
    var input = List()
    assertEquals(0, countIncreased(input))
  @Test def test_countIncreased_none(): Unit = 
    var input = List(10,9,8,7)
    assertEquals(0, countIncreased(input))

  @Test def test_sliding_window(): Unit =
      var input = List(607, 618, 618, 617, 647, 716, 769, 792)
      assertEquals(5, countSlidingIncrease(input))