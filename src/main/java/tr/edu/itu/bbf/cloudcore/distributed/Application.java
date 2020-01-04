package tr.edu.itu.bbf.cloudcore.distributed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tr.edu.itu.bbf.cloudcore.distributed.ipc.EventSender;

import java.util.Random;

@ComponentScan(basePackages = {"tr.edu.itu.bbf.cloudcore.distributed"})
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private EventSender sender;

    private enum Events{Pay,Receive,StartFromScratch}

    private enum Hosts{SMOC1,SMOC2,SMOC3}

    private Integer eventNumber;

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {
        eventNumber = 0;
        // iterate over enums using for loop
        for (Events event : Events.values()) {
            Hosts host = Hosts.values()[new Random().nextInt(Hosts.values().length)];
            eventNumber = eventNumber + 1;
            logger.info("Sending {}.event which is __{}__ to __{}__", eventNumber, event.toString(), host.toString());
            sender.send(eventNumber, host.toString(), event.toString());
        }

        for (Events event : Events.values()) {
            Hosts host = Hosts.values()[new Random().nextInt(Hosts.values().length)];
            eventNumber = eventNumber + 1;
            logger.info("Sending {}.event which is __{}__ to __{}__", eventNumber, event.toString(), host.toString());
            sender.send(eventNumber, host.toString(), event.toString());
        }

        for (Events event : Events.values()) {
            Hosts host = Hosts.values()[new Random().nextInt(Hosts.values().length)];
            eventNumber = eventNumber + 1;
            logger.info("Sending {}.event which is __{}__ to __{}__", eventNumber, event.toString(), host.toString());
            sender.send(eventNumber, host.toString(), event.toString());
        }





    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
