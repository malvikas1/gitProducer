package com.netprise.producerApp.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netprise.producerApp.kafka.Sender;
import com.netprise.producerApp.model.TemperatureInfo;

@Controller
public class KafkaProducerController {
	
	
	public static final int MIN_TEMP = 30;
	
	public static final int MAX_TEMP = 80;
	
	public static int counter = 1;
	
	@Autowired
	private Sender sender;
	
	@RequestMapping("/")
	public String startProducer() {
		
		for(int i = 0; i < 200 ; i++) {
			pushTempInfoInKafka();
			
			//Introducing a delay
			try
			{
			    Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
			    Thread.currentThread().interrupt();
			}
		}
		
		return "Producer started";
		
	}
	
	private void pushTempInfoInKafka() {
		TemperatureInfo ti = new TemperatureInfo(counter++, getRandomNumberInRange(), LocalDateTime.now());
		this.sender.send(ti);
	}

	@SuppressWarnings("unused")
	private static int getRandomNumberInRange() {
		try {
			if (MIN_TEMP >= MAX_TEMP) {
				throw new IllegalArgumentException("max must be greater than min");
			}

			Random r = new Random();
			return r.nextInt((MAX_TEMP - MIN_TEMP) + 1) + MIN_TEMP;
		}
		catch(Exception e) {
			System.out.println("Issue in random temperature generation");
		}
		
		return 55;
	}
}
