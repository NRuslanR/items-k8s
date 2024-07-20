package org.example.items_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@PropertySource("classpath:integration-services.properties")
public class ItemsK8sServiceApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(ItemsK8sServiceApplication.class, args);
	}
}
