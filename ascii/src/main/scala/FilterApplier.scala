abstract class FilterNode {
  def applyFilter(image: GreyScaleImg) : GreyScaleImg
  def getNext() : FilterNode
  def append(n: FilterNode) : Unit
}

object FilterNodeEmpty extends FilterNode {
  var next: FilterNode = FilterNodeEmpty

  override def applyFilter(image: GreyScaleImg): GreyScaleImg = image
  override def getNext(): FilterNode = next
  override def append(n: FilterNode): Unit = {
    next = n
  }
}

class FilterNodeVal(f: Filter) extends FilterNode {
  val filter = f
  var next: FilterNode = FilterNodeEmpty

  override def applyFilter(image: GreyScaleImg): GreyScaleImg = {
    filter.apply(image)
  }

  override def getNext(): FilterNode = next

  override def append(n: FilterNode): Unit = {
    next = n
  }
}


class FilterApplier(inputImg: GreyScaleImg) {
  var img = inputImg
  var first : FilterNode = FilterNodeEmpty
  var last : FilterNode = FilterNodeEmpty
  var length = 0

  def appendFilter(f: Filter): Unit = {
    last.append(new FilterNodeVal(f))
    last = last.getNext()

    if (length == 0) {
      first = last
    }
    length += 1
  }

  def run(): GreyScaleImg = {
    for (i <- 0 until length)
      img = first.applyFilter(img)
      first = first.getNext()
    img
  }
  def runActual(): GreyScaleImg = {
    img = first.applyFilter(img)
    first = first.getNext()
    img
  }
  def getLength() : Int = length
}
