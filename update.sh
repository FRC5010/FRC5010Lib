#!/bin/bash
./gradlew :spotlessApply
./gradlew publish
cp -rf build/repos/releases/org/frc5010 frc5010lib/repos/
