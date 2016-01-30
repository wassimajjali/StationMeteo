#!/bin/bash
java -cp .:./*:./ext/* -Dip=157.26.110.230 -Djava.library.path=./ext/Mac/ -Xmx100m -Xms80m ch.hearc.meteo.imp.use.remote.pclocal.UsePCLocal

