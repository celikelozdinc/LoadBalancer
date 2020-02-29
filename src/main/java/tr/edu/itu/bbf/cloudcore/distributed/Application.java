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

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
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

    private enum Events{Pay,Receive,StartFromScratch}

    private enum Hosts{SMOC1,SMOC2,SMOC3,SMOC4,SMOC5,SMOC6,SMOC7,SMOC8,SMOC9,SMOC10,SMOC11,SMOC12,SMOC13,SMOC14,SMOC15}

    private Integer eventNumber;

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {

        eventNumber = 1;
        Integer cycle = 0;

        /* Read number of cycles */
        Integer numberOfCycles = Integer.valueOf(environment.getProperty("loadbalancer.cycles"));

        /* Read number of replicas. Events will be sent to these smocs */
        Integer numberOfReplicas = Integer.valueOf(environment.getProperty("loadbalancer.replicas"));

        // iterate over enums using for loop
        while(cycle < numberOfCycles) {
            logger.info("...Starting cycle {}...",cycle);
            for (Events event : Events.values()) {
                Hosts host = Hosts.values()[new Random().nextInt(numberOfReplicas)];
                logger.info("Sending {}.event which is __{}__ to __{}__", eventNumber, event.toString(), host.toString());
                String ckpt = sender.send(eventNumber, host.toString(), event.toString());
                /* Store CKPT information which is received from smoc */
                inMemoryStore.persist(ckpt);
                /*Calculate new event number*/
                eventNumber = eventNumber + 1;
            }
            cycle = cycle + 1;
        }



    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
