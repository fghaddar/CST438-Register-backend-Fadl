package com.cst438;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cst438.service.GradebookService;
import com.cst438.service.GradebookServiceMQ;
import com.cst438.service.GradebookServiceREST;

@SpringBootApplication
@EnableWebSecurity
public class Cst4380wRegistrationApplication extends WebSecurityConfigurerAdapter 
{  
	public static void main(String[] args) 
	{
		SpringApplication.run(Cst4380wRegistrationApplication.class, args);
	}
	
	@Override
   	protected void configure(HttpSecurity http) throws Exception {
		SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");
		http.cors();
 		http.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
 		// permit requests to /course without authentication. All other URLS are authenticated
 		http.authorizeRequests().mvcMatchers(HttpMethod.PUT, "/course").permitAll();
 		http.antMatcher("/**").authorizeRequests( a -> a.antMatchers("/", "/home", "/login", "/webjars/**").permitAll()
 		.anyRequest().authenticated())
 		.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
		.logout(l -> l.logoutSuccessUrl("/").permitAll() )
 		.oauth2Login(o -> o.failureHandler((request, response, exception) -> {
 			System.out.println("error.message " + exception.getMessage());
 			handler.onAuthenticationFailure(request, response, exception);
 		}));
	}
	

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://accounts.google.com"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean(name = "GradebookService")
	@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "MQ")
	public GradebookService gradebookServiceMQ() 
	{
		return new GradebookServiceMQ();
	}
	
	@Bean(name = "GradebookService")
	@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "REST")
	public GradebookService gradebookServiceREST() 
	{
		return new GradebookServiceREST();
	}
	
	@Bean(name = "GradebookService")
	@ConditionalOnProperty(prefix="gradebook", name="service", havingValue = "default")
	public GradebookService gradebookDefault() 
	{
		return new GradebookService();
	}
}
