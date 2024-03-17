package com.hanwha.backend.config;

import com.hanwha.backend.annotation.Verification;
import com.hanwha.backend.util.verifier.BuiltinVerifier;
import com.hanwha.backend.util.verifier.Verifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class VerifierConfig implements InitializingBean {

    private final ApplicationContext applicationContext;
    private final BuiltinVerifier builtinVerifier;

    @Autowired
    public VerifierConfig(ApplicationContext applicationContext, BuiltinVerifier builtinVerifier){
        this.applicationContext = applicationContext;
        this.builtinVerifier = builtinVerifier;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, Object> annotatedBeans = applicationContext.getBeansWithAnnotation(Verification.class);
        for(Object bean : annotatedBeans.values()){
            builtinVerifier.addVerifier((Verifier) bean);
        }
    }
}
