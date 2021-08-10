/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.config;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kevin Velásquez
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguracion {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    
	private ApiInfo ApiInfo() {
		return new ApiInfo("Super Heroes Rest Api",
				"Api Spring Boot que permita hacer mantenimiento de los súper heroes",
				"1.1",
				"Terminos del Servicio",
				new Contact("Kevin Velásquez", 
							"https://github.com/knvelasquez", 
							"knvelasquez@outlook.com"),
							"Lisencia de la Api",
							"API lisencia URL", Collections.emptyList());
	}
	
	@Bean
	public Docket api() {
		return (new Docket(DocumentationType.SWAGGER_2)
				.select()
                .apis(RequestHandlerSelectors.basePackage("es.w2m.SuperHeroeMantenimiento.ApiRestController"))                
                .build()	
					.apiInfo(ApiInfo.DEFAULT)
					.apiInfo(ApiInfo())
					.pathMapping("/")		
					.forCodeGeneration(true)
					.genericModelSubstitutes(ResponseEntity.class)
					.ignoredParameterTypes(Pageable.class)
					.ignoredParameterTypes(java.sql.Date.class)
					.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
					.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
					.directModelSubstitute(java.time.LocalDateTime.class, Date.class)
					.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()))
					.useDefaultResponseMessages(false));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}
