FROM gradle:8.0.2-jdk19 AS BUILDER
WORKDIR /app

#COPY *.gradle .
COPY server server
COPY app app

FROM openjdk:20-jdk-slim
WORKDIR /app

COPY --from=BUILDER /app/server/build/libs/server-all.jar server-all.jar
EXPOSE 8000
CMD ["java", "-jar", "server-all.jar"]