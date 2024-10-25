abstract class Node[T] {
 def apply(in: T) : T
 def getNext() : Node[T]
 def append(in : Node[T]) : Unit
}