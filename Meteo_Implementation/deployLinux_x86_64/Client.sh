#!/bin/bash
java -cp .:./*:./ext/* -Dip=192.168.153.128 -Djava.library.path=./ext/Linux/x86_64-unknown-linux-gnu/ -Xmx100m -Xms80m ch.hearc.meteo.imp.use.remote.pclocal.UsePCLocal
