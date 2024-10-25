class Transform(ascii_symbols_ : String) {
  val ascii_symbols : String = ascii_symbols_

  def getAsciiByGreyscale(greyscale: Double, isTrans: Boolean) : Char = {
    if (greyscale < 0 || greyscale >= 255){
        println("Invalid greyscale passed")}
    if (isTrans)
    {
      return ' '
    }
    val idx = ((greyscale * (ascii_symbols.length() - 1)) / 255).toInt
    ascii_symbols.charAt(idx)
  }
}
