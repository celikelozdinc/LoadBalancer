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

import java.util.*;


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

    private Dictionary peerGroup;

    private Integer eventNumber;

    static final Logger logger = LoggerFactory.getLogger(Application.class);



    @Override
    public void run(String... args) throws Exception {
        logger.info("Application::run()");
        this.peerGroup = new Hashtable();
        this.peerGroup.put(1, new ArrayList<>(Arrays.asList("SMOC1","SMOC2")));
        this.peerGroup.put(2, new ArrayList<>(Arrays.asList("SMOC3","SMOC4")));
        this.peerGroup.put(3, new ArrayList<>(Arrays.asList("SMOC5","SMOC6")));
        this.peerGroup.put(4, new ArrayList<>(Arrays.asList("SMOC7","SMOC8")));
        this.peerGroup.put(5, new ArrayList<>(Arrays.asList("SMOC9","SMOC10")));
        this.peerGroup.put(6, new ArrayList<>(Arrays.asList("SMOC11","SMOC12")));
        this.peerGroup.put(7, new ArrayList<>(Arrays.asList("SMOC13","SMOC14")));
        this.peerGroup.put(8, new ArrayList<>(Arrays.asList("SMOC15","SMOC16")));
        this.peerGroup.put(9, new ArrayList<>(Arrays.asList("SMOC17","SMOC18")));
        this.peerGroup.put(10, new ArrayList<>(Arrays.asList("SMOC19","SMOC20")));
        this.peerGroup.put(11, new ArrayList<>(Arrays.asList("SMOC21","SMOC22")));
        this.peerGroup.put(12, new ArrayList<>(Arrays.asList("SMOC23","SMOC24")));

        this.eventNumber = 1;
        Integer event = 0;
        Integer eventIndex = -1;

        /* Read number of cycles */
        Integer numberOfEvents = Integer.valueOf(environment.getProperty("loadbalancer.events"));

        /* Read number of replicas. Events will be sent to these smocs */
        Integer numberOfReplicas = Integer.valueOf(environment.getProperty("loadbalancer.replicas"));

        workingMode = environment.getProperty("loadbalancer.workingMode");

        // iterate over enums using for loop
            switch (workingMode){
                case "WithoutExtraCheckpoint":
                    logger.info("Working Mode = WithoutExtraCheckpoint --> Solution Type should be DISTRIBUTED or CENTRALIZED");
                    while(event < numberOfEvents) {
                        eventIndex = event % 3; // since we have 3 event transitions
                        Hosts randomHost = Hosts.values()[new Random().nextInt(numberOfReplicas)]; // start from random smoc
                        Events eventToBeProcessed = Events.values()[eventIndex];
                        logger.info("...Starting processing event {} inside host {}...", event, randomHost.toString());
                        sendEventToEnsemble(eventToBeProcessed.toString(),randomHost.toString(), numberOfReplicas, false, false);
                        logger.info("...Finished processing event {} inside host {}...", event, randomHost.toString());
                        event = event + 1;
                    }
                    break;
                    case "WithExtraCheckpoint":
                    logger.info("Working Mode = WithExtraCheckpoint --> Solution Type should be CONVENTIONAL");
                    while(event < numberOfEvents) {
                        eventIndex = event % 3; // since we have 3 event transitions
                        Events eventToBeProcessed = Events.values()[eventIndex];
                        Hosts firstHost = Hosts.values()[numberOfReplicas - 1]; // start from last smoc in the current replica set
                        logger.info("...Starting event {} inside host {}...",event,firstHost.toString());
                        sendEventToEnsemble(eventToBeProcessed.toString(),firstHost.toString(),numberOfReplicas, true,true);
                        logger.info("...Finished event {} inside host {}...",event,firstHost.toString());
                        event = event + 1;
                    }
                    break;
                case "PartialCheckpoint":
                    logger.info("Working Mode = PartialCheckpoint --> Solution Type should be MIRRORED");
                    while(event < numberOfEvents) {
                        eventIndex = event % 3; // since we have 3 event transitions
                        Events eventToBeProcessed = Events.values()[eventIndex];
                        Hosts randomHost = Hosts.values()[new Random().nextInt(numberOfReplicas)]; // start from random smoc
                        logger.info("...Starting event {} inside host {}...",event,randomHost.toString());
                        sendEventToEnsemble(eventToBeProcessed.toString(),randomHost.toString(),numberOfReplicas, false,true);
                        logger.info("...Finished event {} inside host {}...",event,randomHost.toString());
                        event = event + 1;
                    }
                    break;
            }

    }

    public void sendEventToEnsemble(String event, String host, Integer numOfReplicas, boolean willOthersCkpt, boolean willPeerCkpt){
        logger.info("Sending {}.event which is __{}__ to __{}__ with flag __{}__", this.eventNumber, event,host,true);
        /* Send this message to chosen smoc in order to take CKPT  */
        String ckpt = sender.send(this.eventNumber, host, event,true);
        /* Store CKPT information which is received from smoc */
        inMemoryStore.persist(ckpt);
        /* Send this message to other smocs in order NOT to take CKPT  */
        /* Be sure that all the state machines are in same state after each event processing */
        for (int hostCounter=0; hostCounter < numOfReplicas; hostCounter ++ ) {
            Hosts otherHost = Hosts.values()[hostCounter];

            /* otherHost = smocx, host = smocx -> do not process same event again, skip */
            if (otherHost.toString().equals(host)) {
                logger.info("Skipping host: __{}__",otherHost);
            }
            /* if the otherHost is peer of host */
            /* if workingMode = PartialCheckpoint       -> solutionType = mirrored                       -> peer stores CKPT */
            /* if workingMode = WithoutExtraCheckpoint  -> solutionType = distributed  || centralized    -> peer does not store CKPT */
            /* if workingMode = WithExtraCheckpoint     -> solutionType = conventional                   -> peer stores CKPT */
            else if (otherHost.toString().equals(whoIsMyPair(host))){
                logger.info("Processing {} which is also peer of {}:",otherHost, host);
                logger.info("Sending {}.event which is __{}__ to __{}__ with flag __{}__", this.eventNumber, event,otherHost,willPeerCkpt);
                String _msg = sender.send(this.eventNumber, otherHost.toString(), event,willPeerCkpt);
            }
            /* if workingMode = WithoutExtraCheckpoint  -> solutionType = distributed  || centralized    -> otherHost does not store CKPT */
            /* if workingMode = WithExtraCheckpoint     -> solutionType = conventional                   -> otherHost stores CKPT */
            else {
                logger.info("Processing otherHost {}:",otherHost.toString());
                logger.info("Sending {}.event which is __{}__ to __{}__ with flag __{}__", this.eventNumber, event,otherHost,willOthersCkpt);
                String __msg = sender.send(this.eventNumber, otherHost.toString(), event,willOthersCkpt);
            }
        }
        /*Calculate new event number*/
        this.eventNumber = this.eventNumber + 1;
    }

    public String whoIsMyPair(String host){
        /* n <- smoc<n>*/
        Integer smocNumber = Integer.parseInt(host.substring(4));
        Integer peerGroup = -1 ;
        String peer = "";
        if (smocNumber % 2 == 0)
        { /* EVEN */
            /* Peer Group = 10 <- smoc20 */
            peerGroup = smocNumber/2;
            peer = ((ArrayList<String>) this.peerGroup.get(peerGroup)).get(0);
        }
        else
        { /* ODD */
            /* Peer Group = 10 <- smoc19 */
            peerGroup = (smocNumber+1)/2;
            peer = ((ArrayList<String>) this.peerGroup.get(peerGroup)).get(1);
        }
        return peer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
