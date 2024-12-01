package Tests

import org.scalatest.funsuite.AnyFunSuite

class ConsoleTesterTests extends AnyFunSuite {
  test("Invalid amount of image arguments") {
    ConsoleTester.testInvalidAmountImage()
  }

  test("Invalid amount of table arguments") {
    ConsoleTester.testInvalidAmountTable()
  }

  test("No image argument") {
    ConsoleTester.testNoImg()
  }

  test("No output argument") {
    ConsoleTester.testNoOut()
  }

  test("Invalid filter argument") {
    ConsoleTester.testInvalidFilterArgs()
  }

  test("No filter argument") {
    ConsoleTester.testNoFilterArgs()
  }
}
