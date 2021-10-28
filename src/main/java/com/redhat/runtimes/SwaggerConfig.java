package com.redhat.runtimes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket swaggerDocket(ServletContext servletContext, @Value("${openapi.feedback360Survey.base-path:}") String basePath) {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("local")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.redhat.runtimes.api"))
				.build()
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
				.apiInfo(apiInfo());
    }
	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Todos API")
				.description("This API specification contains information Todo List items")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.termsOfServiceUrl("")
				.version("2.0.0")
				.contact(new Contact("","", "appdevpractice@redhat.com"))
				.build();
	}
	
}
