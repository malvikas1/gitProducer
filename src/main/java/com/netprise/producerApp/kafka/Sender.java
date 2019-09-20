package com.netprise.producerApp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import com.netprise.producerApp.model.TemperatureInfo;

public class Sender {
	
	@Value("${kafka.topic-name}")
	private String topic_name;
	
	@Autowired
	private KafkaTemplate<String, TemperatureInfo> kafkaTemplate;
	
	public void send(TemperatureInfo tempInfo) {
		kafkaTemplate.send(topic_name, tempInfo);
	}

}
