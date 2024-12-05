package ascii

import ascii.*

import scala.collection.mutable.ArrayBuffer

import java.awt.image.BufferedImage

class App(args: Array[String]) {
  private val greyscale_max : Int = 255

  // TODO DEFAULT TABLE

  private val parser : ConsoleParser = new ConsoleParser()
  private val importer : Importer = new Importer()
  private val exporter : Exporter = new Exporter()
  private var buf_img : Option[BufferedImage] = None
  private var gs_img : Option[GreyScaleImg] = None
  private var ascii_img : Option[AsciiImg] = None
  private var transform : Option[Transform] = None
  private val ascii_converter : AsciiConverter = new AsciiConverter()
  private val gs_converter : GreyScaleConverter = new GreyScaleConverter()

  // TODO
  private val min_height = 4
  private val max_height = 100
  private val min_width = 4
  private val max_width = 80

  private def setRandomImg(min_height: Int, max_height: Int, min_width: Int, max_width: Int) : Unit = {
    gs_img = Some(RandomGreyScaleImgGenerator.generate(min_height, max_height, min_width, max_width, 0, greyscale_max))
  }

  private def setPathImg(img_path: String) : Unit = {
    buf_img = Some(importer.importing(img_path))
  }

  private def createInput(img : Either[ExistImg, RandomImg]) : Unit = {
    img match {
      case Right(random) =>
        setRandomImg(min_height, max_height, min_width, max_width)
      case Left(existing) =>
        setPathImg(existing.path)
    }
  }

  private def setDefaultTable(characters: String) : Unit = {
    transform = Some(new LinearTransform(characters, greyscale_max))
  }

  private def setPredefinedTable(name: String) : Unit = {
    transform = PredefinedTransforms.getTransform(name)
    if (transform.isEmpty) {
      throw new Exception(s"Ascii conversion table named $name was not recognized!")
    }
  }

  private def setCustomTable(custom: customTable) : Unit = {
    val non_linear_class_arr = ArrayBuffer[NonLinearClass]()

    for (rule <- custom.rules) {
      non_linear_class_arr += NonLinearClass(rule.character, rule.start, rule.end)
    }

    transform = Some(new NonLinearTransform(non_linear_class_arr, greyscale_max))
  }

  private def createTransform(table : Either[definedTable, customTable]) : Unit = {
    table match {
      case Right(custom) =>
        if (custom.rules.isEmpty) {
          setDefaultTable(custom.characters)
          return
        }
        if (! isValidCustomTable(custom)) {
          throw new Exception("List of symbols in characters and in rules are different!")
        }
        setCustomTable(custom)
      case Left(defined) =>
        setPredefinedTable(defined.name)
    }
  }

  private def createOutput(outputs : List[inOutput], img : AsciiImg) : Unit = {
    for(out <- outputs) {
      out.target match {
        case "file" => out.path match {
          case Some(filepath) => exporter.appendOutput(new FileOutputer(img, filepath))
          case None => throw new Exception(s"filepath not specified")
        }
        case "console" => exporter.appendOutput(new ConsoleOutputer(img))
        case t => throw new Exception(s"output target named $t unrecognized")
      }
    }
  }

  private def getTransform(): Transform = {
    transform match {
      case Some(trans) => trans
      case None => throw new Exception("Unexpected app behaviour - No transform inside app!")
    }
  }

  private def createAsciiImg() : Unit = {
    val grey_img = getGreyscaleImg()
    ascii_img = Some(ascii_converter.convert(grey_img, getTransform()))
  }

  private def getGreyscaleImg() : GreyScaleImg = {
    gs_img match {
      case Some(img) => img
      case None => throw new Exception("Unexpected app behaviour - No greyscale image inside app!")
    }
  }

  private def filterImage(filters: List[inFilter]) : Unit = {
    val grey_img: GreyScaleImg = getGreyscaleImg()
    val filter_applier : ascii.FilterApplier = new ascii.FilterApplier(grey_img)
    for(filter <- filters) {
//      filter_applier.appendFilter(new )
    }
    gs_img = Some(filter_applier.run()) // fully filtered image
  }

  private def getAsciiImg(): AsciiImg = {
    ascii_img match {
      case Some(img) => img
      case None => throw new Exception("Unexpected app behaviour - No ascii image inside app!")
    }
  }

  private def getBufImg() : BufferedImage = {
    buf_img match {
      case Some(img) => img
      case None => throw new Exception("")
    }
  }

  private def createGreyscaleImg() : Unit = {
    val bf_img = getBufImg()
    gs_img = Some(gs_converter.convert_buffered(bf_img))
  }

  private def output() : Unit = {
    exporter.run(getAsciiImg())
  }

  def run() : Unit = {
    val console_parsed : consoleParsed = parser.parse(args)
    createInput(console_parsed.img)

    if (console_parsed.table.isDefined) {
      createTransform(console_parsed.table.get)
    }
    if (gs_img.isEmpty) { // aka not random
      createGreyscaleImg()
    }

    filterImage(console_parsed.filters)
    createAsciiImg()
    output()
  }
}
