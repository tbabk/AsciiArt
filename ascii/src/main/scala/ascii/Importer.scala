package ascii

import java.awt.image.BufferedImage

class Importer {

  private def getFileExtension(file_path: String) : Option[String] = {
    val idx = file_path.lastIndexOf(".")
    if (idx < file_path.length - 1) {
      return Some(file_path.substring(idx + 1))
    }
      None
  }

  def importing(file_path: String) : BufferedImage = {
    var file_extension = getFileExtension(file_path)
    file_extension match {
      case Some(ext) => ext match {
        case "jpg" | "jpeg" => importingJPGPNG(file_path)
        case _ => throw new Exception("Unsuported file extension")
      }
      case None => throw new Exception("No known file extension present")
    }
  }

  private def importingJPGPNG(file_path : String) : BufferedImage = {
    var jpg_png_loader : JpgPngLoader = new JpgPngLoader()
    jpg_png_loader.load(file_path)
  }
}