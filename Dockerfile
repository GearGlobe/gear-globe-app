FROM node:20-alpine AS frontend-build
WORKDIR /app
COPY frontend/ ./
RUN npm install && npm run build

FROM maven:3-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app
COPY --from=frontend-build /app/build ./frontend/build
COPY backend/pom.xml ./backend/
COPY backend/src ./backend/src/
WORKDIR /app/backend
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
ARG JAR_FILE=/app/backend/target/*.jar
COPY --from=backend-build ${JAR_FILE} app.jar
CMD ["java", "-jar", "app.jar"]