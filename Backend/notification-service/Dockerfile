# ---------- Build Stage ----------
FROM gradle:9.5.1-jdk21 AS builder

WORKDIR /app

COPY . .

RUN gradle clean bootJar -x test --no-daemon

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","app.jar"]