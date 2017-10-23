package com.java.api.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by kevintian on 2017/10/23.
 */
public class ConfirmMode {
    static Logger logger = LoggerFactory.getLogger(ConfirmMode.class);

    final static String QUEUE_NAME = "confirm-test";
    static int msgCount = 100;
    static ConnectionFactory connectionFactory;

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0) {
            msgCount = Integer.parseInt(args[0]);
        }

        connectionFactory = new ConnectionFactory();

        // Consume msgCount messages.
        (new Thread(new Consumer())).start();
        // Publish msgCount messages and wait for confirms.
        (new Thread(new Publisher())).start();
    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    static class Publisher implements Runnable {
        public void run() {
            try {
                long startTime = System.currentTimeMillis();

                // Setup
                Connection conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);
                ch.confirmSelect();

                // Publish
                String publishContent = "nop";
                for (long i = 0; i < msgCount; ++i) {
                    ch.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, publishContent.getBytes());
                }
                logger.info("publisher wait for confirm...");

                ch.waitForConfirmsOrDie();

                logger.info("publisher confirmed.");
                // Cleanup
                ch.queueDelete(QUEUE_NAME);
                ch.close();
                conn.close();

                long endTime = System.currentTimeMillis();
                logger.debug(String.format("Test took %.3fs\n", (float) (endTime - startTime) / 1000));
            } catch (Throwable e) {
                logger.error("foobar:", e);
            }
        }
    }

    static class Consumer implements Runnable {
        public void run() {
            try {
                // Setup
                Connection conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume.
                // NOTE: QueueingConsumer is deprecated since 4.x.x(http://blog.csdn
                // .net/u013256816/article/details/71342622)
                QueueingConsumer qc = new QueueingConsumer(ch);
                ch.basicConsume(QUEUE_NAME, true, qc);
                for (int i = 0; i < msgCount; ++i) {
                    QueueingConsumer.Delivery delivery = qc.nextDelivery();
                    String encoding = delivery.getProperties().getContentEncoding() == null ? "utf-8" : delivery
                            .getProperties().getContentEncoding();
                    String content = new String(delivery.getBody(), encoding);
                    logger.info("consumer take:" + content);
                }

                // Cleanup
                ch.close();
                conn.close();
            } catch (Throwable e) {
                logger.error("Whoosh!", e);
            }
        }
    }
}
