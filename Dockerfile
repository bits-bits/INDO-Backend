# ---- Build Stage ----
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Copy everything
COPY . .

# Build the jar
RUN gradle clean bootJar --no-daemon

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port (Spring Boot default)
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]