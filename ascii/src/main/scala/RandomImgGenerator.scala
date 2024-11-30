//import java.awt.image.BufferedImage
//import scala.util.Random
//
////abstract class RandomImgGenerator[T](min_height_ : Int, max_height_ : Int, min_width_ : Int, max_width_ : Int) {
////  val min_height : Int = min_height_
////  val max_height : Int = max_height_
////  val min_width : Int = min_width_
////  val max_width : Int = max_width_
////
////  //
////  def generate(min_val: Int, max_val: Int) : Image[T]
////}
//
//class ascii.RandomGreyScaleImgGenerator(min_height__ : Int, max_height__ : Int, min_width__ : Int, max_width__ : Int) {
//  val min_height: Int = min_height__
//  val max_height: Int = max_height__
//  val min_width: Int = min_width__
//  val max_width: Int = max_width__
//
//  def generate(min_val: Int, max_val: Int): GreyScaleImg = {
//    val height = Random.between(min_height, max_height + 1)
//    val width = Random.between(min_width, max_width + 1)
//
//    val matrix = Array.fill(height, width)(Random.between(min_val, max_val + 1))
//    var img: GreyScaleImg = new GreyScaleImg(width, height, matrix)
//    img
//  }
//}