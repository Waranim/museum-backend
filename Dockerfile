FROM gradle:jdk21-alpine AS BUILD
WORKDIR /app/
COPY . .
RUN gradle assemble

FROM eclipse-temurin:21-jre-alpine
COPY --from=BUILD /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","/app.jar"]
