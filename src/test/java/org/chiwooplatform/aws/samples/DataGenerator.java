package org.chiwooplatform.aws.samples;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.chiwooplatform.aws.samples.model.CodeMapping;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataGenerator {

    private final Logger logger = LoggerFactory.getLogger("data.logback.DataGenerator");

    final ExecutorService executor = Executors.newFixedThreadPool(11);

    private final class LogrWorker implements Callable<String> {

        final long from;
        final long to;

        public LogrWorker(long from, long to) {
            this.from = from;
            this.to = to;
        }


        /**
         * {@link CodeMapping}
         */
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

    private Callable<String> task0 = new LogrWorker(1, 100000000);
    private Callable<String> task1 = new LogrWorker(100000001, 200000000);
    private Callable<String> task2 = new LogrWorker(200000001, 300000000);
    private Callable<String> task3 = new LogrWorker(300000001, 400000000);
    private Callable<String> task4 = new LogrWorker(400000001, 500000000);
    private Callable<String> task5 = new LogrWorker(500000001, 600000000);
    private Callable<String> task6 = new LogrWorker(600000001, 700000000);
    private Callable<String> task7 = new LogrWorker(700000001, 800000000);
    private Callable<String> task8 = new LogrWorker(800000001, 900000000);
    private Callable<String> task9 = new LogrWorker(900000001, 1000000000);
    private Callable<String> task10 = new LogrWorker(1000000001, 1100000000);

    /**
     * @throws Exception
     */
    @Test
    public void generateDataLog() throws Exception {

        Future<String> future01A = executor.submit(task0);
        Future<String> future01B = executor.submit(task1);
        Future<String> future02A = executor.submit(task2);
        Future<String> future02B = executor.submit(task3);
        Future<String> future03A = executor.submit(task4);
        Future<String> future03B = executor.submit(task5);
        Future<String> future04A = executor.submit(task6);
        Future<String> future04B = executor.submit(task7);
        Future<String> future05A = executor.submit(task8);
        Future<String> future05B = executor.submit(task9);
        Future<String> future06A = executor.submit(task10);

        StringBuilder b = new StringBuilder();
        b.append('\n').append("future01A: ").append(future01A.get());
        b.append('\n').append("future01B: ").append(future01B.get());
        b.append('\n').append("future02A: ").append(future02A.get());
        b.append('\n').append("future02B: ").append(future02B.get());
        b.append('\n').append("future03A: ").append(future03A.get());
        b.append('\n').append("future03B: ").append(future03B.get());
        b.append('\n').append("future04A: ").append(future04A.get());
        b.append('\n').append("future04B: ").append(future04B.get());
        b.append('\n').append("future05A: ").append(future05A.get());
        b.append('\n').append("future05B: ").append(future05B.get());
        b.append('\n').append("future06A: ").append(future06A.get());
        System.out.println(b.toString());

    }

}
