# FRC5010Lib

This is the vendor template for FRC 5010 Tiger Dynasty common codebase.

## Json File

https://frc5010.github.io/FRC5010Lib/frc5010Lib.json

## Layout

The code is located under src/main/java/org/frc5010/common and is split into several sub folders
    arch - contains architectural files that undergird the library
    commands - common commands
    config - classes that read json config files to build a robot
    constants - folder for constants files
    drive - classes for defining drive systems and pose localization
    mechanisms - mechanisms can be used to organize commands and subsystems if needed
    motors - classes for handling motors and motor functions
    sensors - classes for handling many different types of sensors
    subsystems - classes for predefined subsystems
    telemetry - logging classes
    vision - additional classes for vision, some are deprecated

## Customizing
For Java, the library name will be the folder name the build is starts from.

For the maven artifact names, those are all in publish.gradle about 40 lines down.

## Building and editing
This uses gradle, and uses the same base setup as a standard GradleRIO robot project. This means you build with `./gradlew build`, and can install the native toolchain with `./gradlew installRoboRIOToolchain`. If you open this project in VS Code with the wpilib extension installed, you will get intellisense set up for both C++ and Java.

By default, this template builds against the latest WPILib development build. To build against the last WPILib tagged release, build with `./gradlew build -PreleaseMode`.

## Updating
Copy the library code under src to FRCLibraryExample - dev branch to ensure the latest code is being used
Make code changes in FRCLibraryExample - dev branch, test and simulate
Bring changes back over to this code base
Update the version in frc5010Lib.json (2 places) and publish.gradle (pubVersion)
Build the code
If needed, in a terminal, run > .\gradlew.bat :spotlessApply
Run > .\update.bat to create the new version files
Commit and push 
