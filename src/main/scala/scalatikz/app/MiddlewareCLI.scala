package scalatikz.app

object MiddlewareCLI extends AppCLI[InitialConf]("scalatikz") {

  opt[Unit]('2', "2d".underlined).optional.maxOccurs(1)
    .action { (_, conf) => conf.copy(three = 2) }.text("2 dimensional figure.\n")

  opt[Unit]('3', "3d".underlined).optional.maxOccurs(1)
    .action { (_, conf) => conf.copy(three = 3) }.text("2 dimensional figure.\n")

  help("help").text("Print usage options.\n")

  version("version").text("Display the version.")

  /*
   * Parse arguments
   */
  parse(args, InitialConf()) match {
    case Some(conf) if conf.three == 3 => ???
    case Some(conf) if conf.three == 2 => ScalaTIKZ.main(this.args.tail)
    case _ =>
  }
}
