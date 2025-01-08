@echo off
echo Formatting
call gradlew.bat :spotlessApply
echo Publish
call gradlew.bat publish
@REM echo Copy
@REM Xcopy /E /y .\\build\\repos\\releases\\org\\frc5010 .\\frc5010lib\\repos\\org\\frc5010
