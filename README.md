# FRC5010Lib

This is the vendor template for FRC 5010 Tiger Dynasty common codebase.

## Json File

https://frc5010.github.io/FRC5010Lib/frc5010Lib.json

## Layout

The code is located under src/main/java/org/frc5010/common and is split into several sub folders<br/>
<ul>
    <li>arch - contains architectural files that undergird the library</li>
    <li>commands - common commands</li>
    <li>config - classes that read json config files to build a robot</li>
    <li>constants - folder for constants files</li>
    <li>drive - classes for defining drive systems and pose localization</li>
    <li>mechanisms - mechanisms can be used to organize commands and subsystems if needed</li>
    <li>motors - classes for handling motors and motor functions</li>
    <li>sensors - classes for handling many different types of sensors</li>
    <li>subsystems - classes for predefined subsystems</li>
    <li>telemetry - logging classes</li>
    <li>vision - additional classes for vision, some are deprecated</li>    
</ul>

## Customizing
For Java, the library name will be the folder name the build is starts from.

For the maven artifact names, those are all in publish.gradle about 40 lines down.

## Building and editing
This uses gradle, and uses the same base setup as a standard GradleRIO robot project. This means you build with `./gradlew build`, and can install the native toolchain with `./gradlew installRoboRIOToolchain`. If you open this project in VS Code with the wpilib extension installed, you will get intellisense set up for both C++ and Java.

By default, this template builds against the latest WPILib development build. To build against the last WPILib tagged release, build with `./gradlew build -PreleaseMode`.

## Updating
<ul>
<li>Copy the library code under src to FRCLibraryExample - dev branch to ensure the latest code is being used</li>
<li>Make code changes in FRCLibraryExample - dev branch, test and simulate</li>
<li>Bring changes back over to this code base</li>
<li>Update the version in frc5010Lib.json (2 places) and publish.gradle (pubVersion)</li>
<li>Build the code</li>
<li>If needed, in a terminal, run > .\gradlew.bat :spotlessApply</li>
<li>Run > .\update.bat to create the new version files</li>
<li>Commit and push</li> 
</ul>
