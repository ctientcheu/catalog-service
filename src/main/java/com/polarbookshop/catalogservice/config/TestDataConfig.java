package com.polarbookshop.catalogservice.config;

import com.polarbookshop.catalogservice.config.properties.PolarProperties;
import com.polarbookshop.catalogservice.demo.BookDataLoader;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project catalog-service
 * @org Cerebrau
 */
@Configuration
@ConditionalOnProperty(name = "polar.test-data.enable", havingValue = "true")
public class TestDataConfig {
    private final BookRepository bookRepository;
    private final PolarProperties polarProperties;

    public TestDataConfig(BookRepository bookRepository, PolarProperties polarProperties) {
        this.bookRepository = bookRepository;
        this.polarProperties = polarProperties;
    }

    @Bean
    public BookDataLoader bookDataLoader() {
        return new BookDataLoader(bookRepository, polarProperties);
    }
}
