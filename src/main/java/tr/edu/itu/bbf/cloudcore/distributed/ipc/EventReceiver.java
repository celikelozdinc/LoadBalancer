package tr.edu.itu.bbf.cloudcore.distributed.ipc;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tr.edu.itu.bbf.cloudcore.distributed.service.InMemoryStore;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Component
public class EventReceiver {

    @Autowired
    private InMemoryStore inMemoryStore;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public EventReceiver(){
        logger.info(" +++++++++ CONSTRUCTOR of RECEIVER ++++++++++");
    }

    @PostConstruct
    public void init() {
        logger.info(" +++++++++ POSTCONTRUCT of RECEIVER ++++++++++");
    }

    @RabbitListener(queues = "${LB_QUEUE}")
    public ArrayList<Response> process(@NotNull CkptMessage msg) throws UnknownHostException {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.info("CkptMessage Received from sender. Hostname of sender={}, IP of sender={}",msg.getHostname(),msg.getIpAddr());
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!");
        return inMemoryStore.read();
    }


}
