package org.chiwooplatform.aws.samples;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataLogGeneratorSecond {

    private final Logger logger = LoggerFactory.getLogger("poc.DeviceInfoLogger");

    final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final class LogrWorker implements Callable<String> {

        final long from;
        final long to;

        public LogrWorker(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String call() throws Exception {
            for (long i = from; i <= to; i++) {
                final String id = UUID.randomUUID().toString();
                final String name = "MyName";
                final String ifid = String.format("%s%s_%s", UUID.randomUUID().toString(), UUID.randomUUID().toString(), i);
                final Long currentTimestamp = System.currentTimeMillis();
                StringBuilder builder = new StringBuilder();
                builder.append("{ ");
                builder.append(String.format("%s%s%s: %s%s%s", '"', "id", '"', '"', id, '"')); // id
                builder.append(String.format(", %s%s%s: %s%s%s", '"', "name", '"', '"', name, '"')); // name
                builder.append(String.format(", %s%s%s: %s%s%s", '"', "ifid", '"', '"', ifid, '"')); // ifid
                builder.append(String.format(", %s%s%s: %s", '"', "regtime", '"', currentTimestamp)); // regtime
                builder.append(" }");
                final String data = builder.toString();
                logger.trace(data);

                if (i % 1000000 == 0) {
                    System.out.println("1초 쉬고 가자 ------------ " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            return "OK";
        }
    }

    private Callable<String> task01A = new LogrWorker(1100000001, 1150000000);
    private Callable<String> task01B = new LogrWorker(1150000001, 1200000000);
    private Callable<String> task02A = new LogrWorker(1200000001, 1250000000);
    private Callable<String> task02B = new LogrWorker(1250000001, 1300000000);
    private Callable<String> task03A = new LogrWorker(1300000001, 1350000000);
    private Callable<String> task03B = new LogrWorker(1350000001, 1400000000);

    /**
     * PRD 기준 1,323,868,043 건의 DeviceInfo 정보가 RDS 의 16개의 샤드로 적재되어 있음.
     * POC 는 14억건을 생성 하여 테스트 데이터 적재
     * 
     * @throws Exception
     */
    @Test
    public void generateDeviceInfoSecondLog() throws Exception {

        Future<String> future01A = executor.submit(task01A);
        Future<String> future01B = executor.submit(task01B);
        Future<String> future02A = executor.submit(task02A);
        Future<String> future02B = executor.submit(task02B);
        Future<String> future03A = executor.submit(task03A);
        Future<String> future03B = executor.submit(task03B);

        StringBuilder b = new StringBuilder();
        b.append('\n').append("future01A: ").append(future01A.get());
        b.append('\n').append("future01B: ").append(future01B.get());
        b.append('\n').append("future02A: ").append(future02A.get());
        b.append('\n').append("future02B: ").append(future02B.get());
        b.append('\n').append("future03A: ").append(future03A.get());
        b.append('\n').append("future03B: ").append(future03B.get());
        System.out.println(b.toString());

    }

}
