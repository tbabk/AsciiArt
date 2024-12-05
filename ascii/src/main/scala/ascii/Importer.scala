package ascii

import java.awt.image.BufferedImage

class Importer {

  private val extensions : Set[String] = Set(".jpg", ".jpeg", ".png")

  private def getFileExtension(file_path: String) : Option[String] = {
    val idx = file_path.lastIndexOf(".")
    if (idx < file_path.length - 1 && idx != -1) {
      return Some(file_path.substring(idx + 1))
    }
      None
  }

  def importing(file_path: String) : BufferedImage = {
    val file_extension = getFileExtension(file_path)
    file_extension match {
      case Some(ext) => ext match {
        case "jpg" | "jpeg" | "png" => importingJPGPNG(file_path)
        case _ => throw new Exception("Unsupported file extension")
      }
      case None => throw new Exception("No known file extension present")
    }
  }

  def getValidExtensions : Set[String] = {
    extensions
  }

  private def importingJPGPNG(file_path : String) : BufferedImage = {
    val jpg_png_loader : JpgPngLoader = new JpgPngLoader()
    jpg_png_loader.load(file_path)
  }
}