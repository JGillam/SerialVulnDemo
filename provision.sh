#!/bin/bash

echo "Provisioning demo host..."

sudo apt-get update
sudo apt-get -y upgrade

echo "Installing apache and php"
sudo apt-get -y install apache2 php php-pear