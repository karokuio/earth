FROM openjdk:8-alpine

COPY wtfc.sh .
COPY earth.jar .

ENTRYPOINT ["./wtfc.sh", "-T", "40", "nc -z cassandra 9042", "&&", "java", "-jar", "earth.jar"]
