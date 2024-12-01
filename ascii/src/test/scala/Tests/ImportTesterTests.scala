package Tests

import org.scalatest.funsuite.AnyFunSuite

class ImportTesterTests extends AnyFunSuite {
  test("Invalid extension") {
    ImportTester.testInvalidExtension()
  }

  test("Unsupported extension") {
    ImportTester.testUnsupportedExtension()
  }
}
