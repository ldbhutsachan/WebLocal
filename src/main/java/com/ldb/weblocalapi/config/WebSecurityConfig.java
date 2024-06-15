package com.ldb.weblocalapi.config;

import com.ldb.weblocalapi.Security.jwt.JwtAuthenticationEntryPoint;
import com.ldb.weblocalapi.Security.jwt.JwtAuthenticationFilter;
import com.ldb.weblocalapi.handler.LimitLoginAuthenticationProvider;
import com.ldb.weblocalapi.utils.APIMappingPaths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
//@Import({ AsyncConfiguration.class})
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Value("${url.mapping}")
    private String urlMapping;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        LimitLoginAuthenticationProvider provider = (LimitLoginAuthenticationProvider) authenticationProvider;
        authenticationManagerBuilder.authenticationProvider(provider);

    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/MBAPI/MB-REPORT/Authenticate",
//                        "/MBAPI/QR-EasyPass/Qr",
//                        "/MBAPI/QR-EasyPass/Id",
//                        "/MBAPI/QR-EasyPass/Find",
//                        "/MBAPI/LDBAPP/**"
//                )
                .antMatchers(
                        urlMapping + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH +
                                APIMappingPaths.API_MB_REPORT_PATH +
                                APIMappingPaths.API_AUTHENTICATION_GATEWAY_PATH + "/**"
//                        urlMapping + APIMappingPaths.API_MB_GATEWAY_VERSION_PATH +
//                                APIMappingPaths.API_MB_REPORT_PATH +
////                                APIMappingPaths.API_FILE_PATH +
//                                APIMappingPaths.REPORT_SERVICE.DOWNLOAD_PDF_FILE_GATEWAY_PATH,

                )
                .permitAll()
                .antMatchers(urlMapping +APIMappingPaths.API_MB_GATEWAY_VERSION_PATH + "/**")
//                .hasIpAddress("192.168.8.107")
//                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        // Add our custom JWT Security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
