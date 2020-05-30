FROM tomcat:8.0.51-jre8-alpine
EXPOSE 8080
#RUN rm -rf /usr/local/tomcat/webapps/*
#COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
#CMD ["catalina.sh","run"]
ADD target/*.war todo-web-application-h2.war
ENTRYPOINT ["sh","-c", "java -jar todo-web-application-h2.war"]
