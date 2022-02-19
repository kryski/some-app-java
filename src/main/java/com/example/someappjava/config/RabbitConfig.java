package com.example.someappjava.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String RABBIT_LISTENER_CONTAINER_FACTORY = "rabbitListenerContainerFactory";

    @Bean
    public MessageConverter messageConverter(final ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, final MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean(RABBIT_LISTENER_CONTAINER_FACTORY)
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> rabbitListenerContainerFactory(
            final ConnectionFactory connectionFactory,
            final MessageConverter messageConverter) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);

        return factory;
    }

    @Bean
    public Queue articleCreateQueue(@Value("${articles.rabbit.queue}") final String queueName) {
        return new Queue(queueName);
    }

    @Bean
    public Exchange articlesExchange(@Value("${articles.rabbit.exchange}") final String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding articlesBiding(final Queue articleCreateQueue, final Exchange articlesExchange, @Value("articles.rabbit.routing-key}") final String routingKey) {
        return BindingBuilder
                .bind(articleCreateQueue)
                .to(articlesExchange)
                .with(routingKey)
                .noargs();
    }

    @Bean
    public AmqpAdmin amqpAdmin(
            final ConnectionFactory connectionFactory,
            final Queue articleCreateQueue,
            final Exchange articlesExchange,
            final Binding articlesBiding) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareQueue(articleCreateQueue);
        rabbitAdmin.declareExchange(articlesExchange);
        rabbitAdmin.declareBinding(articlesBiding);

        return rabbitAdmin;
    }
}
