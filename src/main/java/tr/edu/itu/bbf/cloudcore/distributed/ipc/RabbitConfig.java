package tr.edu.itu.bbf.cloudcore.distributed.ipc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

    /* Add exchanges for other smocs */

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

}
