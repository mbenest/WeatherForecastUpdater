#!/bin/bash

export JAVA_HOME="$1"

jarfilename="WeatherForecastUpdater.jar"
binDirname="bin"

find src -type f -name "*.java" > sources.txt

echo "Manifest-Version: 1.0" > MANIFEST.MF
echo "Main-Class: eu.akka.weatherforecast.Launcher" >> MANIFEST.MF
echo "Class-Path: ." >> MANIFEST.MF

[ -e $jarfilename ] && rm $jarfilename
[ -d $binDirname ] && rm -rf $binDirname

mkdir $binDirname

$JAVA_HOME/bin/javac -d $binDirname @sources.txt
$JAVA_HOME/bin/jar cvfm $jarfilename MANIFEST.MF -C $binDirname/ .
$JAVA_HOME/bin/jar uf $jarfilename -C resources/ .

rm MANIFEST.MF
rm sources.txt

$JAVA_HOME/bin/java -jar $jarfilename
