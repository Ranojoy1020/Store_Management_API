This is the backend API for the store management system. This is developed using SpringBoot Framework.

# Add application.properties file inside src/main/resources/ of project with following properties:

  server.servlet.context-path=/api
  
  server.servlet.session.cookie.secure=true
  
  server.servlet.session.cookie.same-site=strict
  
  spring.jackson.time-zone=Asia/Kolkata
  
  spring.datasource.url= {Database URL}
  
  spring.datasource.username= {Database Username}
  
  spring.datasource.password= {Database Password}
  
  spring.datasource.driver-class-name= {Driver class name}    e.g.: org.postgresql.Driver
  
  spring.jpa.hibernate.ddl-auto=update
  
  spring.jpa.show-sql=true
  
  #spring.security.user.name=admin
  
  #spring.security.user.password=admin
  
  logging.level.org.springframework.security=DEBUG
  
  
  
  
  spring.mail.host=smtp.gmail.com
  
  spring.mail.port=587
  
  spring.mail.username= {Email ID}
  
  spring.mail.password= {Password}
  
  spring.mail.properties.mail.smtp.auth=true
  
  spring.mail.properties.mail.smtp.starttls.enable=true
  
  spring.mail.default-encoding=UTF-8
