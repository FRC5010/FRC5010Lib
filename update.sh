#!/bin/bash
./gradlew :spotlessApply
dos2unix build.gradle
dos2unix publish.gradle
dos2unix config.gradle
dos2unix README.md
./gradlew publish
cp -rf build/repos/releases/org/frc5010 frc5010lib/repos/
