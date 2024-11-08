import java.io._

abstract class Outputer {
  def output() : Unit
}

class FileOutputer(img_ : AsciiImg, filePath: String) extends Outputer {
  val img_width : Int = img_.getWidth()
  val img_height : Int = img_.getHeight()
  val img: AsciiImg = img_

  override def output() : Unit = {
    try {
      val fw = new FileWriter(filePath)
      for (i <- 0 until img_width) {
        for (j <- 0 until img_height) {
          fw.write(img.getPixel(i, j))
        }
        fw.write("\n")
      }
      fw.close()
    }
    catch {
      case e: FileNotFoundException =>
        println(s"Error: The file or directory could not be found: ${e.getMessage}")
      case e: IOException =>
        println(s"Error: An I/O error occurred while writing to the file $filePath: ${e.getMessage}")
      case e: Exception =>
        println(s"Error: OOPS.., Something went wrong: ${e.getMessage} ")
    }
  }

}

class ConsoleOutputer(img_ : AsciiImg) extends Outputer {
  val img_width : Int = img_.getWidth()
  val img_height : Int = img_.getHeight()
  val img: AsciiImg = img_

  override def output() : Unit = {
    for (i <- 0 until img_width) {
      for (j <- 0 until img_height) {
        print(img.getPixel(i, j))
      }
      println()
    }
  }

}
