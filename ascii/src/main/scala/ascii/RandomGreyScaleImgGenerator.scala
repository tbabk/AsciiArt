package ascii

import scala.util.Random

object RandomGreyScaleImgGenerator {
  def generate(min_height : Int, max_height : Int, min_width : Int, max_width : Int, min_val: Int, max_val: Int): GreyScaleImg = {
    val height = Random.between(min_height, max_height + 1)
    val width = Random.between(min_width, max_width + 1)

    val matrix : Matrix[Int] = Array.fill(height, width)(Random.between(min_val, max_val + 1))
    var img: GreyScaleImg = new GreyScaleImg(width, height, matrix)
    img
  }
}