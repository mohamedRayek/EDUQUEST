package com.platform.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
@EnableWebSecurity
public class PlatformApplication {

	public static void main(String[] args) {

		// هذه الدالة مؤقتة لإنشاء المفتاح
        try {
            generateJwtSecretKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SpringApplication.run(PlatformApplication.class, args);


	}

	private static void generateJwtSecretKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
		keyGen.init(512);
		SecretKey secretKey = keyGen.generateKey();
		String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println("JWT Secret Key: " + base64Key);
	}

}
