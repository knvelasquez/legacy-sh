package es.w2m.SuperHeroeMantenimiento;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.w2m.SuperHeroeMantenimiento.Seguridad.JWTFiltroAuthorization;
import es.w2m.SuperHeroeMantenimiento.exception.ExceptionGlobalHandler;

//@EnableTransactionManagement
//@EnableJpaRepositories("es.w2m.SuperHeroeMantenimiento.Repository")
@Configuration
@SpringBootApplication
public class SuperHeroeMantenimientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperHeroeMantenimientoApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		/**
		 * Método para configurar los filtros por medio de spring security
		 * 
		 * */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTFiltroAuthorization(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/w2m/usuario").permitAll()
					// .antMatchers(HttpMethod.GET, "/superheroe/**").permitAll()
					.anyRequest().authenticated();
			// Para capturar la exepcion de tipo acceso denegado y enviarlo a la clase global manejadora de exepciones 
			http.exceptionHandling().accessDeniedHandler((solicitud, respuesta, accesoDenegadoExcepcion) -> {				
				respuesta.setHeader("Content-Type", "application/json");
				respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				//Envía la Respuesta serializada
				respuesta.getOutputStream().write(
						new ObjectMapper().writeValueAsString(
								new ExceptionGlobalHandler().accessDeniedException(accesoDenegadoExcepcion).getBody()).getBytes());				
				return;
			});
		}
		
		/**
		 * Método para configurar los filtros por medio de spring security 
		 * 
		 * */
		@Override
		public void configure(WebSecurity web) throws Exception {
			// Permitir que se acceda a swagger sin autenticación 
			web.ignoring()
				.antMatchers("/v2/api-docs")
				.antMatchers("/swagger-resources/**")
				.antMatchers("/swagger-ui.html")
				.antMatchers("/configuration/**")
				.antMatchers("/webjars/**")
				.antMatchers("/public")
				// Base de datos H2 no segura (para fines de prueba, la consola H2
				// No debe establecerse para producción
				.and()
				.ignoring()
				.antMatchers("/h2-console/**/**");
		}
	}
}
