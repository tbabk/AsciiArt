package ascii

class FilterNode(f: Filter) extends Node[Filter, GreyScaleImg, GreyScaleImg] {
  val filter : Filter = f

  override def func(in: GreyScaleImg) : GreyScaleImg = {
    filter.apply(in)
  }
}
