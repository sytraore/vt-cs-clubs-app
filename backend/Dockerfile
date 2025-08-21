# Stage 1: Build the app with maven in the container
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /build

# Copy the Maven project file first. This is a Docker optimization.
# If pom.xml doesn't change, Docker can reuse the cached dependency layer.
COPY pom.xml .

# Download all the project dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code into the container
COPY src ./src

# Package the application into an executable .jar file
RUN mvn package -DskipTests


# Stage 2: Create the final, smaller production image
# We use a lean JRE image to run the application, which is much smaller
# than the full JDK/Maven image, making our final image more efficient.
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the .jar file that was built in the 'builder' stage
COPY --from=builder /build/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to allow traffic to the application
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]