package com.rav.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;

@Configuration
public class Config {

    @Autowired
    public Transformer transformer;

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(fileReader(),
                spec -> spec.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();
    }

    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("source"));
        return source;
    }

    @Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                new File("destination")
        );
        handler.setExpectReply(false);

        return handler;
    }
}
