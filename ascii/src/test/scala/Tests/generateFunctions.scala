package Tests

import scala.util.Random

def generateDouble(min: Double, max: Double) : Double = {
  min + (max - min) * Random.nextDouble()
}

def generateInt(min: Int, max: Int) : Int = {
  min + Random.nextInt((max - min) + 1)
}

def generateString(len: Int): String = {
  Random.alphanumeric.take(len).mkString
}

def generateAscii(charset: String, len: Int): String = {
  if (charset.isEmpty) {
    return ""
  }
  var generated: String = ""
  for (i <- 0 until len) {
    val idx = generateInt(0, charset.length - 1)
    generated += charset(idx)
  }
  generated
}
