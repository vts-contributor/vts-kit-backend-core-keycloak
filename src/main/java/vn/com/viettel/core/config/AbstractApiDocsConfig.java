package vn.com.viettel.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public abstract class AbstractApiDocsConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version:unknown}")
    private String appVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        String docTitle = (appName + "-API").toUpperCase();
        String docDescription = "Auto Generated Docs";
        OpenAPI openAPI = new OpenAPI();
        System.setProperty("springdoc.api-docs.path", "/api-docs");
        System.setProperty("springdoc.swagger-ui.enabled", "false");
        openAPI.info(new Info().title(docTitle).description(docDescription).version(appVersion));
        return openAPI;
    }

    /**
    @Bean
    public OperationCustomizer customGlobalHeaders() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter missingParam1 = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema())
                    .name("Accept-Language")
                    .example("vi")
                    .description("Expect Response Language")
                    .required(false);
            operation.addParametersItem(missingParam1);
            return operation;
        };
    }**/

}
