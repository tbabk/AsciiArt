package ascii

abstract class Filter {
  def apply(img: GreyScaleImg) : GreyScaleImg
}

class Rotate(degree_ : Int) extends Filter {
  private val degree : Int = degree_ % 360

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    val (new_width, new_height, coorMapping) = degree match {
      case 90 | -270 =>
        (img.getHeight(), img.getWidth(), (x: Int, y: Int) => (y, img.getHeight() - x - 1))
      case 180 | -180 =>
        (img.getWidth(), img.getHeight(), (x: Int, y: Int) => (img.getHeight() - x - 1, img.getWidth() - y - 1))
      case 270 | -90 =>
        (img.getHeight(), img.getWidth(), (x: Int, y: Int) => (img.getWidth() - y - 1, x))
      case 360 | -360 | 0 | -0 =>
        (img.getWidth(), img.getHeight(), (x: Int, y: Int) => (x, y))
      case _ =>
        throw new Exception("Only rotation by n*90 for now!")
    }

    var rotatedImgMatrix = Array.ofDim[Int](new_height, new_width)

    for (i <- 0 until img.getHeight(); j <- 0 until img.getWidth()) {
      val (new_x, new_y) = coorMapping(i, j)
      rotatedImgMatrix(new_x)(new_y) = img.getPixel(i, j)
    }
    new GreyScaleImg(new_width, new_height, rotatedImgMatrix)
  }
}

class Invert extends Filter {
  private val white : Int = 255

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    val img_height = img.getHeight()
    val img_width = img.getWidth()
    val img_arr = Array.ofDim[Int](img_height, img_width)

    for (y <- 0 until img_height; x <- 0 until img_width) {
        val new_val = white - img.getPixel(y, x)
        img_arr(y)(x) = new_val
    }
    new GreyScaleImg(img_width, img_height, img_arr)
    }
}

class Scale(scale_ : Double) extends Filter {
  if ((scale_ > 1.0 && (scale_ % 2.0) >= 0.0001) || (scale_ < 1.0 && ((1.0 / scale_) % 2.0) >= 0.0001)) {
    throw new Exception("Scale must be divisible by 2!")
  }
  private val scale: Double = scale_

  private def scaleUp(img: GreyScaleImg) : GreyScaleImg = {
    val half_scale : Int = (scale / 2).toInt

    var new_img_width = (img.getWidth() * half_scale).toInt
    var new_img_height = (img.getHeight() * half_scale).toInt
    val emptyArray = Array.ofDim[Int](new_img_height, new_img_width)
    var new_img = new GreyScaleImg(new_img_width, new_img_height, emptyArray)

//    for (x <- 0 until img.getWidth() ; y <- 0 until img.getHeight()) {
//      for (i <- (x * half_scale) until (x * half_scale + half_scale)
//           ; j <- (y * half_scale) until (y * half_scale + half_scale) )  {
//        new_img.setPixel(i, j, img.getPixel(x, y))
//      }
//    }
    for (y <- 0 until img.getHeight(); x <- 0 until img.getWidth()) {
      for (i <- (y * half_scale) until (y * half_scale + half_scale); j <- (x * half_scale) until (x * half_scale + half_scale)) {
        new_img.setPixel(i, j, img.getPixel(y, x))
      }
    }
    new_img
  }

  private def scaleDown(img: GreyScaleImg) : GreyScaleImg = {
    val half_scale : Int = ((1 / scale) / 2).toInt

    var new_img_width = (img.getWidth() / half_scale).toInt
    var new_img_height = (img.getHeight() / half_scale).toInt
    val emptyArray = Array.ofDim[Int](new_img_width, new_img_height)
    var new_img = new GreyScaleImg(new_img_width, new_img_height, emptyArray)

    for (y <- 0 until new_img_height; x <- 0 until new_img_width) {
      var median : Int = 0
      for (j <- (y * half_scale) until (y * half_scale + half_scale)
           ; i <- (x * half_scale) until (x * half_scale + half_scale)) {
        median += img.getPixel(j, i)
      }
      median = median / (half_scale * 2)
      new_img.setPixel(y, x, median)
    }
    new_img
  }

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    if (scale == 1.0) {
      return img
    }
    if (scale < 0.5) {
      //
      return scaleDown(img)
    }
    if (scale > 2.0) {
      //
     return scaleUp(img)
    }
    throw new Exception(s"invalid scale (${scale_}) - must be either 1, >= 4, <= 0.25")
  }
}