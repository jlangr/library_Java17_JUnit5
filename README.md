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

Extending the Name Normalizer Exercise
---

There are numerous ways to add increments to the name normalizer exercise.

* The current set of requirements is very obviously biased toward Western names. Introduce "family-name" first naming, used in East Asia, parts of India, Hungary, and elsewhere. Seems to suggest the need for an indicator as to which naming convention is in use.
* Cover the case where a name already has middle initials:
  * John Q. Public  -> Public, John Q.
* Salutations: Dr, Mrs, Mr. etc. Known list? Or use a heuristic? Or have a separate field?
* Middle east: Common structure: Given name + Father’s name + Grandfather’s name + Family/Tribe name.
    Omar ibn (son of) Abdullah al-Farsi
    Religious or tribal identifiers often appear at the end (e.g., al- meaning “of the”).
* Dual surnames (Spanish, Portuguese, and others):
    Maria Garcia Lopez -> Garcia Lopez, Maria
* Cultural particles (e.g., "de", "von", "van", "da", "di", "le", "al"):
    Leonardo da Vinci -> da Vinci, Leonardo
* Nicknames provided:
    Robert "Bob" Smith -> Smith, Robert "Bob"
* Spaces omitted (some East Asian names):
    LiMing -> Li, Ming
* Religious names:
    Sister Mary Francis -> Francis, Sister Mary

Thoughts on AI Use
---

* AADV is the way to go, and is test-driven in spirit, though not as granular
* When test driving "by hand" and using an AI Assistant, writing the tests first will provide the LLM with context that improves its ability to generate the corresponding prod code.