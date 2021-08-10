INSERT INTO `privilegio` VALUES(1,'ROLE_ADMIN','Permite Obtener Acceso a todos los recursos del sistema.');
INSERT INTO `privilegio` VALUES(2,'ROLE_SUPERHEROE_CONSULTARTODOS','Permite obtener acceso a la lista con información de todos los Súper Héroes encontrados.');
INSERT INTO `privilegio` VALUES(3,'ROLE_SUPERHEROE_CONSULTARPORID','Permite obtener acceso a la información de cada Súper Héroe indicado por medio de una identificacion.');
INSERT INTO `privilegio` VALUES(4,'ROLE_SUPERHEROE_CONSULTARNOMBRECONTENGA','Permite obtener acceso a la lista con información de todos los Súper Héroes que contengan en su nombre el valor indicado.');
INSERT INTO `privilegio` VALUES(5,'ROLE_SUPERHEROE_MODIFICAR','Permite obtener acceso a actualizar la información de un Súper Héroe por medio de la identificación indicada');
INSERT INTO `privilegio` VALUES(6,'ROLE_SUPERHEROE_ELIMINAR','Permite obtener acceso a eliminar la información de un Súper Héroe por medio de la identificación indicada');

-- -----------------------------------------------------
-- admin usuario con acceso a todos los recursos del sistema
-- uct usuario con acceso al recurso SUPERHEROE_CONSULTARTODOS
-- uci usuario con acceso al recurso SUPERHEROE_CONSULTARPORID
-- ucn usuario con acceso al recurso SUPERHEROE_CONSULTARNOMBRECONTENGA
-- usc usuario con acceso solo a los recursos de consulta(SUPERHEROE_CONSULTARTODOS,SUPERHEROE_CONSULTARPORID,SUPERHEROE_CONSULTARNOMBRECONTENGA)
-- ucm usuario con acceso a los recursos de consulta y de modificación
-- ucme usuario con acceso a los recursos de consulta, modificación y eliminación
-- -----------------------------------------------------
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(1,'admin','Administrador','Administrador','d1492f52432ed97d3b696bc8a229ecf53e9929813bce704aa5ae40b5fd950acf432b513c135d81b4809c853d6e5dcfd0707790683f890c4e2ff9b573a7b6ccef');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(2,'uct','uct','uct','2061b7cb89b47b5cb5d5438fe1ef199bbdf9bf16f58b7e9e2415c563ef997b4f3939321aecb29bd8f68646e6b4403259b78309d0651e5d7c1fe6bc3365bb295d');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(3,'uci','uci','uci','1085591ec5950d5312ecf1c5d06af2fae94339a64286e1b20207a4ddade6402d677afaf09cf560c710411b1b80308b767ac58a87240cc994e64e0ba89150591f');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(4,'ucn','ucn','ucn','aee2c7f16a49a8dd738beddc0df418f2e5162ec2f3c43d616af8bd217b24f2f571c4f1c2721bd9df1181c0b0acca69f0052e41009f24f1f5ad7c7f995d775c12');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(5,'usc','usc','usc','0254027b692d46c8e05d7d1e91eb72e7770c49faf4e1e80de7f71cfa9f7ef3f9489aae9780e0c7069693b413fa76d0dd2de74e0b5f82436992486049d47ab96d');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(6,'ucm','ucm','ucm','9de97c1ef4d1640e7d88efd204a33d50e5aebe8a83aaec66d5d6fe01826b7f441585f8ddf72db4e2cc57cef52ebd3e51a5695d18aa3cd2c27e536e5b057c06eb');
INSERT INTO `usuario` (idusuario,usuario,nombre,apellido,contrasenia) VALUES(7,'ucme','ucme','ucme','ca57623866c4b50fe1f6813a063b251cc162668cae999270e2372dcf9666d762bf1d572c18945f7e56151bc6007184e4c38318f2d4e30966aad8785b81907ad4');

-- admin
INSERT INTO `usuario_privilegio` VALUES(1,1);

-- Solo SUPERHEROE_CONSULTARTODOS
INSERT INTO `usuario_privilegio` VALUES(2,2);

-- Solo SUPERHEROE_CONSULTARPORID
INSERT INTO `usuario_privilegio` VALUES(3,3);

-- Solo SUPERHEROE_CONSULTARPORID
INSERT INTO `usuario_privilegio` VALUES(4,4);

-- Solo Consultas
INSERT INTO `usuario_privilegio` VALUES(5,2);
INSERT INTO `usuario_privilegio` VALUES(5,3);
INSERT INTO `usuario_privilegio` VALUES(5,4);

--Solo Modificacion
INSERT INTO `usuario_privilegio` VALUES(6,2);
INSERT INTO `usuario_privilegio` VALUES(6,3);
INSERT INTO `usuario_privilegio` VALUES(6,4);
INSERT INTO `usuario_privilegio` VALUES(6,5);

--Solo Eliminar
INSERT INTO `usuario_privilegio` VALUES(7,2);
INSERT INTO `usuario_privilegio` VALUES(7,3);
INSERT INTO `usuario_privilegio` VALUES(7,4);
INSERT INTO `usuario_privilegio` VALUES(7,5);
INSERT INTO `usuario_privilegio` VALUES(7,6);

INSERT INTO `super_heroe` VALUES(NULL,'Bat Man','Bruce Wayne','Murcielago','Negro','Ciudad Gotica','SuperIntelecto','El Guason');
INSERT INTO `super_heroe` VALUES(NULL,'Super Man','Clark Kent','S','Azul','Metrópolis','Super Fuerza','Lex Luthor');
INSERT INTO `super_heroe` VALUES(NULL,'Spider Man','Peter Parker','Araña','Rojo','Ciudad De Nueva York','Telaraña','Duende Verde');
INSERT INTO `super_heroe` VALUES(NULL,'Iron Man','Tony Stark','Robot','Rojo','Mansión Stark','Volar','Ultrón');
INSERT INTO `super_heroe` VALUES(NULL,'Capitan America','Steven Rogers','Estrella','Azul','Ciudad De Nueva York','SuperSoldado','Hydra');
INSERT INTO `super_heroe` VALUES(NULL,'Thor','Dios del Trueno','Martillo','Dorado','Asgard','CombateArmado','Loki');
INSERT INTO `super_heroe` VALUES(NULL,'Hulk','Bruce Banner','Puño','Verde','Sakaar','SuperFuerza','Graviton');