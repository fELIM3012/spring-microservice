@echo off
setlocal

echo Starting Eureka Server...
start "Eureka Server" cmd /k "mvn spring-boot:run -pl eureka-server"

echo Waiting Eureka Server to start...
timeout /t 30 /nobreak > nul

echo Starting User Service...
start "User Service" cmd /k "mvn spring-boot:run -pl user-service"

echo Starting User Service ready...
timeout /t 30 /nobreak > nul

echo Starting Gateway Service...
start "Gateway Service" cmd /k "mvn spring-boot:run -pl gateway-service"

echo all service started!
pause
