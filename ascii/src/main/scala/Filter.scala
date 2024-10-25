abstract class Filter {
  def apply(img: GreyScaleImg) : GreyScaleImg
}

class Rotate(degree_ : Int) extends Filter {
  val degree = degree_

  override def apply(img: GreyScaleImg): GreyScaleImg = {
    ???
  }
}

class Invert extends Filter {
  override def apply(img: GreyScaleImg): GreyScaleImg = {
    ???
  }
}

class Scale(scale_ : Double) extends Filter {
  override def apply(img: GreyScaleImg): GreyScaleImg = {
    ???
  }
}


