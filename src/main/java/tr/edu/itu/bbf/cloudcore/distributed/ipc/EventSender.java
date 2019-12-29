package tr.edu.itu.bbf.cloudcore.distributed.ipc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EventSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${EVENT_EXCHANGE_SMOC2}")
    private String EVENT_EXCHANGE_SMOC2;

    static final Logger logger = LoggerFactory.getLogger(EventSender.class);

    public EventSender(){}

    public void send(String host, String event)  {
        EventMessage msg = new EventMessage();
        msg.setEvent(event);
        String exchange = "EVENT_EXCHANGE_" + host;
        logger.info("Sending event {} to exchange {}",msg.getEvent(),exchange);
        String reply = (String) rabbitTemplate.convertSendAndReceive(exchange,"rpc",msg);
        logger.info("Received reply from smoc = {}", reply);
    }


}
