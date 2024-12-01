package Tests

import scala.collection.mutable.ListBuffer
import org.scalatest.Assertions.fail
import scala.util.Random
import ascii._
import scala.util.{Try, Success, Failure}

object ArgGenerator {
  private val ascii_set: String = "@$*!{},"
  private val ascii_len: Int = 2
  private val str_len: Int = 2
  private val min_double: Double = -10.0
  private val max_double: Double = 10.0

  def generate(input: String) : String = {
    val result = input
      .replace("$A", generateAscii(ascii_set, ascii_len))
      .replace("$S", generateString(str_len))
      .replace("$D", generateDouble(min_double, max_double).toString)
    result
  }
}

object ConsoleTester {
  private val filters: Array[String] = Array("--invert", "--scale $D", "--rotate $D")
  private val images: Array[String] = Array("--image-random", "--image $S")
  private val tables: Array[String] = Array("--table $A", "--custom-table $S")
  private val outs: Array[String] = Array("--output-file $S", "--output-console")

  private val console_parser: ConsoleParser = new ConsoleParser()

  def generateValidArgs(table: Boolean, filter: Int, out: Int): Array[String] = {
    if (table) {
      return (Random.shuffle(generate(tables, 1) ++ generate(images, 1) ++ generate(outs, out) ++ generate(filters, filter))).toArray
    }
    (Random.shuffle(generate(images, 1) ++ generate(outs, out) ++ generate(filters, filter))).toArray
  }

  def generateWithTwoFirst(first: Array[String], second: Array[String], num_args: Int) : Array[String] = {
    val selected_first = generate(first, 2)
    val rest = generate(second ++ first, num_args - 2)
    val combined = Random.shuffle(selected_first ++ rest)
    (combined.toArray).flatMap(_.split(" "))
  }

  def testInvalidAmountImage() : Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateWithTwoFirst(images, filters, 5)))

    result match {
      case Success(_) =>
        fail("Expected an error when the number of arguments for image is invalid, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "only one --image* can be specified!", s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def testInvalidAmountTable() : Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateWithTwoFirst(tables, filters, 5)))

    result match {
      case Success(_) =>
        fail("Expected an error when the number of arguments for table is invalid, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "Only one table can be specified!", s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def generate(list: Array[String], amount: Int) : List[String] = {
    val buffer = ListBuffer[String]()
    for (_ <- 1 to amount) {
      buffer += ArgGenerator.generate(Random.shuffle(list).head)
    }
    buffer.toList
  }

  def generateNoImg(filter: Int, out: Int) : Array[String] = {
    val filters_ : List[String] = generate(filters, filter)
    val outs_ : List[String] = generate(outs, out)
    val combined = Random.shuffle(filters_ ++ outs_)
    (combined.toArray).flatMap(_.split(" "))
  }

  def testNoImg() : Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateNoImg(4, 2)))

    result match {
      case Success(_) =>
        fail("Expected an error when there is no --image, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "--image* not specified", s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def generateNoOut(filter: Int) : Array[String] = {
    val filters_ : List[String] = generate(filters, filter)
    val imgs_ : List[String] = generate(images, 1)
    val combined = Random.shuffle(filters_ ++ imgs_)
    (combined.toArray).flatMap(_.split(" "))
  }

  def testNoOut(): Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateNoOut(3)))

    result match {
      case Success(_) =>
        fail("Expected an error when there is no --output, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "output not specified", s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def generateInvalidFilterArgs(outputs: Int, filt: Int) : Array[String] = {
    val invalid_filters = Array("--rotate $A", "--scale $A")
    val filts = generate(invalid_filters, filt)
    val imgs = generate(images, 1)
    val out = generate(outs, outputs)
    val combined = Random.shuffle(filts ++ imgs ++ out)
    (combined.toArray).flatMap(_.split(" "))
  }

  def testInvalidFilterArgs(): Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateInvalidFilterArgs(2, 3)))

    result match {
      case Success(_) =>
        fail("Expected an error when there is a filter with an invalid argument, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage.endsWith("is not a valid numerical!"), s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def generateNoFilterArgs(outputs: Int, filt: Int) : Array[String] = {
    val invalid_filters = Array("--rotate", "--scale")
    val combined = generateValidArgs(false, filt, outputs) ++ generate(invalid_filters, 1)
    combined.flatMap(_.split(" "))
  }

  def testNoFilterArgs() : Unit = {
    val result: Try[Unit] = Try(console_parser.parse(generateNoFilterArgs(2, 3)))

    result match {
      case Success(_) =>
        fail("Expected an error when there is a filter with no argument, but parse succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage.endsWith("amount not specified!"), s"Did not expect to get: ${exception.getMessage}")
    }
  }
}
