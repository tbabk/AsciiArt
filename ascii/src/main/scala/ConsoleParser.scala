case class inFilter(name: String, value: Option[String] = None)
case class inOutput(target: String, path: Option[String] = None)
case class consoleParsed(image: String, filters: List[inFilter], out: List[inOutput], random_image: Boolean = false)

class ConsoleParser {
  def parse(args: Array[String]) : consoleParsed = {
    var filters: List[inFilter] = List()
    var outputs: List[inOutput] = List()
    var image: String = ""
    var random_img : Boolean = false
    var img_count = 0

    var i = 0
    while (i < args.length) {
      args(i) match {
        case "--image" => {
          if (i + 1 >= args.length) {
            throw new Exception("image file not specified!")
          }
          if (img_count != 0)
          {
            throw new Exception("only one --image* can be specified!")
          }
          img_count += 1
          image = args(i + 1)
          i += 2
        }
        case "--image-random" => {
          if (img_count != 0)
          {
            throw new Exception("only one --image* can be specified!")
          }
          random_img = true
          i += 1
        }

        case "--output-file" => {
          if (i + 1 >= args.length) {
            throw new Exception("output file not specified!")
          }
          outputs = outputs :+ inOutput("file", Some(args(i + 1)))
          i += 2
        }
        case "--output-console" => {
          outputs = outputs :+ inOutput("console")
          i += 1
        }
        case "--rotate" => {
          if (i + 1 >= args.length) {
            throw new Exception("rotation amount not specified!")
          }

          // check if number afterward
          filters = filters :+ inFilter("rotate", Some(args(i + 1)))
          i += 2
        }
        case "--scale" => {
          if (i + 1 >= args.length) {
            throw new Exception("scale amount not specified!")
          }

          // check if number afterward
          filters = filters :+ inFilter("scale", Some(args(i + 1)))
          i += 2
        }
        case "--invert" => {
          filters = filters :+ inFilter("invert")
          i += 1
        }
      }
    }
    if (image.isEmpty && !random_img) {
       throw new Exception("--image* not specified")
    }
    if (outputs.isEmpty) {
      throw new Exception("output not specified")
    }
    if (random_img) {
      consoleParsed(image, filters, outputs, random_img)
    }
    consoleParsed(image, filters, outputs)
  }

}
