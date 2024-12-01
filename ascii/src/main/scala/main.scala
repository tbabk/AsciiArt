import ascii._


def main(args: Array[String]): Unit = {

  val console_parser : ConsoleParser = new ConsoleParser()
  val ret : consoleParsed = console_parser.parse(args)
  println(ret)
  //  val matrix = Array(
//    Array(1, 2, 3),
//    Array(4, 5, 6),
//    Array(7, 8, 9)
//  )
//  val scaler = new Scale(4.0)
//  var scaled = scaler.apply(new GreyScaleImg(3, 3, matrix))
//  for(i <- 0 until 6) {
//    for (j <- 0 until 6) {
//      print(scaled.getPixel(i, j))
//      print(",")
//    }
//    println("")
//  }
//
//  val matrix2 = Array(
//    Array(1, 2, 3, 4),
//    Array(5, 6, 7, 8),
//    Array(9, 10, 11, 12),
//    Array(13, 14, 15, 16)
//  )
//  val scaler2 = new Scale(0.25)
//  var scaled2 = scaler2.apply(new GreyScaleImg(4, 4, matrix2))
//  for (i <- 0 until 2) {
//    for (j <- 0 until 2) {
//      print(scaled2.getPixel(i, j))
//      print(",")
//    }
//    println("")
//  }
//  val scaler3 = new Scale(1)
//  var scaled3 = scaler3.apply(new GreyScaleImg(4, 4, matrix2))
//  for (i <- 0 until 4) {
//    for (j <- 0 until 4) {
//      print(scaled3.getPixel(i, j))
//      print(",")
//    }
//    println("")
//  }

  //  var app: ascii.App = new ascii.App(args.split(" "))
//  try {
//    app.run()
//  }
//  catch
//    case e : Exception => e.getMessage
}

