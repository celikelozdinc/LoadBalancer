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

    @Value("${EVENT_EXCHANGE_SMOC4}")
    private String EVENT_EXCHANGE_SMOC4;

    @Value("${EVENT_EXCHANGE_SMOC5}")
    private String EVENT_EXCHANGE_SMOC5;

    @Value("${EVENT_EXCHANGE_SMOC6}")
    private String EVENT_EXCHANGE_SMOC6;

    @Value("${EVENT_EXCHANGE_SMOC7}")
    private String EVENT_EXCHANGE_SMOC7;


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

    @Bean
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


}
