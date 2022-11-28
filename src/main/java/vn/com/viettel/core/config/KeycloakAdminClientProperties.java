package vn.com.viettel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak-admin-client")
@Data
public class KeycloakAdminClientProperties {
    String realm;
    String clientId;
    String authServerUrl;
    String masterUsername;
    String masterPassword;
    int connectionPoolSize;
}
