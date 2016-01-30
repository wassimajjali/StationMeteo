@echo off

set bits=32
if %PROCESSOR_ARCHITECTURE%==AMD64 set bits=64

java -cp .;./*;./ext/* -Dip=157.26.105.237 -Djava.library.path=./ext/Windows/x%bits%/ -Xmx100m -Xms80m ch.hearc.meteo.imp.use.remote.pclocal.UsePCLocal

pause