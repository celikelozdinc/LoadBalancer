package tr.edu.itu.bbf.cloudcore.distributed.ipc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Dictionary;
import java.util.Hashtable;


@Component
public class EventSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${EVENT_EXCHANGE_SMOC1}")
    private String EVENT_EXCHANGE_SMOC1;

    @Value("${EVENT_EXCHANGE_SMOC2}")
    private String EVENT_EXCHANGE_SMOC2;

    @Value("${EVENT_EXCHANGE_SMOC3}")
    private String EVENT_EXCHANGE_SMOC3;


    private Dictionary exchangeDictionary;

    static final Logger logger = LoggerFactory.getLogger(EventSender.class);


    public EventSender(){
        logger.info("+++++EventSender::Constructor+++++");
    }

    @PostConstruct
    public void init() {
        logger.info("+++++EventSender::PostConstruct+++++");
        exchangeDictionary = new Hashtable();
        exchangeDictionary.put("SMOC1",EVENT_EXCHANGE_SMOC1);
        exchangeDictionary.put("SMOC2",EVENT_EXCHANGE_SMOC2);
        exchangeDictionary.put("SMOC3",EVENT_EXCHANGE_SMOC3);
    }

    public void send(String host, String event)  {
        /* Prepare message for smoc */
        EventMessage msg = new EventMessage();
        msg.setEvent(event);
        msg.setSender(System.getenv("HOSTNAME"));
        /* Choose exchange for sendind message to smoc */
        String exchange = exchangeDictionary.get(host).toString();
        logger.info("Sending event __{}__ to exchange __{}__ by process __{}__",msg.getEvent(),exchange,System.getenv("HOSTNAME"));
        String reply = (String) rabbitTemplate.convertSendAndReceive(exchange,"rpc",msg);
        logger.info("Received reply from smoc  __{}__", reply);
    }


}
