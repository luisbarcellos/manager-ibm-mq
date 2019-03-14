package br.com.ibm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@EnableAutoConfiguration
@ComponentScan
public class MainTest {
    public static void main(String[] args) throws IOException {
        new SpringApplication(MainTest.class).run(args);
    }
}