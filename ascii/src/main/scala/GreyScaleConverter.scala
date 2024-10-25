import java.awt.image.BufferedImage

class GreyScaleConverter {
  def convertJpgPng(img: BufferedImage): GreyScaleImg = {
    val width = img.getWidth()
    val height = img.getHeight()
    var img_arr = Array.ofDim[Int](width, height)

    // loop through pixels:
    for (x <- 0 until width)
      for (y <- 0 until height)
        val rgb = img.getRGB(x, y)

        val red = (rgb >> 16) & 0xff
        val green = (rgb >> 8) & 0xff
        val blue = rgb & 0xff

        val alpha = (rgb >> 24) & 0xff
        var isTrans = false

        if (alpha == 0) {
          isTrans = true
        }
        val greyscale = ((0.3 * red) + (0.59 * green) + (0.11 * blue)).toInt
        img_arr(x)(y) = greyscale
    new GreyScaleImg(width, height, img_arr)    
  }
}
