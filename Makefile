SHELL:=/usr/bin/env bash -euo pipefail -c
.DEFAULT_GOAL := help

CURRENT_DIR:=$(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))
PROJECT_VERSION=$(shell grep "^ThisBuild / version[ ]*:=[ ]*" version.sbt | sed -e 's/ThisBuild \/ version[ ]*:\=[ ]*\"\(.*\)\"/\1/g')

###  help                 : Prints usage information
.PHONY: help
help:
	@echo "Makefile usage:"
	@echo ""
	@sed -n 's/^###//p' < $(CURRENT_DIR)/Makefile | sort

###  format               : Format code
.PHONY: format
format:
	@sbt scalafmt

###  compile              : Compile project
.PHONY: compile
compile:
	@sbt clean +compile

###  test                 : Test project
.PHONY: test
test:
	@sbt test

###  build                : Clean, build and test project
.PHONY: build
build: compile test

###  changelog            : Create changelogs
.PHONY: changelog
	@git tag -a v"${PROJECT_VERSION}" -m "version ${PROJECT_VERSION}"
	@cz changelog --file-name "docs/release_notes/${PROJECT_VERSION}.md" v${PROJECT_VERSION}
	@cat "docs/release_notes/${PROJECT_VERSION}.md" | tail -n +3 > "docs/release_notes/${PROJECT_VERSION}.md"

###  release              : Creates a release
.PHONY: release
release: build
	@echo "Releasing version '${PROJECT_VERSION}'"
	@sbt dist
	@sbt +package
	@sbt +publishSigned
	@sbt sonatypeReleaseAll
	@git push origin v"${PROJECT_VERSION}"
	@gh release create v"${PROJECT_VERSION}" -F "docs/release_notes/${PROJECT_VERSION}.md" \
		./target/scala-2.12/scalatikz_2.12-${PROJECT_VERSION}.jar \
		./target/scala-2.13/scalatikz_2.13-${PROJECT_VERSION}.jar \
		./target/scala-3.5.0/scalatikz_3-${PROJECT_VERSION}.jar \
		./target/universal/ScalaTIKZ-${PROJECT_VERSION}.zip