import java.awt.image.BufferedImage

abstract class RandomImgGenerator[T](max_height_ : Int, max_width_ : Int) {
  val max_height = max_height_
  val max_width = max_width_

  def generate() : T
}

//class RandomGreyScaleImgGenerator(max_height__ : Int, max_width__ : Int) extends RandomImgGenerator[GreyScaleImg](max_height_ = max_height__, max_width_ = max_width__) {
//  def
//}