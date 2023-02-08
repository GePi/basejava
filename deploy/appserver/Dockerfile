FROM tomcat:9.0.71-jre17

#ADD tomcat-users.xml $CATALINA_HOME/conf/

#RUN mv /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps/
WORKDIR $CATALINA_HOME/webapps/
#COPY context.xml ./manager/META-INF/

COPY resumes.war .

ADD postgresql-42.5.1.jar $CATALINA_HOME/lib/

EXPOSE 8080
CMD [ "catalina.sh", "run" ]