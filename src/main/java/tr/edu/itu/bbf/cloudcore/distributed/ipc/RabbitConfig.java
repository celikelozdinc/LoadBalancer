package tr.edu.itu.bbf.cloudcore.distributed.ipc;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${EVENT_EXCHANGE_SMOC1}")
    private String EVENT_EXCHANGE_SMOC1;

    @Value("${EVENT_EXCHANGE_SMOC2}")
    private String EVENT_EXCHANGE_SMOC2;

    @Value("${EVENT_EXCHANGE_SMOC3}")
    private String EVENT_EXCHANGE_SMOC3;

/*    @Value("${EVENT_EXCHANGE_SMOC4}")
    private String EVENT_EXCHANGE_SMOC4;

    @Value("${EVENT_EXCHANGE_SMOC5}")
    private String EVENT_EXCHANGE_SMOC5;

    @Value("${EVENT_EXCHANGE_SMOC6}")
    private String EVENT_EXCHANGE_SMOC6;

    @Value("${EVENT_EXCHANGE_SMOC7}")
    private String EVENT_EXCHANGE_SMOC7;

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
    private String EVENT_EXCHANGE_SMOC15;*/

    @Bean
    DirectExchange smoc1Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC1);
    }

    @Bean
    DirectExchange smoc2Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC2);
    }

    @Bean
    DirectExchange smoc3Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC3);
    }

/*    @Bean
    DirectExchange smoc4Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC4);
    }

    @Bean
    DirectExchange smoc5Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC5);
    }

    @Bean
    DirectExchange smoc6Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC6);
    }

    @Bean
    DirectExchange smoc7Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC7);
    }

    @Bean
    DirectExchange smoc8Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC8);
    }

    @Bean
    DirectExchange smoc9Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC9);
    }

    @Bean
    DirectExchange smoc10Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC10);
    }

    @Bean
    DirectExchange smoc11Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC11);
    }

    @Bean
    DirectExchange smoc12Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC12);
    }

    @Bean
    DirectExchange smoc13Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC13);
    }

    @Bean
    DirectExchange smoc14Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC14);
    }

    @Bean
    DirectExchange smoc15Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC15);
    }*/

}
