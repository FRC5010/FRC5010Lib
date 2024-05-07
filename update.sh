#!/bin/bash
./gradlew :spotlessApply
./gradlew publish
cp -rf build/repos/releases/frc5010lib frc5010lib/repos/
