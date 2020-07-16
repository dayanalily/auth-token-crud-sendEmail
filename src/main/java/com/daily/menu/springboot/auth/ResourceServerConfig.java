package com.daily.menu.springboot.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/*
	 *ASIGNACION DE PERMISOS con este metodo hacemos que el token no sea necesario
	 * para listar clientes ejemplo : -> "/api/registro" y "/api/registro/page/**"
	 * para indicar que debe para mostrar mas de un rol -> hasAnyRole para mostrar
	 * solo un rol -> hasAnyRole
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/registro", "/api/registro/page/**", "/api/uploads/img/**", "/images", "api/usuario/listar").permitAll()
				.antMatchers(HttpMethod.POST,  "/api/usuario/registrar", "/send/email")
				.permitAll()
				/* 
				 *  SE ASINGO A CADA METODO EN REGISTRO
				 * .antMatchers(HttpMethod.GET, "/api/registro/{id}").hasAnyRole("ROLE USER",
				 * "ADMIN") .antMatchers(HttpMethod.POST,
				 * "/api/registro/upload").hasAnyRole("ROLE USER", "ADMIN")
				 * .antMatchers(HttpMethod.POST,
				 * "/api/registro").hasRole("ADMIN").antMatchers("/api/registro/**").hasRole(
				 * "ADMIN")
				 */
				.anyRequest().authenticated()
				.and().cors().configurationSource(corsConfigurationSource());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		// config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // con este
		// metodo indicamos que obtendra acceso desde localhost
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>( new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
		
		
	}

}
