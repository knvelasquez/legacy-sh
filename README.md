# legacy sh 
### API´s en Spring Boot 
APIS Restfull que permiten el manteniemiento de sh y está desarrollada en SpringBoot, Spring Security, Java 11, docker,
La informacion se almacena en una base de datos en memoria de tipo H2 que luego puede ser escalable hacia una base de datos productiva on premis
- - -
# Tabla de Contenido
En esta unidad se cubrirán los siguientes temas:
* Instalación y uso
  * Clonar el repositorio
    ```sh
    git clone https://github.com/knvelasquez/superheroes.git
    ```
  * Navegar al directorio raíz del projecto
    ```sh
    cd superheroes
    ```
  * Compilar el projecto con maven 
    ```sh
    mvn clean install
    ``` 
  * Iniciar el contenedor con la imagen de Docker
    ```sh
    docker compose up
    ```
- - -
# Documentación de las API´s
  * Se usa la interfaz de  swagger-ui para la documentación
  * Enlace de acceso: [swagger-ui](http://localhost:8585/swagger-ui.html)
  * Se usan sustantivos autoexplicativos en lugar de verbos para describir mejor el recurso
    * Ejemplo sustantivo
      * usuario
    * Ejemplo verbo 
       * autenticar_usuario
  * El uso del sustantivo nos permite comprender mucho mejor el recurso. Las acciones contra estos recursos serán mediante los métodos HTTP (GET,POST,PUT,DELETE)
  * Los recursos disponibles son los siguientes:
    * POST usuario permite iniciar sesión por medio del usuario y contraseña dentro del cuerpo de la solicitud.
     ```json
     {
	    usuario:usuario,
	    contrasenia:contrasenia
     }
      ```
    * GET superheroe recupera una lista con información de todos los súper heroes encontrados
    * GET superheroe/:id recupera la información de un super heroe mediante el identificación indicada
    * GET superheroe/nombre/:nombre recupera una lista con información de los Súper Héroes que en su nombre contengan el valor indicado
    * PUT superheroe modifica la informacion de un Súper Héroe
    * DELETE superheroe elimina la información de un Súper Héroe

	
De esta manera se puede comprender mucho mejor los recursos descritos en la url, se mantiene la coherencia y permite utilizar de manera ordenada, elegante y simple desde cualquier aplicacion web.

Ya que una URL base larga y difícil de leer no solo es mala de ver, sino que también puede ser propensa a errores al intentar recodificarla.


Nota: Se debe enviar dentro en el header el token recibido durante el inicio de sesión para poder avanzar
  para cada recurso expuesto exceptuando el usuario se debe pasar como parametro dentro del header el token generado durante inicio de sesió
  este token tendrá una vigencia de 10min y los privilegios de acceso para poder acceder a las APIS

- - -
# Seguridad de las API´s Spring Security 
  * Desde la versión 3 de Spring Security se encuentra disponible la anotación @PreAuthorize la cual es más nueva y mucho más flexible @Secured,  ejemplo sintaxis: hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')
  * Cada API se encuentra protegida con esta anotación y no se puede acceder sin antes iniciar sesión
  * @PreAuthorize realiza un filtro de autorización que valida la existencia de un JSON Web Tokens dentro del encabezado de la solicitud
   ```json
   authorization bearer ${SON Web Token}
   ```
  * Luego verifica dentro del payload que exista el privilegio que le permita avanzar y de no tener el suficiente privilegio devolverá un error de acceso 401
   ```json
   {
    "iss": "World 2 Meet, S.L.U.",
    "sub": "uct",
    "nombre": "uct",
    "apellido": "uct",
    "authorities": [
      "ROLE_SUPERHEROE_CONSULTARTODOS"
    ],
   "iat": 1628610137,
   "exp": 1628610737
  }
   ```
  * Los atributos dentro del payload son los siguientes:
    * iss: Representa quién creó y firmó este token
	* sub: Representa a quién se refiere este token 
	* nombre: Es el nombre del que ha generado este token
	* apellido: Es el apellido del que ha generado este token	
    * authorities: Representa una lista de privilegios otorgados a un usuario para acceder a las API´s 
	* los authorities son entregado al contexto para entrar al ciclo de seguridad de spring security
      * Lista de Privilegios
	     * ROLE_ADMIN Permite Obtener Acceso a todos los recursos del sistema
		 * ROLE_SUPERHEROE_CONSULTARTODOS Permite obtener acceso a la información de todos los Súper Héroes	 
		 * ROLE_SUPERHEROE_MODIFICAR Permite actualizar la información de un Súper Héroe
		 * ROLE_SUPERHEROE_ELIMINAR Permite eliminar la información de un Súper Héroe
	* iat: Representa la fecha en milisegundos del momento que se creó el token	
	* exp: Representa la fecha en milisegundos del momento en el cual expirará el token
	  * Nota el tiempo de vigencia que posee un Json Web Token es el tiempo estandar de 6.000 milisegundos = 10min
   * La clave secreta de compresión del cifrado se encuentra establecida con una longitud de 26 carácteres
   * El Nombre del algoritmo establecido para la firma de JWT es SHA-512  
   * La contraseña es cifrada con el algoritmo de criptografía PBKDF2 que utiliza una clave secreta de 20 carácteres no configurable más un valor aleatorio generado por el sistema el cual es agregado junto con el valor cifrado para evitar duplicidad incluso en el caso que dos usuarios usen la misma contraseña.
   * Se usa para validar al usuario en su proximo inicio de sesion

- - -

# Base de datos 
  * La base de datos implementada para este ejercicio es H2 en memoria
  * Enlace para ingresar a la consola [h2-console](http://localhost:8585/h2-console/)
    * Parámetros de Configuración:
      * Driver Class org.h2.Driver
      * Url Jdbc: jdbc:h2:mem:testdb
      * Nombre de Usuario: sa
      * Contraseña: 
  * Base de Dato por ambientes:
    * maestrod01 Desarrollo
    * maestroh01 Homolacion
    * maestro01=> Producción
  * Tablas
    * usuario Tiene información básica de un usuario como
     (nombre,apellido,usuario y contraseña)
    * privilegios Tiene la información de los privilegios de acceso y la descripción del alcance permitido
    * superheroe Tiene información de los Súper Heroes como (nombre,identidad secreta,logo,color,lugar de residencia,super poder y archi enemigo)

- - -

# Anotación Personalizada 
### Para medir el tiempo de ejecución de una petición
  * Se implementó la anotación @TotalTiempoEjecucion la cual mide el tiempo de ejecución de una petición y muestra por consola el total en milisegundos
  * Se puede establecer la anotación en cualquiera de las API´s actualmente se encuentra establecida en
    * GET superheroes
    * GET superheroes/nombre:nombre
    ![](https://i.imgur.com/OoLTTGk.jpg)

- - -

# Gestión centralizada
## Para el manejo global de excepciones
  * Ha sido creada una clase ExceptionGlobalHandler la cual se encarga de manejar todas las execpciones generadas de manera automática
  * Solo hay que registrar el nuevo tipo de execepción que se desea capturar y el mensaje de respuesta para que quede configurado
 ![](https://i.imgur.com/PUiWmq0.jpg)
 
- - -
# Tests Unitarios y de Integración
  * Para ejecutar los test unitarios se debe ejecutar el siguiente comando:
  ```sh
  mvn clean test -P dev
  ```
	
  * Para ejecutar los test de integración e debe ejecutar el siguiente comando:
  ```sh
  mvn clean integration-test
  ```
- - -
arquitectura de la aplicacion

- - -

# Usar desde POSTMAN
## Importar la colección es.w2m.postman_collection.json
   * Variables importadas disponibles 
      * {{host}} Representa el ambiente sobre el que se encuentra implementadas las API´s
      * {{user}},{{pass}} Representan el usuario y contraseña en la solicitud y se establecen de forma automática
         ```json
         {
           "usuario": "{{user}}",
           "contrasenia": "{{pass}}"
         }
         ```
     * {{adminPass}},{{uctPass}},{{uciPass}},{{ucnPass}},{{uscPass}} Representan las diferentes contraseñas de los diferentes usuarios, están mockeadas y son seleccionadas automáticamente por postman luego de seleccionar un usuario
     * {{jwtToken}} Representa el Json Web Token generado durante el inicio de sesión y se establece en el header de todas las API´s de manera automática
         ```json
         Bearer {{jwtToken}}
         ```
     * Scripts de inicialización
        * Se encargan de establecer las varibles antes de realizar una solicitud y de establecer luego la variable del jwt token obtenido al finalizar una petición  
  * Procedimiento:    
     * Seleccionar el botón de enviroment
     * Seleccionar {{user}} y dar click en el botón de edicición, luego indicar el usuario que desea autenticar
       * admin usuario administrador con full acceso
       * usc usuario con privilegios de solo consulta
       * ucm usuario con privilegios de consulta y modificacion
       * ucme usuario con privilegios de consulta modificacion y eliminacion
     * Click en enviar
     * Si la autenticación es éxitosa obtendrá codigo de estatus 200 y el  json web token además se establecerá la variable {{jwToken}} que es heredada en el encabezado de cada APIS, por lo que podrá utilizar la API´s de Súper Héroe.
  
# Ejemplo de uso Swagger vs POSTMAN
![](https://i.imgur.com/lDBQRl5.gif)
![](https://i.imgur.com/RNwCgJw.gif)

- - - - -
© 2021 [knvelasquez](https://linkedin.com/in/knvelasquez)
