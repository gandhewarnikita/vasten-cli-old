package com.vasten.cli.security.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("in resource configure security config");
		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/api-docs/**").permitAll();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
//	{
//	    auth.parentAuthenticationManager(authenticationManagerBean())
//	        .userDetailsService(userDetailsService);
//	}

	/*
	 * @Bean public FilterRegistrationBean corsFilter() {
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
	 * CorsConfiguration(); config.setAllowCredentials(true);
	 * config.addAllowedOrigin("*"); config.addAllowedHeader("*");
	 * config.addAllowedMethod("*"); source.registerCorsConfiguration("/**",
	 * config); FilterRegistrationBean bean = new FilterRegistrationBean(new
	 * CorsFilter(source)); bean.setOrder(0); return bean; }
	 */
}
