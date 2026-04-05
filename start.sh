#!/bin/bash
set -e

Xvfb :99 -screen 0 1280x720x24 &
sleep 1
x11vnc -display :99 -rfbauth /etc/x11vnc.pass -forever -noxdamage &
sleep 1

exec java \
  --module-path /opt/javafx-sdk-21.0.1/lib \
  --add-modules javafx.controls,javafx.fxml \
  -cp app.jar \
  shoppingcart.Main
