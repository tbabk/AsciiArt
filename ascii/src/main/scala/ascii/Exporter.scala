package ascii

class OutputNode(out: Outputer) extends Node[Outputer, AsciiImg, Unit] {
  private val outputer : Outputer = out

  override def func(in: AsciiImg) : Unit = {
    outputer.output()
  }
}

class Exporter {
  private var first : Option[Node[Outputer, AsciiImg, Unit]] = None
  private var last : Option[Node[Outputer, AsciiImg, Unit]] = None

  def appendOutput(out: Outputer): Unit = {
    last match {
      case Some(lastNode) =>
        lastNode.append(new OutputNode(out))
        last = lastNode.getNext()

      case None =>
        val newNode = new OutputNode(out)
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
