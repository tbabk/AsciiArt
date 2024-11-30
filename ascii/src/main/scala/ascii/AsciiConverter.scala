package ascii

class AsciiConverter {
    def convert(gs_img: GreyScaleImg, transformer: Transform) : AsciiImg = {
      val height = gs_img.getHeight()
      val width = gs_img.getWidth()

      val ascii_arr = Array.ofDim[Char](width, height)
      for(i <- 0 to height) {
        for(j <- 0 to width) {
          val pixel_val = gs_img.getPixel(i, j)
          transformer.getAscii(pixel_val) match {
            case Some(ascii_char) => ascii_arr(i)(j) = ascii_char
            case None => throw new Exception(s"Invalid Greyscale value at index: ($i, $j), pixel value: $pixel_val")
          }
        }
      }
      new AsciiImg(width, height, ascii_arr)
    }
}
