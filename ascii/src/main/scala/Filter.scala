abstract class Filter {
  def apply(img: GreyScaleImg) : GreyScaleImg
}

class Rotate(degree_ : Int) extends Filter {
  val degree = degree_

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    val (new_width, new_height, coorMapping) = degree match {
      case 90 | -270 =>
        (img.getHeight(), img.getWidth(), (x: Int, y: Int) => (y, img.getHeight() - x - 1))
      case 180 | -180 =>
        (img.getWidth(), img.getHeight(), (x: Int, y: Int) => (img.getHeight() - x - 1, img.getWidth() - y - 1))
      case 270 | -90 =>
        (img.getHeight(), img.getWidth(), (x: Int, y: Int) => (img.getWidth() - y - 1, x))
      case 360 | -360 =>
        (img.getWidth(), img.getHeight(), (x: Int, y: Int) => (x, y))
      case _ =>
        throw new Exception("Only rotation by n*90 for now!")
    }

    var rotatedImgMatrix = Array.ofDim[Int](new_width, new_height)

    for (x <- 0 until img.getWidth(); y <- 0 until img.getHeight()) {
      val (new_x, new_y) = coorMapping(x, y)
      rotatedImgMatrix(new_x)(new_y) = img.getPixel(x, y)
    }
    new GreyScaleImg(new_width, new_height, rotatedImgMatrix)
  }
}

class Invert extends Filter {
  val white = 255

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    val img_height = img.getHeight()
    val img_width = img.getWidth()
    for (x <- 0 until img_width; y <- 0 until img_height) {
        val new_val = white - img.getPixel(x, y)
        img.setPixel(x, y, new_val)
    }
    img
    }
}

class Scale(scale_ : Double) extends Filter {
  if ((scale_ % 2) != 0) {
    throw new Exception("Scale must be divisible by 2!")
  }
  val scale = scale_

  def scaleUp(img: GreyScaleImg) : GreyScaleImg = {
    val half_scale : Int = (scale / 2).toInt

    var new_img_width = (img.getWidth() * half_scale).toInt
    var new_img_height = (img.getHeight() * half_scale).toInt
    val emptyArray = Array.ofDim[Int](new_img_width, new_img_height)
    var new_img = new GreyScaleImg(new_img_width, new_img_height, emptyArray)

    for (x <- 0 until img.getWidth() ; y <- 0 until img.getHeight()) {
      for (i <- (x * half_scale) until (x * half_scale + half_scale)
           ; j <- (y * half_scale) until (y * half_scale + half_scale) )  {
        new_img.setPixel(i, j, img.getPixel(x, y))
      }
    }
    new_img
  }

  def scaleDown(img: GreyScaleImg) : GreyScaleImg = {
    val half_scale : Int = ((1 / scale) / 2).toInt

    var new_img_width = (img.getWidth() / half_scale).toInt
    var new_img_height = (img.getHeight() / half_scale).toInt
    val emptyArray = Array.ofDim[Int](new_img_width, new_img_height)
    var new_img = new GreyScaleImg(new_img_width, new_img_height, emptyArray)

    for (x <- 0 until new_img_width; y <- 0 until new_img_height) {
      var median : Int = 0
      for (i <- (x * half_scale) until (x * half_scale + half_scale)
           ; j <- (y * half_scale) until (y * half_scale + half_scale)) {
        median += img.getPixel(i, j)
      }
      median = median / (half_scale * 2)
      new_img.setPixel(x, y, median)
    }
    new_img
  }

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    if (scale == 1) {
      img
    }
    if (scale < 0.5) {
      //
      scaleDown(img)
    }
    if (scale > 2) {
      //
      scaleUp(img)
    }
    throw new Exception("invalid scale - must be either 1, >= 4, <= 0.25")
  }
}