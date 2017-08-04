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

package scalatikz.common

sealed trait Colors {

  implicit def hasColorized(s: String): ColorizedString =
    new ColorizedString(s)

  class ColorizedString(s: String) {
    import Console._

    /**
      * Colorize the given string foreground to ANSI black.
      */
    def black: String = BLACK + s + RESET

    /**
      * Colorize the given string foreground to ANSI red.
      */
    def red: String = RED + s + RESET

    /**
      * Colorize the given string foreground to ANSI green.
      */
    def green: String = GREEN + s + RESET

    /**
      * Colorize the given string foreground to ANSI yellow.
      */
    def yellow: String = YELLOW + s + RESET

    /**
      * Colorize the given string foreground to ANSI blue.
      */
    def blue: String = BLUE + s + RESET

    /**
      * Colorize the given string foreground to ANSI magenta.
      */
    def magenta: String = MAGENTA + s + RESET

    /**
      * Colorize the given string foreground to ANSI cyan.
      */
    def cyan: String = CYAN + s + RESET

    /**
      * Make the given string bold.
      */
    def bold: String = BOLD + s + RESET

    /**
      * Underline the given string.
      */
    def underlined: String = UNDERLINED + s + RESET

    /**
      * Make the given string blink (some terminals may have this disabled).
      */
    def blink: String = BLINK + s + RESET

    /**
      * Reverse the ANSI colors of the given string.
      */
    def reversed: String = REVERSED + s + RESET

    /**
      * Make the given string invisible using ANSI color codes.
      */
    def invisible: String = INVISIBLE + s + RESET
  }
}