FROM gradle:jdk21-alpine AS BUILD
WORKDIR /app/
COPY . .
RUN gradle assemble

FROM eclipse-temurin:21-jre-alpine
COPY --from=BUILD /app/build/libs/*.jar app.jar
COPY src/main/resources/db/changelog /app/db/changelog
EXPOSE 8080
CMD ["java","-jar","/app.jar"]
