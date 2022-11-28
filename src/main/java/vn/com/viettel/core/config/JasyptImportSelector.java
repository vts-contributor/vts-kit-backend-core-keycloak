package vn.com.viettel.core.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class JasyptImportSelector implements ImportSelector {
    public JasyptImportSelector() {
    }

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableJasypt.class.getName(), true);
        if (attributes != null) {
            String password = (String) attributes.get("key");
            System.setProperty("viettel-jasypt-pwd", password);
        }
        return new String[0];
    }
}
