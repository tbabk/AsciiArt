//package Tests.FilterTests
//
//import org.scalatest.funsuite.AnyFunSuite
//import ascii._
//
//
//class ScaleTest extends AnyFunSuite {
//  test("ScaleUp") {
//    var random_img_gen = new RandomGreyScaleImgGenerator()
//    var min_height = 0
//    var max_height = 25
//    var min_width = 0
//    var max_width = 25
//    var random_img = random_img_gen.generate(min_height, max_height, min_width, max_width, 0, 255)
//
//    for (i <- 4 to 10 by 2) {
//      var scale = new Scale(i.toDouble)
//      var scaled_img = scale.apply(random_img)
//    }
//
//  }
//}
