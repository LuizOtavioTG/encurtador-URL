# 🔗 Encurtador de URL  

Este projeto é um encurtador de URL desenvolvido com **Java e Spring Boot**, permitindo transformar links longos em versões curtas e fáceis de compartilhar, além de uma versão **QRCode** do link. O projeto surgiu a partir de um desafio proposto pelo Backend <Brasil>. (Link do desafio: https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md)

## 🚀 Tecnologias utilizadas  
- **Spring Boot** (para Spring Web, Spring Data JPA)  
- **PostgreSQL** (para banco relacional SQL)  
- **FlywayDB** (para versionamento do banco)  
- **Lombok** (para reduzir boilerplate code)
- **ZXing Core** (para construção do QR Code) 
  

## 🎯 Funcionalidades  
✅ Encurtar URLs de forma rápida e segura  
✅ Redirecionamento automático para a URL original  
✅ Expiração e métricas de acessos  
✅ Versão **QR Code** do link  
❎ TO DO: API RESTful documentada com Swagger  

## 📦 Como executar  
1. Clone o repositório  
2. Configure o banco de dados no `application.properties`  
3. Execute o projeto com `mvn spring-boot:run` ou via IDE  
