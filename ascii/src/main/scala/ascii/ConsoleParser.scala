package ascii

case class inFilter(name: String, value: Option[String] = None)
case class inOutput(target: String, path: Option[String] = None)
// tables

// for user defined transform conversion rules
case class Rule(start: Int, end: Int, character: Char)

case class definedTable(name: String)
case class customTable(characters: String, rules: List[Rule])

// imgs
case class ExistImg(path: String)
case class RandomImg()

// consoleParser's output
case class consoleParsed(
                           img: Either[ExistImg, RandomImg],
                           out: List[inOutput],
                           table: Option[Either[definedTable, customTable]],
                           filters: List[inFilter],
                         )

class ConsoleParser {

  private def parseRules(rules: String) : List[Rule] = {
    if (rules.isEmpty) {
      return List()
    }
    rules.split(",").map {
      rule =>
        val parts = rule.split("=")
        val range = parts(0).split("-")

        if (range.length != 2) {
          throw new Exception("More - in one conversion rule")
        }

        val start = range(0).toInt
        val end = range(1).toInt

        val char = parts(1)
        if (char.length != 1) {
          throw new Exception(s"Invalid character: '$char' in conversion rule. Only one character is allowed!")
        }
        Rule(start, end, char.charAt(0))
    }.toList
  }

  def parse(args: Array[String]) : consoleParsed = {
    var img: Option[Either[ExistImg, RandomImg]] = None
    var outputs = List[inOutput]()
    var filters = List[inFilter]()
    var table: Option[Either[definedTable, customTable]] = None
    var custom_table_chars : String = String()

    var img_count = 0
    var table_count = 0

    var i = 0
    while (i < args.length) {
      args(i) match {
        case "--image" =>
          if (i + 1 >= args.length) {
            throw new Exception("image file not specified!")
          }
          if (img_count != 0)
          {
            throw new Exception("only one --image* can be specified!")
          }
          img_count += 1
          img = Some(Left(ExistImg(args(i + 1))))
          i += 2
        case "--image-random" =>
          if (img_count != 0)
          {
            throw new Exception("only one --image* can be specified!")
          }
          img_count += 1
          img = Some(Right(RandomImg()))
          i += 1
        case "--output-file" =>
          if (i + 1 >= args.length) {
            throw new Exception("output file not specified!")
          }
          outputs = outputs :+ inOutput("file", Some(args(i + 1)))
          i += 2
        case "--output-console" =>
          outputs = outputs :+ inOutput("console")
          i += 1
        case "--table" =>
          if (i + 1 >= args.length) {
            throw new Exception("--table name not specified!")
          }
          if (table_count > 0) {
            throw new Exception("Only one table can be specified!")
          }
          table_count += 1
          table = Some(Left(definedTable(args(i + 1))))
          i += 2
        case "--custom-table" =>
          if (i + 1 >= args.length) {
            throw new Exception("--custom-table characters not specified!")
          }
          if (table_count > 0) {
            throw new Exception("Only one table can be specified!")
          }
          table_count += 1

          custom_table_chars = args(i + 1)
          i += 2
        case "--conversion-rules" =>
          if (custom_table_chars.isEmpty) {
            throw new Exception("--custom-table argument not specified before --conversion-rules!")
          }
          if (i + 1 >= args.length) {
            throw new Exception("--conversion-rules param not specified!")
          }
          val rules : List[Rule] = parseRules(args(i + 1))
          table = Some(Right(customTable(custom_table_chars, rules)))
          i += 2
        case "--rotate" =>
          if (i + 1 >= args.length) {
            throw new Exception("rotation amount not specified!")
          }

          // check if number afterward
          if (! validateFilterDoubleArgs(args(i + 1))) {
            throw new Exception(s"rotation amount is not a valid numerical!")
          }
          filters = filters :+ inFilter("rotate", Some(args(i + 1)))
          i += 2
        case "--scale" =>
          if (i + 1 >= args.length) {
            throw new Exception("scale amount not specified!")
          }

          // check if number afterward
          if (!validateFilterDoubleArgs(args(i + 1))) {
            throw new Exception("scale amount is not a valid numerical!")
          }
          filters = filters :+ inFilter("scale", Some(args(i + 1)))
          i += 2
        case "--invert" =>
          filters = filters :+ inFilter("invert")
          i += 1
        case unknown =>
          throw new Exception(s"invalid argument: $unknown")
      }
    }

    if (img.isEmpty) {
      throw new Exception("--image* not specified")
    }
    if (outputs.isEmpty) {
      throw new Exception("output not specified")
    }

    consoleParsed(img.get, outputs, table, filters)
  }

}
