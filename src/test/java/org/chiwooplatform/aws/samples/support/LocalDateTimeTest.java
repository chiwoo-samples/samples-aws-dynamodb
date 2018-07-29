package org.chiwooplatform.aws.samples.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDateTimeTest {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter
            .ofPattern("[yyyyMMddHHmmss][yyyy-MM-dd HH:mm:ss][yyyy-MM-dd'T'HH:mm:ss]");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("[yyyyMMdd][yyyy-MM-dd][yyyy/MM/dd]");

    //    {
    //        org.springframework.data.repository.query.QueryLookupStrategy
    //    }
    /**
     * test yyyyMMddHHmmss
     * 
     * @throws Exception
     */
    @Test
    void ut1001_localDtmCASE1() throws Exception {
        final String value = "20180101121200";
        final LocalDateTime localDtm = LocalDateTime.parse(value, DATETIME_FORMATTER);
        assertEquals("2018-01-01T12:12", localDtm.toString());
        log.info("localDtm: {}", localDtm);
        log.info("Timestamp: {}", new Timestamp(System.currentTimeMillis()));

    }

    /**
     * test yyyy-MM-dd'T'HH:mm:ss
     * 
     * @throws Exception
     */
    @Test
    void ut1002_localDtmCASE2() throws Exception {
        final String value = "2018-01-01T12:12:00";
        final LocalDateTime localDtm = LocalDateTime.parse(value, DATETIME_FORMATTER);
        assertEquals("2018-01-01T12:12", localDtm.toString());
        log.info("localDtm: {}", localDtm);
    }

    /**
     * test yyyy-MM-dd HH:mm:ss
     * 
     * @throws Exception
     */
    @Test
    void ut1003_localDtmCASE3() throws Exception {
        final String value = "2018-01-01 12:12:00";

        final LocalDateTime localDtm = LocalDateTime.parse(value, DATETIME_FORMATTER);
        assertEquals("2018-01-01T12:12", localDtm.toString());
        log.info("localDtm: {}", localDtm);
    }

    /**
     * test yyyyMMdd
     * 
     * @throws Exception
     */
    @Test
    void ut1003_localDateCASE1() throws Exception {
        final String value = "20180101";
        final LocalDate localDt = LocalDate.parse(value, DATE_FORMATTER);
        assertEquals("2018-01-01", localDt.toString());
        log.info("localDt: {}", localDt);
    }

    /**
     * test yyyy-MM-dd
     * 
     * @throws Exception
     */
    @Test
    void ut1003_localDateCASE2() throws Exception {
        final String value = "2018-01-01";
        final LocalDate localDt = LocalDate.parse(value, DATE_FORMATTER);
        assertEquals("2018-01-01", localDt.toString());
        log.info("localDt: {}", localDt);
    }

    /**
     * test yyyy/MM/dd
     * 
     * @throws Exception
     */
    @Test
    void ut1003_localDateCASE3() throws Exception {
        final String value = "2018/01/01";
        final LocalDate localDt = LocalDate.parse(value, DATE_FORMATTER);
        assertEquals("2018-01-01", localDt.toString());
        log.info("localDt: {}", localDt);
    }
}
