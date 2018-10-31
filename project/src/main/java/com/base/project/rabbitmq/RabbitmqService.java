package com.base.project.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqService {
    private static final Logger logger = LogManager.getLogger(RabbitmqService.class);

    @RabbitListener(queues = "cif_q_updateEvent")
    public void processMessage(String body) {
        logger.info("rabbitmq result:" + body);
    }
}
