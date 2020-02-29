package tr.edu.itu.bbf.cloudcore.distributed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tr.edu.itu.bbf.cloudcore.distributed.ipc.Response;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Dictionary;

@Service
public class InMemoryStore {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Dictionary ckptDictionary;

    private ArrayList<Response> responseList;

    public InMemoryStore(){
        logger.info(" +++++++++ CONSTRUCTOR of InMemoryStore ++++++++++");
    }

    @PostConstruct
    public void init() {
        logger.info(" +++++++++ POSTCONTRUCT of InMemoryStore ++++++++++");
        responseList = new ArrayList<>();
    }

    public void persist(String ckpt){
        /*  Read from csv */
        String[] values = ckpt.split(",");
        /* Initalize a response object */
        Integer eventNumber = Integer.valueOf(values[0]);
        String sourceState = values[2].toString();
        String processedEvent = values[3].toString();
        String targetState = values[4].toString();
        Response response = new Response(eventNumber,sourceState,processedEvent,targetState);
        /* Add response object to array */
        responseList.add(response);

        logger.info("#of CKPTS stored by loadbalancer: {}",responseList.size());
    }

    public ArrayList<Response> read() {
        return responseList;
    }
}
