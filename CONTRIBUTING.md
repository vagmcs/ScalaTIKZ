# Contributing to ScalaTIKZ

Do you like ScalaTIKZ and want to get involved? Cool! That's great!

Please take a moment and review this document, in order to make the contribution process easy and effective for everyone involved.

## Core Ideas

The purpose of ScalaTIKZ is to provide both a usable tool and a library for TIKZ and PGF vector graphics using a high level API or CLI to describe the graphics of interest. ScalaTIKZ currently supports a subset of PGF plot library. As a tool, ScalaTIKZ aims to provide useful features around TIKZ and PGF vector graphics that are mature enough in terms of usability and stability. We prefer to postpone the release of a feature, in order to have implementations that are clean in terms of user experience and development friendliness, well-documented (documentation and examples) and well-tested (unit tests, example code).

The master branch, contains the stable versions of ScalaTIKZ and is considered frozen. Thus, no active development should take place on the master branch. Instead, any pull requests or minor improvements should be made in the development version in order to be included in the next release candidate. The develop branch, contains the latest development snapshot of ScalaTIKZ, and thus all contributions should be made there.

## Submitting a Pull Request

Good pull requests, such as patches, improvements, and new features, are a fantastic help. They should remain focused in scope and avoid containing unrelated commits.

Please **ask first** if somebody else is already working on this or the core developers think your feature is in-scope for ScalaTIKZ. Generally, always have a related issue having discussions for whatever you are about to include.

Please also provide a test plan, i.e., specify how you verified that your patch works, add unit tests or provide examples that facilitate understanding the importance of the feature.

Finally, since master branch is only for stable releases tagged with a version, **a pull request should be always target to the develop branch.**

Thank you again for considering contributing to ScalaTIKZ and happy hacking :)