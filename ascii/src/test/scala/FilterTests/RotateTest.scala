package FilterTests

import org.scalatest.funsuite.AnyFunSuite
import ascii._

class RotateTest extends AnyFunSuite {
  test("Rotate90") {
    var rotate90 = new Rotate(90)
    var random_img_gen = new RandomGreyScaleImgGenerator()
    var min_height = 0
    var max_height = 25
    var min_width = 0
    var max_width = 25
    // test 20 samples
    for (i <- 0 until 20) {
      println(i)
      var random_img = random_img_gen.generate(min_height, max_height, min_width, max_width, 0, 255)
      var img_width = random_img.getWidth()
      var img_height = random_img.getHeight()
      var rotated_img = rotate90.apply(random_img)
      for (h <- 0 until img_height; w <- 0 until img_width) {
        assert(random_img.getPixel(h, w) == rotated_img.getPixel(w, img_height - h - 1))
      }
    }
  }
}
