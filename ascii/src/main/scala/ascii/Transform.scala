package ascii

import scala.collection.mutable.ArrayBuffer


abstract class Transform(greyscale_max_val: Int) {
  val table : Array[Char] = Array.ofDim[Char](greyscale_max_val + 1)

  def isValid(in: Int): Boolean = {
    if (in < 0 || in > greyscale_max_val) false else true
  }
//  def getAscii(in: Int) : Option[Char]
  def getAscii(in: Int): Option[Char] = {
    if (isValid(in)) Some(table(in)) else None
  }
}

class LinearTransform(ascii_chars : String, greyscale_max_val : Int) extends Transform(greyscale_max_val) {
//  val table : Array[Char] = Array.ofDim[Char](greyscale_max_val + 1)
  var it = 0
  private var own : Int = (greyscale_max_val + 1) / (ascii_chars.length)

  private var ascii_idx = 0

  while (it != greyscale_max_val + 1)
  {
    for(i <- 0 until own) {
      table(it) = ascii_chars.charAt(ascii_idx)
      it += 1
    }
    ascii_idx += 1
  }

//  def isValid(in: Int) : Boolean = {
//    if (in < 0 || in > greyscale_max_val) false else true
//  }

//  override def getAscii(in: Int): Option[Char] = {
//    if (isValid(in)) Some(table(in)) else None
//  }
}

case class NonLinearClass(ascii: Char, min_gs: Int, max_gs: Int)

class NonLinearTransform(ascii_chars : ArrayBuffer[NonLinearClass], greyscale_max_val : Int) extends Transform(greyscale_max_val) {
  for (as <- ascii_chars) {
    if (!isValid(as.min_gs) || !isValid(as.max_gs)) {
      throw new Exception("Greyscale number passed to NonLinearTransform out of greyscale_max_val range")
    }
    if (as.min_gs > as.max_gs) {
      throw new Exception("Interval has to start with lower value!")
    }
    var it = as.min_gs
    while (it <= as.max_gs) {
      if (table(it) != '\u0000') {
        throw new Exception(s"Redefinition of greyscale value for index ${it}!")
      }
      table(it) = as.ascii
      it += 1
    }

  }
}

object PredefinedTransforms {
  private var transforms : Map[String, Transform] = Map(
    "Bourke-Small" -> new LinearTransform(" .:-=+*#%@", 255),
    "Bourke-Big" -> new LinearTransform("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ", 255)
  )

  def getTransform(name: String): Option[Transform] = transforms.get(name)
}