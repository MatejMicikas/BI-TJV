package fit.biktjv.customers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //   docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html
        // matching must start from more specific to the general ones
        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/locale").permitAll()
                .requestMatchers("/customers", "/receipts").hasRole("user")
                .requestMatchers("/rest/**").permitAll()
                .requestMatchers("/**").hasRole("admin")
                .and()
                .formLogin().loginPage("/login");
        //.formLogin(withDefaults());  default login page
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("user")
                        .build();
        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("user", "admin")
                        .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
