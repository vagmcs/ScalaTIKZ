# Runtime Requirements:

ScalaTIKZ requires an underlying TeXLive distribution to be installed on your system
in order to produce PGF/TIKZ vector graphics. Moreover, optionally depends on the GhostScript
package for exporting graphics into image formats.

#### Install TeXLive on a ***Debian-based*** system:
```bash
$ sudo apt-get install texlive-full
```

#### Install TeXLive on a ***Apple Mac OS***

[Macports](https://www.macports.org):
```bash
sudo port install texlive
```

[Homebrew](http://brew.sh):
```bash
$ brew tap caskroom/cask
$ brew cask install mactex
```

#### Install GhostScript on a ***Debian-based*** system:
```bash
$ sudo apt-get install ghostscript
```

#### Install GhostScript on a ***Apple Mac OS***

[Macports](https://www.macports.org):
```bash
$ sudo port install ghostscript
```

[Homebrew](http://brew.sh):
```bash
$ brew install ghostscript
```
