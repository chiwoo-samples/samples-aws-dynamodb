package org.chiwooplatform.aws.samples.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class ApplicationConfiguration {

    private static final String ALGORITHM = "PBEWithMD5AndDES";
    // private static final String ALGORITHM = "PBEWithMD5AndDES";

    @Value("${spring.application.name:SPRING-SAMPLES}")
    private String seedKey;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {

        final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(seedKey);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("hexadecimal");
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        // encryptor.setAlgorithm(algorithm);
        return encryptor;
    }
}
