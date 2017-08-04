package scalatikz.common

trait Colors {

  implicit def hasColorized(s: String): ColorizedString =
    new ColorizedString(s)

  class ColorizedString(s: String) {
    import Console._

    /** Colorize the given string foreground to ANSI black */
    def black: String = BLACK + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def red: String = RED + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def green: String = GREEN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def yellow: String = YELLOW + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def blue: String = BLUE + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def magenta: String = MAGENTA + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def cyan: String = CYAN + s + RESET
    /** Colorize the given string foreground to ANSI red */
    def white: String = WHITE + s + RESET

    /** Make the given string bold */
    def bold: String = BOLD + s + RESET
    /** Underline the given string */
    def underlined: String = UNDERLINED + s + RESET
    /** Make the given string blink (some terminals may turn this off) */
    def blink: String = BLINK + s + RESET
    /** Reverse the ANSI colors of the given string */
    def reversed: String = REVERSED + s + RESET
    /** Make the given string invisible using ANSI color codes */
    def invisible: String = INVISIBLE + s + RESET
  }
}