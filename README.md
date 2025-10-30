# library_Java17_JUnit_5

This source base requires JDK 17 or later.

Contact me at jeff @ langrsoft.com if you have any problems.

Loading the Project in IDEA
---

Import using the build.gradle file located in the root of this repo. The defaults should work.

Running the Application
---

./gradlew bootRun


Running the Unit Tests: IntelliJ IDEA Instructions (TDD class only)
---

* In the project tool window, expand library_full -> library -> src -> test -> junit.
* Select AllFastTests. Right-click and select Run `AllFastTests`.
* You should see at least 200 green unit tests, and they should run in a second or so at most.


SonarLint customizations
---
These rules should either be customized or turned off:

Minor java:S3577
Test classes should comply with a naming convention
  change Options: format to ^((A|An|Test|IT)[a-zA-Z0-9_]+|[A-Z][a-zA-Z0-9_]*(Test|Tests|TestCase|IT|ITCase))$


Disclaimer
---

Some of the source in the codebase deliberately stinks. Some of it stinks because, well, it's easy for all of us to write code we're soon not proud of. (No worries--we accept that reality and know that we can incrementally improve the code.)