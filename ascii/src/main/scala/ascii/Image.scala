package ascii

import scala.reflect.ClassTag


type Matrix[T] = Array[Array[T]]


class Image[T: ClassTag] (width_ : Int, height_ : Int, img_arr: Matrix[T]) {
  var width: Int = width_
  var height: Int = height_

  var img: Matrix[T] = Array.ofDim[T](height, width)
  for (i <- 0 until height; j <- 0 until width) {
      img(i)(j) = img_arr(i)(j)
  }


  def checkBounds(y: Int, x: Int): Unit = {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IndexOutOfBoundsException(s"Index ($x, $y) out of bounds.")
    }
  }

  def getWidth(): Int = width

  def getHeight(): Int = height

  def getPixel(y: Int, x: Int): T = {
    checkBounds(y, x)
    img(y)(x)
  }

  def setPixel(y: Int, x: Int, value: T): Unit = {
    checkBounds(y, x)
    img(y)(x) = value
  }
}

type GreyScaleImg = Image[Int]
type AsciiImg = Image[Char]
