package ascii

class OutputNode(out: Outputer) extends Node[Outputer, AsciiImg, Unit] {
  val outputer : Outputer = out

  override def func(in: AsciiImg) : Unit = {
    outputer.output()
  }
}

//class Exporter(inputImg: AsciiImg) {
class Exporter {
//  var img: AsciiImg = inputImg
  var first : Option[Node[Outputer, AsciiImg, Unit]] = None
  var last : Option[Node[Outputer, AsciiImg, Unit]] = None

  def appendOutput(out: Outputer): Unit = {
    last match {
      case Some(lastNode) =>
        lastNode.append(new OutputNode(out))
        last = lastNode.getNext()

      case None =>
        var newNode = new OutputNode(out)
        first = Some(newNode)
        last = Some(newNode)
    }
  }

  def run(img: AsciiImg): Unit = {
    var current = first

    while (true) {
      current match {
        case Some(node) =>
          node.func(img)
          current = node.getNext()
        case None =>
          return
      }
    }
  }
}
