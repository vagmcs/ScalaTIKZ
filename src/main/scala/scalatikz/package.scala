/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
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
