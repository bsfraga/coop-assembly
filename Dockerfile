# Usar a imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim as build

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar o build.gradle e gradlew para o container
COPY build.gradle gradlew /app/
COPY gradle /app/gradle

# Baixar as dependências
RUN ./gradlew dependencies

# Copiar o código fonte para o container
COPY src /app/src

# Construir a aplicação
RUN ./gradlew build

# Usar a imagem base do OpenJDK 17 para executar a aplicação
FROM openjdk:17-jdk-slim

# Copiar o JAR construído para o container
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Definir o comando para executar a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
