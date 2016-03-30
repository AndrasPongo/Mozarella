package com.hybridtheory.mozzarella.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.hybridtheory.mozzarella.api.StudentApi;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(StudentApi.class);
    }
}