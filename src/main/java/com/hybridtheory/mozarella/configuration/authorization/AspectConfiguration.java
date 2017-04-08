package com.hybridtheory.mozarella.configuration.authorization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages="com.hybridtheory.mozarella.authorization.aspects")
public class AspectConfiguration {

}
