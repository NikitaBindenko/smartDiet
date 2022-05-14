#!/bin/bash

cd webapps/smartDiet/	#move to web application directory
javac -cp ~/tomcat9/apache-tomcat-9.0.54/lib/tomcat-jdbc.jar:src/ src/Model/*.java -d WEB-INF/classes	#recompiling all java files
javac -cp ~/tomcat9/apache-tomcat-9.0.54/lib/servlet-api.jar:src/ src/Controller/*.java -d WEB-INF/classes
cd ../..
cd bin		#move to Catalina directory
bash catalina.sh stop	#restarting Tomcat
bash catalina.sh start

#разложил в model и controller в каждом файле модели написал 
#package Model; 
#в каждом файле контроллера написал
#package Controller;

#import Model.*;

#команды для компиляции

#javac -cp ~/tomcat9/apache-tomcat-9.0.54/lib/servlet-api.jar:src/ src/Model/*.java -d WEB-INF/classes

#javac -cp ~/tomcat9/apache-tomcat-9.0.54/lib/servlet-api.jar:src/ src/Controller/*.java -d WEB-INF/classes
