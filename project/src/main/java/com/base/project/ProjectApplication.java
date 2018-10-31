package com.base.project;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/v1/main")
public class ProjectApplication {
    private static final Logger logger = LogManager.getLogger(ProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		logger.info("ProjectApplication has bean start...");
	}
	
    @RequestMapping("/test")
	public String getMsg() {
        logger.info("hello world");
        BigDecimal num = new BigDecimal(0);
        for(int i = 0; i < 200; i++)
            num.add(new BigDecimal(1));
        return "hello world" + num.longValue();
	}
}
