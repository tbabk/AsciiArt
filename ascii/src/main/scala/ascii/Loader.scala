package ascii

import java.awt.image.BufferedImage
import java.io.*
import javax.imageio.ImageIO

class Loader {
}

abstract class FileLoader() extends Loader {
  def load(filePath: String) : BufferedImage
}

class JpgPngLoader extends FileLoader {
  override def load(filePath: String): BufferedImage = {
    ImageIO.read(new File(filePath))
  }
}
