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
import java.util.concurrent.TimeUnit;


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

    @Value("${EVENT_EXCHANGE_SMOC4}")
    private String EVENT_EXCHANGE_SMOC4;

    @Value("${EVENT_EXCHANGE_SMOC5}")
    private String EVENT_EXCHANGE_SMOC5;

    @Value("${EVENT_EXCHANGE_SMOC6}")
    private String EVENT_EXCHANGE_SMOC6;

    @Value("${EVENT_EXCHANGE_SMOC7}")
    private String EVENT_EXCHANGE_SMOC7;

    @Value("${EVENT_EXCHANGE_SMOC8}")
    private String EVENT_EXCHANGE_SMOC8;

    @Value("${EVENT_EXCHANGE_SMOC9}")
    private String EVENT_EXCHANGE_SMOC9;

    @Value("${EVENT_EXCHANGE_SMOC10}")
    private String EVENT_EXCHANGE_SMOC10;

    @Value("${EVENT_EXCHANGE_SMOC11}")
    private String EVENT_EXCHANGE_SMOC11;

    @Value("${EVENT_EXCHANGE_SMOC12}")
    private String EVENT_EXCHANGE_SMOC12;

    @Value("${EVENT_EXCHANGE_SMOC13}")
    private String EVENT_EXCHANGE_SMOC13;

    @Value("${EVENT_EXCHANGE_SMOC14}")
    private String EVENT_EXCHANGE_SMOC14;

    @Value("${EVENT_EXCHANGE_SMOC15}")
    private String EVENT_EXCHANGE_SMOC15;

    @Value("${EVENT_EXCHANGE_SMOC16}")
    private String EVENT_EXCHANGE_SMOC16;

    @Value("${EVENT_EXCHANGE_SMOC17}")
    private String EVENT_EXCHANGE_SMOC17;

    @Value("${EVENT_EXCHANGE_SMOC18}")
    private String EVENT_EXCHANGE_SMOC18;

    @Value("${EVENT_EXCHANGE_SMOC19}")
    private String EVENT_EXCHANGE_SMOC19;

    @Value("${EVENT_EXCHANGE_SMOC20}")
    private String EVENT_EXCHANGE_SMOC20;

    @Value("${EVENT_EXCHANGE_SMOC21}")
    private String EVENT_EXCHANGE_SMOC21;

    @Value("${EVENT_EXCHANGE_SMOC22}")
    private String EVENT_EXCHANGE_SMOC22;

    @Value("${EVENT_EXCHANGE_SMOC23}")
    private String EVENT_EXCHANGE_SMOC23;

    @Value("${EVENT_EXCHANGE_SMOC24}")
    private String EVENT_EXCHANGE_SMOC24;

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
        exchangeDictionary.put("SMOC4",EVENT_EXCHANGE_SMOC4);
        exchangeDictionary.put("SMOC5",EVENT_EXCHANGE_SMOC5);
        exchangeDictionary.put("SMOC6",EVENT_EXCHANGE_SMOC6);
        exchangeDictionary.put("SMOC7",EVENT_EXCHANGE_SMOC7);
        exchangeDictionary.put("SMOC8",EVENT_EXCHANGE_SMOC8);
        exchangeDictionary.put("SMOC9",EVENT_EXCHANGE_SMOC9);
        exchangeDictionary.put("SMOC10",EVENT_EXCHANGE_SMOC10);
        exchangeDictionary.put("SMOC11",EVENT_EXCHANGE_SMOC11);
        exchangeDictionary.put("SMOC12",EVENT_EXCHANGE_SMOC12);
        exchangeDictionary.put("SMOC13",EVENT_EXCHANGE_SMOC13);
        exchangeDictionary.put("SMOC14",EVENT_EXCHANGE_SMOC14);
        exchangeDictionary.put("SMOC15",EVENT_EXCHANGE_SMOC15);
        exchangeDictionary.put("SMOC16",EVENT_EXCHANGE_SMOC16);
        exchangeDictionary.put("SMOC17",EVENT_EXCHANGE_SMOC17);
        exchangeDictionary.put("SMOC18",EVENT_EXCHANGE_SMOC18);
        exchangeDictionary.put("SMOC19",EVENT_EXCHANGE_SMOC19);
        exchangeDictionary.put("SMOC20",EVENT_EXCHANGE_SMOC20);
        exchangeDictionary.put("SMOC21",EVENT_EXCHANGE_SMOC21);
        exchangeDictionary.put("SMOC22",EVENT_EXCHANGE_SMOC22);
        exchangeDictionary.put("SMOC23",EVENT_EXCHANGE_SMOC23);
        exchangeDictionary.put("SMOC24",EVENT_EXCHANGE_SMOC24);

    }

    public String send(Integer eventNumber, String host, String event, boolean willCkptTriggered)  {
        /* Prepare message for smoc */
        EventMessage msg = new EventMessage();
        msg.setEvent(event);
        msg.setSender(System.getenv("HOSTNAME"));
        msg.setEventNumber(eventNumber);
        msg.setCkptTriggered(willCkptTriggered);
        /* Choose exchange for sending message to smoc */
        String exchange = exchangeDictionary.get(host).toString();
        logger.info("Sending event ___{}___ to exchange __{}__ with flag __{}__",event,exchange, willCkptTriggered);
        String reply = (String) rabbitTemplate.convertSendAndReceive(exchange,"rpc",msg);
        //rabbitTemplate.convertAndSend(EVENT_EXCHANGE_NEWCLIENT1,"rpc",msg);
        //logger.info("Received reply which is processed for event: ___{}___",reply.getHeaders().get("processedEvent").toString());
        logger.info("Received reply from smoc  __{}__", reply);
        return reply;
    }


    public void sleep(Integer sleepTime){
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException ex) {
            System.out.println("Exception during sleep in main program --> " + ex.toString());
        }

    }

}
