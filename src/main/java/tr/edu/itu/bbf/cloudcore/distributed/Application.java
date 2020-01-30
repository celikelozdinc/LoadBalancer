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

    //private enum Hosts{SMOC1,SMOC2,SMOC3}
    private enum Hosts{SMOC1,SMOC2,SMOC3,SMOC4,SMOC5,SMOC6,SMOC7}
    //private enum Hosts{SMOC1,SMOC2,SMOC3,SMOC4,SMOC5,SMOC6,SMOC7,SMOC8,SMOC9,SMOC10,SMOC11}
    //private enum Hosts{SMOC1,SMOC2,SMOC3,SMOC4,SMOC5,SMOC6,SMOC7,SMOC8,SMOC9,SMOC10,SMOC11,SMOC12,SMOC13,SMOC14,SMOC15}

    private Integer eventNumber;

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {
        /*
        sender.send(1,Hosts.SMOC1.toString(),Events.Pay.toString());
        sender.send(2,Hosts.SMOC1.toString(),Events.Receive.toString());
        sender.send(3,Hosts.SMOC2.toString(),Events.StartFromScratch.toString());
        sender.send(4,Hosts.SMOC2.toString(),Events.Pay.toString());
        sender.send(5,Hosts.SMOC3.toString(),Events.Receive.toString());
        sender.send(6,Hosts.SMOC3.toString(),Events.StartFromScratch.toString());
        sender.send(7,Hosts.SMOC1.toString(),Events.Pay.toString());
        sender.send(8,Hosts.SMOC2.toString(),Events.Receive.toString());
        sender.send(9,Hosts.SMOC3.toString(),Events.StartFromScratch.toString());
        sender.send(10,Hosts.SMOC3.toString(),Events.Pay.toString());
        sender.send(11,Hosts.SMOC2.toString(),Events.Receive.toString());
        sender.send(12,Hosts.SMOC1.toString(),Events.StartFromScratch.toString());
        sender.send(13,Hosts.SMOC3.toString(),Events.Pay.toString());
        sender.send(14,Hosts.SMOC2.toString(),Events.Receive.toString());
        sender.send(15,Hosts.SMOC1.toString(),Events.StartFromScratch.toString());
        sender.send(16,Hosts.SMOC1.toString(),Events.Pay.toString());
        sender.send(17,Hosts.SMOC2.toString(),Events.Receive.toString());
        sender.send(18,Hosts.SMOC3.toString(),Events.StartFromScratch.toString());
        */

        eventNumber = 1;
        Integer cycle = 0;

        // iterate over enums using for loop
        while(cycle < 3200) {
            logger.info("...Starting cycle {}...",cycle);
            for (Events event : Events.values()) {
                Hosts host = Hosts.values()[new Random().nextInt(Hosts.values().length)];
                logger.info("Sending {}.event which is __{}__ to __{}__", eventNumber, event.toString(), host.toString());
                sender.send(eventNumber, host.toString(), event.toString());
                eventNumber = eventNumber + 1;
            }
            cycle = cycle + 1;
        }



    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
