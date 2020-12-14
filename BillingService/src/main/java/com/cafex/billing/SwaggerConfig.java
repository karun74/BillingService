package com.cafex.billing;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author karunanithi.S
 * This class is created for configuration of the Swagger API of Compute Service
 * It excess all the API Operations that are configured in the BillingService with required security parameters
 */
@Configuration
@EnableSwagger2

public class SwaggerConfig {
	
	@Value("${service.path.pattern:/*/**}")
	private String pathPattern;
	
	@Value("${service.title:Billing Service}")
	private String title;
	
	@Value("${service.description:This Service allows to generate bill for Cafe X Menu items ordered by customer}")
	private String description;
	
	@Value("${service.license:}")
	private String license;
	
	@Value("${service.licenseUrl:}")
	private String licenseUrl;
	
	
	
	
	
	private  ServletContext servletContext;
	
	 @Autowired
	    public SwaggerConfig(ServletContext servletContext) {
	        this.servletContext = servletContext;
	    }
	
	/**
	 * api - This method return Docket Placeholder containing all the APIs of the Rest Controller
	 * @return Docket Plugin
	 */
	@Bean
    public Docket api() { 
		
        return new Docket(DocumentationType.SWAGGER_2)
          .select()                                  
          .apis(RequestHandlerSelectors.any()) 
         
          .paths(PathSelectors.ant(pathPattern))                          
          .build().apiInfo(apiEndPointsInfo());        
	
    }
	/**
	 * apiEndPointsInfo - Get the API End points Information
	 * @return ApiInfo
	 */
	private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(title)
            .description(description)
            .license(license)
            .licenseUrl(licenseUrl)
            .build();
    }
}
