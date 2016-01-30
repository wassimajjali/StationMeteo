#!/bin/bash
java -cp .:./*:./ext/* -Dip=192.168.153.128 -Xmx100m -Xms80m ch.hearc.meteo.imp.use.remote.pccentral.UsePCCentral

