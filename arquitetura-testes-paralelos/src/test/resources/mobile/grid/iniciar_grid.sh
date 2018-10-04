#!/bin/bash

function new_tab() {
  TAB_NAME=$1
  COMMAND=$2
  osascript \
    -e "tell application \"Terminal\"" \
    -e "tell application \"System Events\" to keystroke \"t\" using {command down}" \
    -e "do script \"printf '\\\e]1;$TAB_NAME\\\a'; $COMMAND\" in front window" \
    -e "end tell" > /dev/null
}

SELENIUM_SERVER_DIR="/Users/eliasnogueira/Downloads"
SELENIUM_SERVER_FILE="selenium-server-standalone-3.14.0.jar"


new_tab "Grid" "java -jar $SELENIUM_SERVER_DIR/$SELENIUM_SERVER_FILE -role hub -hubConfig ../json/grid.json"

new_tab "Node 2 - Android" "appium --nodeconfig ../json//android-1.json -p 4723 -bp 5526 -U emulator-5553"
new_tab "Node 1 - Android" "appium --nodeconfig ../json//android-2.json -p 4724 -bp 5524 -U emulator-5554"