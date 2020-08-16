echo Installing ,please wait...
set p=%cd%
echo %p%
%p%\tools\adb install -r %p%\app\*.apk
pause