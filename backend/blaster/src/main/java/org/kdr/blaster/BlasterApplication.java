package org.kdr.blaster;

import org.kdr.blaster.config.AwsCredentialsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlasterApplication {

	public static void main(String[] args) {
		AwsCredentialsConfig.loadEnv();
		SpringApplication.run(BlasterApplication.class, args);
	}

}
