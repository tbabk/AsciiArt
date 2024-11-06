//class GreyScaleImg(width_ : Int, height_ : Int, img_arr : Array[Array[Int]]) {
//  var width : Int = width_
//  var height : Int = height_
//  var img : Array[Array[Int]] = img_arr.clone()
//
//  def checkBounds(x: Int, y: Int) : Unit = {
//    if (x < 0 || x >= width || y < 0 || y >= height) {
//      throw new IndexOutOfBoundsException(s"Index ($x, $y) out of bounds.")
//    }
//  }
//  def getWidth() : Int = width
//  def getHeight() : Int = height
//  def getPixel(x: Int, y: Int) : Int = {
//    checkBounds(x, y)
//    img(x)(y)
//  }
//  def setPixel(x: Int, y: Int, value: Int) : Unit = {
//    checkBounds(x, y)
//    img(x)(y) = value
//  }
//}
