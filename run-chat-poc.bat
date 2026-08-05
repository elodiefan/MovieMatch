@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0"

echo.
echo  ==========================================================
echo   MovieMatch one-on-one chat - proof of concept
echo  ==========================================================
echo.
echo   Opens three windows (enzo, kiersten, lily) all talking to
echo   the same MongoDB Atlas cluster. Type in one, watch it turn
echo   up in another. No Spring Boot, no Docker, no WebSocket.
echo.

rem ------------------------------------------------------------
rem  mongo.properties has to be here, it is how we reach Atlas
rem ------------------------------------------------------------
if not exist "mongo.properties" (
    echo   ERROR: mongo.properties not found in
    echo          %CD%
    echo.
    echo   It is git-ignored on purpose, so copy in the same one
    echo   you already use to run the main app.
    echo.
    pause
    exit /b 1
)

rem ------------------------------------------------------------
rem  Find Maven. PATH first, then whatever IntelliJ ships with.
rem ------------------------------------------------------------
set "MVN="
for /f "delims=" %%P in ('where mvn 2^>nul') do if not defined MVN set "MVN=%%P"

if not defined MVN call :findunder "C:\Program Files\JetBrains"
if not defined MVN call :findunder "%LOCALAPPDATA%\Programs"
if not defined MVN call :findunder "C:\Program Files (x86)\JetBrains"

if not defined MVN (
    echo   ERROR: could not find Maven.
    echo.
    echo   Easiest fix: open the project in IntelliJ and let it
    echo   import once, then run this again. Or put mvn on PATH.
    echo.
    pause
    exit /b 1
)
echo   Maven .... !MVN!

rem ------------------------------------------------------------
rem  Find Java. JAVA_HOME, then PATH, then IntelliJ's bundled JBR.
rem ------------------------------------------------------------
set "JAVAW="
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javaw.exe" set "JAVAW=%JAVA_HOME%\bin\javaw.exe"
if not defined JAVAW for /f "delims=" %%P in ('where javaw 2^>nul') do if not defined JAVAW set "JAVAW=%%P"
if not defined JAVAW call :findjbr "C:\Program Files\JetBrains"
if not defined JAVAW call :findjbr "%LOCALAPPDATA%\Programs"

if not defined JAVAW (
    echo   ERROR: could not find Java.
    echo   Set JAVA_HOME, or install a JDK, or run from IntelliJ.
    echo.
    pause
    exit /b 1
)
echo   Java ..... !JAVAW!
echo.

rem ------------------------------------------------------------
rem  Build, then work out the dependency classpath
rem ------------------------------------------------------------
echo   Compiling, this takes a few seconds...
call "!MVN!" -B -q clean compile
if errorlevel 1 (
    echo.
    echo   ERROR: compile failed. Scroll up for the reason.
    echo.
    pause
    exit /b 1
)

rem  Write the classpath under target\ so it never gets committed.
call "!MVN!" -B -q dependency:build-classpath -Dmdep.outputFile=target\cp.txt
if errorlevel 1 (
    echo.
    echo   ERROR: could not resolve dependencies. Are you online?
    echo.
    pause
    exit /b 1
)

set /p DEPS=<target\cp.txt
set "CP=target\classes;!DEPS!"
echo   Built OK.
echo.

rem ------------------------------------------------------------
rem  Launch. One name given = one window, otherwise all three.
rem ------------------------------------------------------------
if /i "%~1"=="clean" (
    set "JAVA=!JAVAW:javaw.exe=java.exe!"
    echo   Deleting every saved message...
    "!JAVA!" -cp "!CP!" poc.chat.PocChat clean
    echo.
    pause
    exit /b 0
)

if not "%~1"=="" (
    echo   Opening one window as %~1 ...
    start "" "!JAVAW!" -cp "!CP!" poc.chat.PocChat %~1
) else (
    echo   Opening three windows: enzo, kiersten, lily ...
    start "" "!JAVAW!" -cp "!CP!" poc.chat.PocChat enzo
    start "" "!JAVAW!" -cp "!CP!" poc.chat.PocChat kiersten
    start "" "!JAVAW!" -cp "!CP!" poc.chat.PocChat lily
)

echo.
echo  ----------------------------------------------------------
echo   WHAT TO TRY
echo.
echo   The enzo and kiersten windows open on the same saved
echo   conversation, so you should see identical messages in
echo   both. That already proves they share one store.
echo.
echo   Type in one and it appears in the other within about two
echo   seconds. That round trip goes through Atlas - your text
echo   is written to the database and read back by the other
echo   window, which is exactly how it would work across two
echo   different laptops.
echo.
echo   For the third person, set kiersten's dropdown to lily,
echo   send something, then switch lily's dropdown to kiersten.
echo.
echo   Flip a dropdown back and forth. Each conversation loads
echo   on its own and nothing leaks between them.
echo.
echo   To wipe the saved messages:  run-chat-poc.bat clean
echo  ----------------------------------------------------------
echo.
echo   Windows are open. You can close this box.
echo.
pause
exit /b 0

rem ============================================================
rem  Helpers: hunt through a JetBrains install folder
rem ============================================================
:findunder
if not exist "%~1" exit /b 0
for /d %%D in ("%~1\*IDEA*") do (
    if not defined MVN if exist "%%D\plugins\maven-plugin\lib\maven3\bin\mvn.cmd" set "MVN=%%D\plugins\maven-plugin\lib\maven3\bin\mvn.cmd"
    if not defined MVN if exist "%%D\plugins\maven\lib\maven3\bin\mvn.cmd" set "MVN=%%D\plugins\maven\lib\maven3\bin\mvn.cmd"
)
exit /b 0

:findjbr
if not exist "%~1" exit /b 0
for /d %%D in ("%~1\*IDEA*") do (
    if not defined JAVAW if exist "%%D\jbr\bin\javaw.exe" set "JAVAW=%%D\jbr\bin\javaw.exe"
)
exit /b 0
