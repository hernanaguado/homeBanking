package com.mindhub.homebanking.repositories.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@EnableWebSecurity
@Configuration

public class WebAuthorization extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter  configura la autorización.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
   http.authorizeRequests()



           .antMatchers(HttpMethod.POST, "/api/clients", "/api/admin/loans", "/api/admin/loans","/api/transactions","/api/PDF").permitAll()  //cuando trabajamos con sprint security limitamos el acceso, de esta foma vamos liberand//dar acceso a las rutas, en este caso a todos

           .antMatchers(HttpMethod.POST,  "/api/clients/current/accounts","/api/clients/current/cards").hasAnyAuthority("ADMIN","CLIENT")  //cuando trabajamos con sprint security limitamos el acceso, de esta foma vamos liberand//dar acceso a las rutas, en este caso a todos

           .antMatchers("/web/index2.html","/web/index2.js","/web/style.css","/web/images/","/api/clients/current","/web/index.html","/web/index.js","/web/css/**","/web/fonts/**", "/web/images/**","/web/js/**","/web/scss/**").permitAll()

           .antMatchers("/rest/**","/h2-console","/clients/current","/web/loanCreateByAdmin.html","/web/loanCreateByAdmin.js").hasAuthority("ADMIN")

            .antMatchers("/web/**","/web/cards.js","/web/cards.html","/web/accounts.html","/web/accounts.js","/web/create-cards.js","/web/create-cards.html","/web/loan-application.html","/web/loan-application.js").hasAnyAuthority("CLIENT","ADMIN");


        http.formLogin()

           .usernameParameter("email")

           .passwordParameter("password")

           .loginPage("/api/login");



   http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens

        http.csrf().disable();



        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }





}






}



