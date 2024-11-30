package ascii

abstract class Node[T, S, V] {
 var next : Option[Node[T, S, V]] = None

 def getNext() : Option[Node[T, S, V]] = next
 def append(in: Node[T, S, V]) : Unit = {
  next = Some(in)
 }
 def func(in: S) : V
}