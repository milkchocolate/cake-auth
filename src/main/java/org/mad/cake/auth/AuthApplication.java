package org.mad.cake.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public AuthApplication(
            OAuth2AuthorizedClientManager authorizedClientManager
    ) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        * authorizedClientManager.authorize sends out requests to exchange for the token.
        * The process needs libraries from spring-boot-starter-web, such as extracting the response
        * from the idp server.
        * Directly include spring-boot-starter-web can save us some work, but we don't want the app
        * to run as a web application, it can be solved by setting web-application-type to NONE.
        * Please check the application.yml file.
        * Run the app to see the token in the console.
        * */
        System.out.println(getOAuth2AccessToken().getTokenValue());
    }

    private OAuth2AccessToken getOAuth2AccessToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("eatcake")
                .principal("eat") // This value is unnecessary, but if you don't give it a value, it throws an exception.
                .build();
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        return authorizedClient.getAccessToken();
    }
}
