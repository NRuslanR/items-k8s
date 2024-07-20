package org.example.items_service.shared.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestPropertySource;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@TestPropertySource(locations = {
    "classpath:integration-services.properties",
    "classpath:application-test.properties"
})
public @interface TestPropertySources 
{
    
}
