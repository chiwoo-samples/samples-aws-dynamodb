package org.chiwooplatform.aws.samples.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Provider;
import java.security.Security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JasyptStringEncryptorTest {

    private static final String ALGORITHM = "PBEWithMD5AndDES";

    private StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("samples-aws-dynamodb");
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("hexadecimal");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Test
    void ut1000_checkAlgorithm() throws Exception {
        for (Provider provider : Security.getProviders()) {
            log.info(provider.getName());
            for (String key : provider.stringPropertyNames())
                log.info("\t" + key + "\t" + provider.getProperty(key));
        }
    }

    @Test
    void ut1001_stringEncryptor() throws Exception {
        final String source = "TmBAXmSuY9zOb8B9vsFwds0eiCEdWuypnUP3wuK/";
        StringEncryptor encryptor = jasyptStringEncryptor();
        log.info("ENC({})", encryptor.encrypt(source));
        assertEquals(source, encryptor.decrypt(encryptor.encrypt(source)));
    }

    @Test
    void ut1002_stringEncryptor() throws Exception {
        final String source = "We1c0me^2018";
        StringEncryptor encryptor = jasyptStringEncryptor();
        log.info("u ENC({})", encryptor.encrypt("beluga2018"));
        log.info("p ENC({})", encryptor.encrypt(source));
        assertEquals(source, encryptor.decrypt(encryptor.encrypt(source)));
    }
}
