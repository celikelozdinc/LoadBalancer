package tr.edu.itu.bbf.cloudcore.distributed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import tr.edu.itu.bbf.cloudcore.distributed.ipc.EventSender;
import tr.edu.itu.bbf.cloudcore.distributed.service.InMemoryStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@ComponentScan(basePackages = {"tr.edu.itu.bbf.cloudcore.distributed"})
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private EventSender sender;

    @Autowired
    private InMemoryStore inMemoryStore;

    @Autowired
    private Environment environment;

    private String workingMode;

    private enum Events{Pay,Receive,StartFromScratch}

    private enum Hosts{SMOC1,SMOC2,SMOC3,SMOC4,SMOC5,SMOC6,SMOC7,SMOC8,SMOC9,SMOC10,SMOC11,SMOC12,SMOC13,SMOC14,SMOC15}

    private Integer eventNumber;

    static final Logger logger = LoggerFactory.getLogger(Application.class);



    @Override
    public void run(String... args) throws Exception {

        this.eventNumber = 1;
        Integer event = 0;

        /* Read number of cycles */
        Integer numberOfEvents = Integer.valueOf(environment.getProperty("loadbalancer.events"));

        /* Read number of replicas. Events will be sent to these smocs */
        Integer numberOfReplicas = Integer.valueOf(environment.getProperty("loadbalancer.replicas"));

        workingMode = environment.getProperty("loadbalancer.workingMode");

        // iterate over enums using for loop
            switch (workingMode){
                case "randomized":
                    logger.info("Working Mode = Randomized");
                    while(event < numberOfEvents) {
                        Hosts randomHost = Hosts.values()[new Random().nextInt(numberOfReplicas)];
                        logger.info("...Starting processing event {} inside host {}...", event, randomHost.toString());
                        sendEventToEnsemble(randomHost.toString(), numberOfReplicas, false);
                        logger.info("...Finished processing event {} inside host {}...", event, randomHost.toString());
                        event = event + 1;
                    }
                    break;
                    case "ordered":
                    logger.info("Working Mode = Ordered");
                    /* Prepare hosts list */
                    List<String> hostsList = new ArrayList<String>();
                    for(int hostIndex=1; hostIndex<=numberOfReplicas;hostIndex++){
                        String host = "SMOC"+hostIndex;
                        hostsList.add(host);
                    }
                    while(event < numberOfEvents) {
                        for (String host : hostsList) {
                            logger.info("...Starting event {} inside host {}...",event,host);
                            sendEventToEnsemble(host.toString(),numberOfReplicas, true);
                            logger.info("...Finished event {} inside host {}...",event,host);
                            event = event + 1;
                        }
                    }
                    break;
            }

    }

    public void sendEventToEnsemble(String host, Integer numOfReplicas, boolean withCkpt){
        Events event = Events.Pay;
        logger.info("Sending {}.event which is __{}__ to __{}__ with flag __{}__", this.eventNumber, event.toString(),host,true);
        /* Send this message to chosen smoc in order to take CKPT  */
        String ckpt = sender.send(this.eventNumber, host, event.toString(),true);
        /* Store CKPT information which is received from smoc */
        inMemoryStore.persist(ckpt);
        /* Send this message to other smocs in order NOT to take CKPT  */
        /* Be sure that all the state machines are in same state after each event processing */
        for (int hostCounter=0; hostCounter < numOfReplicas; hostCounter ++ ) {
            Hosts otherHost = Hosts.values()[hostCounter];
            /* otherHost = smocx, host = smocx -> do not process same event again and again */
            if (otherHost.toString().equals(host)) {
                logger.info("Skipping host: __{}__",otherHost);
            }
            /* if solution is ... AND if the otherHost is peer of host*/

            /* */
            else {
                /* if workingMode = ordered     -> solutionType = conventional                  -> all smocs stores each CKPT */
                /* if workingMode = randomized  -> solutionType = distributed || centralized    -> no smoc stores any CKPT */
                logger.info("Sending {}.event which is __{}__ to __{}__ with flag __{}__", this.eventNumber, event.toString(),otherHost,withCkpt);
                String _msg = sender.send(this.eventNumber, otherHost.toString(), event.toString(),withCkpt);
            }
        }
        /*Calculate new event number*/
        this.eventNumber = this.eventNumber + 1;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
