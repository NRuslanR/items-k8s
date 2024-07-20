package org.example.items_service.shared.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.example.items_service.application.shared.exceptions.handling.ApiExceptionHandler;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Tag("MockIntegrationTest")
@EnableFeignClients(basePackages = "org.example.items_service")
@TestPropertySources
@TestInstance(Lifecycle.PER_CLASS)
@Import({
    ApiExceptionHandler.class
})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public @interface MockIntegrationTest 
{
    
}
