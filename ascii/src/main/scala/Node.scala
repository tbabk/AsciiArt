abstract class Node[T] {
 def getNext() : Node[T]
 def append(in : Node[T]) : Unit
}

class NodeVal[T] extends Node[T] {
  var next : Node[T] = Empty[T]
 override def getNext(): Node[T] = next
 override def append(in: Node[T]): Unit = {next = in}
}

class Empty[T] extends Node[T] {
 override def getNext(): Node[T] = this
 override def append(in: Node[T]): Unit = {}
}