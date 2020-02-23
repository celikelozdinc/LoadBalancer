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

    /*
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
    */

    /*
    @Value("${EVENT_EXCHANGE_NEWCLIENT1}")
    private String EVENT_EXCHANGE_NEWCLIENT1;

    @Value("${EVENT_EXCHANGE_NEWCLIENT2}")
    private String EVENT_EXCHANGE_NEWCLIENT2;

    @Value("${EVENT_EXCHANGE_NEWCLIENT3}")
    private String EVENT_EXCHANGE_NEWCLIENT3;
     */

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
        /*
        exchangeDictionary.put("SMOC8",EVENT_EXCHANGE_SMOC8);
        exchangeDictionary.put("SMOC9",EVENT_EXCHANGE_SMOC9);
        exchangeDictionary.put("SMOC10",EVENT_EXCHANGE_SMOC10);
        exchangeDictionary.put("SMOC11",EVENT_EXCHANGE_SMOC11);
        exchangeDictionary.put("SMOC12",EVENT_EXCHANGE_SMOC12);
        exchangeDictionary.put("SMOC13",EVENT_EXCHANGE_SMOC13);
        exchangeDictionary.put("SMOC14",EVENT_EXCHANGE_SMOC14);
        exchangeDictionary.put("SMOC15",EVENT_EXCHANGE_SMOC15);
         */
    }

    public void send(Integer eventNumber, String host, String event)  {
        /* Prepare message for smoc */
        EventMessage msg = new EventMessage();
        msg.setEvent(event);
        msg.setSender(System.getenv("HOSTNAME"));
        msg.setEventNumber(eventNumber);
        /* Choose exchange for sending message to smoc */
        String exchange = exchangeDictionary.get(host).toString();
        logger.info("Sending event to exchange __{}__",exchange);
        String reply = (String) rabbitTemplate.convertSendAndReceive(exchange,"rpc",msg);
        //String reply = (String) rabbitTemplate.convertSendAndReceive(EVENT_EXCHANGE_NEWCLIENT1,"rpc",msg);
        //rabbitTemplate.convertAndSend(EVENT_EXCHANGE_NEWCLIENT1,"rpc",msg);
        //sleep(2);
        logger.info("Received reply from smoc  __{}__", reply);
    }


    public void sleep(Integer sleepTime){
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException ex) {
            System.out.println("Exception during sleep in main program --> " + ex.toString());
        }

    }

}
