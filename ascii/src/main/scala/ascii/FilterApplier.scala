package ascii

class FilterApplier(inputImg: GreyScaleImg) {
  var img: GreyScaleImg = inputImg
  var first : Option[Node[Filter, GreyScaleImg, GreyScaleImg]] = None
  var last : Option[Node[Filter, GreyScaleImg, GreyScaleImg]] = None

  def appendFilter(f: Filter): Unit = {
    last match {
      case Some(lastNode) =>
        lastNode.append(new ascii.FilterNode(f))
        last = lastNode.getNext()

      case None =>
        val newNode = new ascii.FilterNode(f)
        first = Some(newNode)
        last = Some(newNode)
    }
  }

  def run(): GreyScaleImg = {
    var current = first

    while (true) {
      current match {
        case Some(node) =>
          img = node.func(img)
          current = node.getNext()
        case None =>
          return img
      }
    }
    img
  }
  // def runActual(): GreyScaleImg = {
  //  // TODO
  //  img
  // }
  // def getLength() : Int = length
}
