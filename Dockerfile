## Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

## Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    xvfb \
    x11vnc \
    libgtk-3-0 \
    libglu1-mesa \
    && rm -rf /var/lib/apt/lists/*

# Install JavaFX 21 SDK
RUN wget -q https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_linux-x64_bin-sdk.zip \
    && unzip -q openjfx-21.0.1_linux-x64_bin-sdk.zip -d /opt \
    && rm openjfx-21.0.1_linux-x64_bin-sdk.zip

COPY --from=builder /build/target/ShoppingCart.jar app.jar
COPY start.sh /start.sh
RUN chmod +x /start.sh

# Set VNC password to "secret"
RUN x11vnc -storepasswd secret /etc/x11vnc.pass

EXPOSE 5900

ENV DISPLAY=:99

CMD ["/start.sh"]
