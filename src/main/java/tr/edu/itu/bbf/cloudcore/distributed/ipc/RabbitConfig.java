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

    @Value("${LB_EXCHANGE}")
    private String LB_EXCHANGE;

    @Value("${LB_QUEUE}")
    private String LB_QUEUE;

    @Value("${EVENT_EXCHANGE_SMOC4}")
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
    private String EVENT_EXCHANGE_SMOC15;

    @Value("${EVENT_EXCHANGE_SMOC16}")
    private String EVENT_EXCHANGE_SMOC16;

    @Value("${EVENT_EXCHANGE_SMOC17}")
    private String EVENT_EXCHANGE_SMOC17;

    @Value("${EVENT_EXCHANGE_SMOC18}")
    private String EVENT_EXCHANGE_SMOC18;

    @Value("${EVENT_EXCHANGE_SMOC19}")
    private String EVENT_EXCHANGE_SMOC19;

    @Value("${EVENT_EXCHANGE_SMOC20}")
    private String EVENT_EXCHANGE_SMOC20;

    @Value("${EVENT_EXCHANGE_SMOC21}")
    private String EVENT_EXCHANGE_SMOC21;

    @Value("${EVENT_EXCHANGE_SMOC22}")
    private String EVENT_EXCHANGE_SMOC22;

    @Value("${EVENT_EXCHANGE_SMOC23}")
    private String EVENT_EXCHANGE_SMOC23;

    @Value("${EVENT_EXCHANGE_SMOC24}")
    private String EVENT_EXCHANGE_SMOC24;



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
    }

    @Bean
    DirectExchange smoc16Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC16);
    }

    @Bean
    DirectExchange smoc17Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC17);
    }

    @Bean
    DirectExchange smoc18Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC18);
    }

    @Bean
    DirectExchange smoc19Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC19);
    }

    @Bean
    DirectExchange smoc20Exchange() {
        return new DirectExchange(EVENT_EXCHANGE_SMOC20);
    }

    @Bean
    DirectExchange smoc21Exchange() { return new DirectExchange(EVENT_EXCHANGE_SMOC21); }

    @Bean
    DirectExchange smoc22Exchange() { return new DirectExchange(EVENT_EXCHANGE_SMOC22); }

    @Bean
    DirectExchange smoc23Exchange() { return new DirectExchange(EVENT_EXCHANGE_SMOC23); }

    @Bean
    DirectExchange smoc24Exchange() { return new DirectExchange(EVENT_EXCHANGE_SMOC24); }



    @Bean
    Queue LbQueue() {
        return new Queue(LB_QUEUE, false);
    }

    @Bean
    DirectExchange LbExchange() {
        return new DirectExchange(LB_EXCHANGE);
    }

    @Bean
    Binding Lbbinding(Queue LbQueue, DirectExchange LbExchange) {
        return BindingBuilder.bind(LbQueue).to(LbExchange).with("rpc");
    }

}
