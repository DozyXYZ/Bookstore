package fi.haagahelia.bookstore;

//import static method antMatcher
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fi.haagahelia.bookstore.web.UserDetailServiceImpl;

// Annotation for a configuration class
// Annotation enables method-level security, secure individual methods
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
            new AntPathRequestMatcher("/api/students**"),
            new AntPathRequestMatcher("/h2-console/**") };

    // Srping bean that configures the security filter chain
    // defines how incoming HTTP requests are handled by the security layer
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize // define authorization rules for incoming HTTP requests
                        .requestMatchers(antMatcher("/css/**")).permitAll() // allows requests for URLS matching the
                                                                            // pattern to be accessed by everyone
                                                                            // without
                                                                            // authentication
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .anyRequest().authenticated()) // ensures that any other requests requires the user to be
                                                       // authenticated
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions
                        .disable())) // for h2console
                .formLogin(formlogin -> formlogin.loginPage("/login") // enables form-based authenticaton (a login
                                                                      // pagefor users to enter credentials)
                        .defaultSuccessUrl("/booklist", true).permitAll()) // after successful login, the user will be
                                                                           // directed to the /booklist page.
                                                                           // permitAll() ensures the login page is
                                                                           // accessible to everyone
                .logout(logout -> logout // configures the log out functionality
                        .permitAll()) // this allows all users to access the logout functionality without needing to
                                      // be authenticated
                .csrf(csrf -> csrf.disable()); // not for production, just for development
        return http.build(); // finalizes the HttpSecurity configuration and returns the SecurityFilterChain
                             // bean
    }

    // load user-specific data (like user name, password, and roles) for
    // authentication
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
