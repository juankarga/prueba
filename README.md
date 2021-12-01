# EJERCICIO COUPON

El ejercicio consiste en exponer un API que dado una lista de item_id y el monto total del cupon pueda darle la lista de items que maximice el total gastado sin excederlo. 

Para implementar este ejercicio se usaron las siguientes herramientas o frameworks:

**Codigo fuente:** Java usando el framework spring (spring boot, Spring data redis).

**Pruebas unitarias:** JUnit 5 y Mockito.

**Administrador de dependencias:** Maven.

**Nube:** AWS donde se hace uno de los servicios, EC2, ECR, ECS, API gateway, ElasticCache.

**Reporsitorio:** Git HUB.

**CI/CD:** implementado en git actions.

**Metricas de calidad:** SonarCLoud (https://sonarcloud.io/summary/overall?id=juankarga_prueba).

**Contenedor:** Docker.

Coupon.drawio.png![imagen](https://user-images.githubusercontent.com/95094164/144253255-a82c5edb-90ab-4b05-b33a-c7c4bdccf955.png)

## Para ejecutar el ejercicio de forma local es necesario seguir los siguientes paso:

1. Tener installado el jdk 11 (https://adoptopenjdk.net/) y maven (https://maven.apache.org/download.cgi).

2. tener instalado Redis localmente (https://redis.io/topics/quickstart).

3. Descargar el repositorio usando el siguiente comando: `git clone https://github.com/juankarga/prueba.git`

4. Ingresar al proyecto usando: `cd prueba`

5. Ejecutar el comando: `mvn clean install`

6. Ejecutar el comando: `mvn spring-boot:run -Drun.jvmArguments="-Xmx512m"`

7. Ejecutar el CURL: `curl --location --request POST 'localhost:80/coupon' \
--header 'Content-Type: application/json' \
--data-raw '{ 
"item_ids": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5", "MCO578230086","MCO610909910","MCO551231884","MCO810716196","MCO810690369","MCO610559808"], 
"amount": 90000
} '`


## Para consumir el API desplegado en la infraestructura de AWS debemos ejecutar el siguiente CURL: 

`curl --location --request POST 'https://uo2vzpzvd4.execute-api.us-east-1.amazonaws.com/prod/coupon' \
--header 'Content-Type: application/json' \
--data-raw '{ 
"item_ids": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5", "MCO578230086","MCO610909910","MCO551231884","MCO810716196","MCO810690369","MCO610559808"], 
"amount": 100000
} '`
