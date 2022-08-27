/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A PGF/TIKZ plot library for Scala.
 *     
 */

package object scalatikz {

  object BuildVersion {
    import java.net.URL

    private val version: String = {
      val clazz = scalatikz.BuildVersion.getClass
      try {
        val classPath = clazz.getResource("package$" + clazz.getSimpleName + ".class").toString

        if (classPath.startsWith("jar")) {
          val manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF"
          val manifest = new java.util.jar.Manifest(new URL(manifestPath).openStream)
          manifest.getMainAttributes.getValue("Specification-Version")
        } else "(undefined version)"

      } catch {
        case _: NullPointerException => "(undefined version)"
      }
    }

    def apply(): String = version
  }
}
