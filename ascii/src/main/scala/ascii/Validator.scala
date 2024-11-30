package ascii

import scala.collection.mutable


def validateFilterDoubleArgs(arg: String): Boolean = {
  arg.toDoubleOption.isDefined
}

def isValidCustomTable(table: customTable) : Boolean = {
  var charsSet = mutable.Set[Char]()
  var rulesSet = mutable.Set[Char]()

  // ++= adds all characters to the set
  charsSet ++= table.characters
  for (rule <- table.rules) {
    rulesSet += rule.character
  }

  if (charsSet == rulesSet) {
    return true
  }
  false
}