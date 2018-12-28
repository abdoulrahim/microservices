package com.xcloud.authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final int accessTokenValiditySeconds = 100;

	private final int refreshTokenValiditySeconds = 300;

	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;

	/**
	 * Configure the security of the Authorization Server, which means in practical
	 * terms the /oauth/token endpoint.
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
		super.configure(oauthServer);
	}

	/**
	 * Configure the ClientDetailsService, declaring individual clients and their
	 * properties.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("browser").authorizedGrantTypes("refresh_token", "password").scopes("ui").and()
				// .withClient("account-service")
				// .secret(env.getProperty("ACCOUNT_SERVICE_PASSWORD"))
				// .authorizedGrantTypes("client_credentials",
				// "refresh_token").scopes("server").and()
				.withClient("ticketing-service")
				// .secret(env.getProperty("TICKETING_SERVICE_PASSWORD"))
				.authorizedGrantTypes("client_credentials", "refresh_token", "password", "authorization_code")
				.scopes("server").secret("12345")

				// clients.inMemory().withClient("web-app").scopes("read")
				.autoApprove(true).accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds);
		// .authorizedGrantTypes("implicit", "refresh_token", "password",
		// "authorization_code");
	}

	/**
	 * Configure the non-security features of the Authorization Server endpoints,
	 * like token store, token customizations, user approvals and grant types.
	 */
	// @Override
	// public void configure(AuthorizationServerEndpointsConfigurer endpoints)
	// throws Exception {
	// endpoints.authenticationManager(authenticationManager);
	// }

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	// @Bean
	// public TokenStore tokenStore() {
	// return new InMemoryTokenStore();
	// }

	// TODO JWT Implementation
	//
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(accessTokenConverter())
				.authenticationManager(authenticationManager);
	}

	@Bean
	TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	protected JwtAccessTokenConverter accessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"),
				"qiN+cL2vDCVVT/fHzBTw7f4+48AARimp7WCFa8ntOdA=".toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
		return converter;
	}
}
