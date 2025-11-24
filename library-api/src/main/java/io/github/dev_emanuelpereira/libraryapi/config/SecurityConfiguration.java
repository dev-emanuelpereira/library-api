package io.github.dev_emanuelpereira.libraryapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.dev_emanuelpereira.libraryapi.security.CustomUserDetailsService;
import io.github.dev_emanuelpereira.libraryapi.security.JwtCustomAuthenticationFilter;
import io.github.dev_emanuelpereira.libraryapi.security.LoginSocialSuccessHandler;
import io.github.dev_emanuelpereira.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

//Obtem token e autentica o usuario, valida informações
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) //habilitar controle de permissao nos controllers
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
        return http
                //metodo utilizado para aplicacoes web
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers("/login/**").permitAll();
                            authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
                            authorize.anyRequest().authenticated();
                        }
                )
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/login");
                    oauth2.successHandler(successHandler);
                })
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)//adicionar depois do filtro do Bearer
                .build();
    }

    //ignorar segurança para construir swagger
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
              web.ignoring().requestMatchers(
              "/v2/api-dosc/**",
                        "/v3/api-dosc/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**"

            );
        };
    }

    //tirar prefixo ROLE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter =  new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

    //JWK - JSON Web Key
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = gerarChaveRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);
    }

    //Gerar par de chaves RSA
    private RSAKey gerarChaveRsa() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey chavePublica = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey chavePrivada = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(chavePublica)
                .privateKey(chavePrivada)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //decodificador do jwt
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                //obter token
                .tokenEndpoint("/oauth2/token")
                //utilizado para consultar status do token
                .tokenIntrospectionEndpoint("/oauth2/introspect")
                //revogar token
                .tokenRevocationEndpoint("/oauth2/revoke")
                //authorization endpoint
                .authorizationEndpoint("/oauth2/authorizate")
                //informações do usuario OPEN ID CONNECT
                .oidcUserInfoEndpoint("/oauth2/userinfo")
                //obter chave publica para verificar a assinatura do token
                .jwkSetEndpoint("/oauth2/jwks")
                //logout
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }

    //@Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("usuario2")
//                .password(encoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(usuarioService);
    };

}
