package com.stackroute.orderservice.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SpringFoxConfig {

    public static final String ORDER_TAG = "order service";

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Team 6: Anas Deis, Badreddine Loulidi and Soukaina Moulay Taj", "", "anas.deis@cgi.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Order APIs", "Order REST Service", "1.0",
            "https://www.cgi.com/en/legal-restrictions", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stackroute.orderservice"))
                .build()
                .tags(new Tag(ORDER_TAG, "Set of endpoints for CRUD operations of Orders."))
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}