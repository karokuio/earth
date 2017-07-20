FROM openjdk:8-alpine

COPY wtfc.sh .
COPY earth.jar .

ENTRYPOINT ["sleep 40","&&", "java", "-jar", "earth.jar"]
