package vn.com.viettel.core.config;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@KeycloakConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class GlobalSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${common.ip.restricted.url}")
    String ipRestrictedUrl;

    @Value("${common.permission.ignore.url}")
    String permissionIgnoreUrl;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/favicon.ico"
                ).permitAll();

        // Cau hinh restricted IP via URL
        String strUrlIpRestricted = ipRestrictedUrl;
        if (strUrlIpRestricted.trim().length() > 0) {
            String[] arrUrlIpRestricted = strUrlIpRestricted.split(";");
            if (arrUrlIpRestricted.length > 0) {
                for (String string : arrUrlIpRestricted) {
                    String[] strConf = string.trim().split("\\|");
                    if (strConf.length > 1) {
                        String strUrl = strConf[0].trim();
                        String strIp = strConf[1].trim();
                        if (strUrl.length() > 0 && strIp.length() > 0) {
                            String[] arrIP = strIp.split(",");
                            StringBuilder hasIpAddress = new StringBuilder();
                            for (String ip : arrIP) {
                                hasIpAddress.append("hasIpAddress('").append(ip).append("')").append(" or ");
                            }
                            http.authorizeRequests().antMatchers(strUrl).access(hasIpAddress.toString());
                        }
                    }
                }
            }
        }

        // Cau hinh ignore URL bypass authentication
        String strPermission = permissionIgnoreUrl;
        if (strPermission.trim().length() > 0) {
            String[] arrLinkIgnore = strPermission.split(";");
            if (arrLinkIgnore.length > 0) {
                for (String string : arrLinkIgnore) {
                    String strLinkIg = string.trim();
                    String[] strSleep = strLinkIg.split(":");
                    if (strSleep.length > 1) {
                        String strMethod = strSleep[0].trim();
                        String strUrl = strSleep[1].trim();
                        if (strMethod.length() > 0 && strUrl.length() > 0) {
                            switch (strMethod.toUpperCase()) {
                                case "GET":
                                    http.authorizeRequests().antMatchers(HttpMethod.GET, strUrl).permitAll();
                                    break;
                                case "HEAD":
                                    http.authorizeRequests().antMatchers(HttpMethod.HEAD, strUrl).permitAll();
                                    break;
                                case "POST":
                                    http.authorizeRequests().antMatchers(HttpMethod.POST, strUrl).permitAll();
                                    break;
                                case "PUT":
                                    http.authorizeRequests().antMatchers(HttpMethod.PUT, strUrl).permitAll();
                                    break;
                                case "PATCH":
                                    http.authorizeRequests().antMatchers(HttpMethod.PATCH, strUrl).permitAll();
                                    break;
                                case "DELETE":
                                    http.authorizeRequests().antMatchers(HttpMethod.DELETE, strUrl).permitAll();
                                    break;
                                case "OPTIONS":
                                    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, strUrl).permitAll();
                                    break;
                                case "TRACE":
                                    http.authorizeRequests().antMatchers(HttpMethod.TRACE, strUrl).permitAll();
                                    break;
                                default:
                                    http.authorizeRequests().antMatchers(strUrl).permitAll();
                                    break;
                            }
                        } else {
                            http.authorizeRequests().antMatchers(strLinkIg).permitAll();
                        }
                    } else {
                        http.authorizeRequests().antMatchers(strLinkIg).permitAll();
                    }
                }
            }
        }
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }
}
