@echo off

java -cp .;./*;./ext/* -Dip=157.26.110.230 -Xmx100m -Xms80m ch.hearc.meteo.imp.use.remote.pccentral.UsePCCentral

pause