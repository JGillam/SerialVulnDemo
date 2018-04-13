#!/bin/bash

echo "Provisioning demo host..."

sudo apt-get update
sudo apt-get -y upgrade

echo "Installing apache and php"
sudo apt-get -y install apache2 php php-pear

echo "Installing jdk and tomcat"
sudo apt-get -y install tomcat8 default-jdk

sudo mkdir -p /opt/svulndemo/java/webapp/WEB-INF/classes
cd /opt/svulndemo/java/src

echo "  - Compiling Java deserialization demo."
udo javac -cp /usr/share/tomcat8/lib/servlet-api.jar:. -d ../webapp/WEB-INF/classes demo/serialization/*.java

echo "  - Deploying Java deserialization demo."
cd /opt/svulndemo/java/webapp
sudo jar cvf /var/lib/tomcat8/webapps/java.war ./