package com.finguard.notification.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.finguard.notification.dto.LoanApprovedEvent;
import com.finguard.notification.dto.LoanRejectedEvent;
import com.finguard.notification.dto.UserRegisteredEvent;

@Configuration
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String, UserRegisteredEvent> consumerFactory() {

		JsonDeserializer<UserRegisteredEvent> deserializer = new JsonDeserializer<>(UserRegisteredEvent.class);
		deserializer.addTrustedPackages("*");

		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

	@Bean
	public ConsumerFactory<String, LoanApprovedEvent> loanConsumerFactory() {

		JsonDeserializer<LoanApprovedEvent> deserializer = new JsonDeserializer<>(LoanApprovedEvent.class);
		deserializer.addTrustedPackages("*");

		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, LoanApprovedEvent> loanKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, LoanApprovedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(loanConsumerFactory());

		return factory;
	}

	@Bean
	public ConsumerFactory<String, LoanRejectedEvent> loanRejectedConsumerFactory() {

		JsonDeserializer<LoanRejectedEvent> deserializer = new JsonDeserializer<>(LoanRejectedEvent.class);
		deserializer.addTrustedPackages("*");

		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, LoanRejectedEvent> loanRejectedKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, LoanRejectedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(loanRejectedConsumerFactory());

		return factory;
	}

}