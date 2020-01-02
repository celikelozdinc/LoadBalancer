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

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {
        // iterate over enums using for loop
        Integer counter = 0;
        while( counter<5) {
            logger.info(".....{}th iteration of loadbalancer.....",counter);
            for (Events event : Events.values()) {
                Hosts host = Hosts.values()[new Random().nextInt(Hosts.values().length)];
                logger.info("Sending event __{}__ to smoc __{}__", event.toString(), host.toString());
                sender.send(host.toString(), event.toString());
            }

            counter ++;
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
