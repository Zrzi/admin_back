package com.admin.admin_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 陈群矜
 */
@SpringBootApplication
@ServletComponentScan
public class AdminBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBackApplication.class, args);
    }

}
