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
import java.util.stream.Collectors;


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
        Integer cycle = 0;

        /* Read number of cycles */
        Integer numberOfCycles = Integer.valueOf(environment.getProperty("loadbalancer.cycles"));

        /* Read number of replicas. Events will be sent to these smocs */
        Integer numberOfReplicas = Integer.valueOf(environment.getProperty("loadbalancer.replicas"));

        workingMode = environment.getProperty("loadbalancer.workingMode");

        // iterate over enums using for loop
            switch (workingMode){
                case "randomized":
                    logger.info("Working Mode = Randomized");

                    Hosts randomHost = Hosts.values()[new Random().nextInt(numberOfReplicas)];
                    logger.info("...Starting cycle {} for hostname {}...",cycle,randomHost.toString());
                    sendEventsToSmoc(randomHost.toString(),numberOfReplicas,true);
                    logger.info("...Finished cycle {} for hostname {}...",cycle,randomHost.toString());

                    break;

                    case "ordered":
                    logger.info("Working Mode = Ordered");
                    /* Prepare hosts list */
                    List<String> hostsList = new ArrayList<String>();
                    for(int hostIndex=1; hostIndex<=numberOfReplicas;hostIndex++){
                        String host = "SMOC"+hostIndex;
                        hostsList.add(host);
                    }

                    while(cycle < numberOfCycles) {
                        for (String host : hostsList) {
                            logger.info("...Starting cycle {} for hostname {}...",cycle,host);
                            sendEventsToSmoc(host.toString(),numberOfReplicas,false);
                            logger.info("...Finished cycle {} for hostname {}...",cycle,host);
                        }
                        cycle = cycle + 1;
                    }
                    break;
            }

    }

    public void sendEventsToSmoc(String host, Integer numOfReplicas, boolean willCkptTriggered){
        Events event = Events.Pay;
        logger.info("Sending {}.event which is __{}__ to __{}__", this.eventNumber, event.toString(),host);
        String ckpt = sender.send(this.eventNumber, host, event.toString(),willCkptTriggered);
        /* Store CKPT information which is received from smoc */
        inMemoryStore.persist(ckpt);
        /* Send this message to other smocs in order NOT to take CKPT  */
        for (int hostCounter=0; hostCounter < numOfReplicas; hostCounter ++ ) {
            Hosts otherHost = Hosts.values()[hostCounter];
            if (otherHost.toString().equals(host)) {
                logger.info("*****");
                logger.info("Skipping host: {}",otherHost);
                logger.info("*****");
            }
            else{
                logger.info("*****");
                logger.info("Sending event to host : {}",otherHost);
                logger.info("*****");
                String msg = sender.send(this.eventNumber, otherHost.toString(), event.toString(),willCkptTriggered);
            }
        }
        /*Calculate new event number*/
        this.eventNumber = this.eventNumber + 1;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
