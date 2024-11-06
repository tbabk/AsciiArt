type Matrix[T] = Array[Array[T]]


class Image[T] (width_ : Int, height_ : Int, img_arr: Matrix[T]) {
  var width: Int = width_
  var height: Int = height_
  var img: Matrix[T] = img_arr.clone()

  def checkBounds(x: Int, y: Int): Unit = {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IndexOutOfBoundsException(s"Index ($x, $y) out of bounds.")
    }
  }

  def getWidth(): Int = width

  def getHeight(): Int = height

  def getPixel(x: Int, y: Int): T = {
    checkBounds(x, y)
    img(x)(y)
  }

  def setPixel(x: Int, y: Int, value: T): Unit = {
    checkBounds(x, y)
    img(x)(y) = value
  }
}

type GreyScaleImg = Image[Int]
type AsciiImg = Image[Char]
