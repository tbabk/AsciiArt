package Tests

import scala.util.matching.Regex

import org.scalatest.Assertions.fail
import scala.util.Random
import ascii._
import scala.util.{Try, Success, Failure}


def generateDouble(min: Double, max: Double) : Double = {
  min + (max - min) * Random.nextDouble()
}

def generateInt(min: Int, max: Int) : Int = {
  min + Random.nextInt((max - min) + 1)
}

object GenerateFilePath {
}

object GenerateFilterArgs {
  val pattern: Regex = """\$(\d+)""".r
  val min: Double = -100
  val max: Double = 100

  def generate(filter: String) : String = {
    val arg_count = filter match {
      case pattern(number) => Some(number.toInt)
      case _ => None
    }

    var args : String = ""
    for (i <- 0 until arg_count.get) {
      args += " " + generateDouble(min, max)
    }
    args
  }
}

object ConsoleTester {
  val filters: Array[String] = Array("--invert", "--scale $1", "--rotate $1")
  val images: Array[String] = Array("--image-random", "--image $1")
  val console_parser: ConsoleParser = new ConsoleParser()

  def generateWithTwoImage(num_args: Int): Array[String] = {
    val selected_images = Random.shuffle(images.toList).take(2)
    val rest = Random.shuffle((filters ++ images).toList).take(num_args - 2)
    val combined = Random.shuffle(selected_images ++ rest)
    combined.toArray.map(GenerateFilterArgs.generate)
  }

  def testInvalidAmountImage() : Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateWithTwoImage(5)))

    result match {
      case Success(_) =>
        fail("Expected an error when the number of arguments is invalid, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "only one --image* can be specified!", s"Did not expect to get: ${exception.getMessage}")
    }
  }


}
