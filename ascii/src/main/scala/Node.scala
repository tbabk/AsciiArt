abstract class Node[T, S, V] {
 var next : Option[Node[T, S, V]] = None

 def getNext() : Option[Node[T, S, V]] = next
 def append(in: Node[T, S, V]) : Unit = {
  next = Some(in)
 }
 def func(in: S) : V
}

class FilterNode_(f: Filter) extends Node[Filter, GreyScaleImg, GreyScaleImg] {
 val filter : Filter = f

 override def func(in: GreyScaleImg) : GreyScaleImg = {
  filter.apply(in)
 }
}

class FilterApplier(inputImg: GreyScaleImg) {
 var img: GreyScaleImg = inputImg
 var first : Option[Node[Filter, GreyScaleImg, GreyScaleImg]] = None
 var last : Option[Node[Filter, GreyScaleImg, GreyScaleImg]] = None

 def appendFilter(f: Filter): Unit = {
  last match {
   case Some(lastNode) =>
    lastNode.append(new FilterNode_(f))
    last = lastNode.getNext()

   case None =>
    var newNode = new FilterNode_(f)
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


//abstract class Node[T, S, V] {
// def getNext() : Node[T, S, V]
// def append(in : Node[T, S, V]) : Unit
// def funct(in: S) : V
//}
//
//class Empty[T, S, V] extends Node[T, S, V] {
// override def getNext(): Node[T, S, V] = this
// override def append(in: Node[T, S, V]): Unit = {}
//
// override def funct(in: S): V = ???
//}
//
//class NodeVal[T, S, V] extends Node[T, S, V] {
//  var next : Node[T, S, V] = new Empty[T, S, V]
// override def getNext(): Node[T] = next
// override def append(in: Node[T]): Unit = {next = in}
//}