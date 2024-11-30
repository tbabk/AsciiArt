package FilterTests

import org.scalatest.funsuite.AnyFunSuite
import ascii._

class InvertTest  extends AnyFunSuite {
  test("Invert") {
    var invert = new Invert()
    var random_img_gen = new RandomGreyScaleImgGenerator()
    var min_height = 0
    var max_height = 26
    var min_width = 0
    var max_width = 26
    // test 10 samples
    for (i <- 0 until 10) {
      var random_img = random_img_gen.generate(min_height, max_height, min_width, max_width, 0, 255)
      var img_width = random_img.getWidth()
      var img_height = random_img.getHeight()
      var inverted_img = invert.apply(random_img)
      for (h <- 0 until img_height; w <- 0 until img_width) {
        assert((255 - random_img.getPixel(h, w)) == inverted_img.getPixel(h, w))
      }
    }

  }

}
