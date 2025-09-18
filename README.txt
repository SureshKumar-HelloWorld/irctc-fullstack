IRCTC Fullstack (Spring Boot + React)

Backend:
  cd backend
  ./mvnw spring-boot:run   (or: mvn spring-boot:run)

Frontend:
  cd frontend
  npm install
  npm run dev

Default CORS allowed origins: http://localhost:5173
Set JWT secret in backend src/main/resources/application.yml
Register admin via POST /api/auth/register-admin then login, use token in frontend automatically via login page.
