package Tests

import ascii.Importer
import scala.util.{Try, Success, Failure}
import org.scalatest.Assertions.fail

object ImportTester {
  val importer: Importer = new Importer()
  
  def testInvalidExtension() : Unit = {
    val result: Try[Unit] = Try(importer.importing(generateString(5)))

    result match {
      case Success(_) =>
        fail("Expected an error when the invalid extension is passed to importer, but import succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "No known file extension present", s"Did not expect to get: ${exception.getMessage}")
    }
  }

  def createUnsupportedExtension() : String = {
    while(true) {
      val extension = "." + generateString(3)
      if (! importer.getValidExtensions.contains(extension)) {
        return generateString(5) + extension
      }
    }
    ""
  }

  def testUnsupportedExtension() : Unit = {
    val ext = createUnsupportedExtension()
    val result: Try[Unit] = Try(importer.importing(ext))

    result match {
      case Success(_) =>
        fail("Expected an error when the unsupported extension is passed to importer, but import succeeded.")
      case Failure(exception) =>
        assert(exception.getMessage == "Unsupported file extension", s"Did not expect to get: ${exception.getMessage}, for extension ${ext}")
    }
  }
}
