#!/bin/bash

export JAVA_HOME="$1"

jarFilename="WeatherForecastUpdater.jar"
binDirname="bin"

find src -type f -name "*.java" > sources.txt

echo "Manifest-Version: 1.0" > MANIFEST.MF
echo "Main-Class: eu.akka.weatherforecast.Launcher" >> MANIFEST.MF
echo "Class-Path: ." >> MANIFEST.MF

[ -e $jarFilename ] && rm $jarFilename
[ -d $binDirname ] && rm -rf $binDirname

mkdir $binDirname

$JAVA_HOME/bin/javac -d $binDirname @sources.txt
$JAVA_HOME/bin/jar cvfm $jarFilename MANIFEST.MF -C $binDirname/ .
$JAVA_HOME/bin/jar uf $jarFilename -C resources/ .

rm MANIFEST.MF
rm sources.txt

$JAVA_HOME/bin/java -jar $jarFilename
