package br.com.nexusapp.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final String signingKey;
    private final String client;
    private final String secret;
    private final String authorizedGrantTypes;
    private final int accessTokenValiditySeconds;

    public AuthorizationServerConfig(
        AuthenticationManager authenticationManager,
        @Value("${security.jwt.signing-key}") String signingKey,
        @Value("${authorization-server.client}") String client,
        @Value("${authorization-server.secret}") String secret,
        @Value("${authorization-server.authorizedGrantTypes}")
        String authorizedGrantTypes,
        @Value("${authorization-server.accessTokenValiditySeconds}")
        int accessTokenValiditySeconds) {
        this.authenticationManager = authenticationManager;
        this.signingKey = signingKey;
        this.client = client;
        this.secret = secret;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(signingKey);
        return tokenConverter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore())
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
            .withClient(client)
            .secret(secret)
            .scopes("read", "write")
            .authorizedGrantTypes(authorizedGrantTypes)
            .accessTokenValiditySeconds(accessTokenValiditySeconds);
    }
}