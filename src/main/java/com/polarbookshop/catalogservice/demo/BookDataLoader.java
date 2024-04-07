package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.config.properties.PolarProperties;
import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project catalog-service
 * @org Cerebrau
 */
public class BookDataLoader {
    private final BookRepository bookRepository;
    private final PolarProperties polarProperties;

    public BookDataLoader(BookRepository bookRepository, PolarProperties polarProperties) {
        this.bookRepository = bookRepository;
        this.polarProperties = polarProperties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        if (polarProperties.testData().isEnable()) {
            if (bookRepository.count() == 0 || polarProperties.testData().doOverride()) {
                bookRepository.deleteAll();
                generateData();
            }
        }
    }

    private void generateData() {
        bookRepository.saveAll(Stream.of(
            Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "TMC Production"),
            Book.of("1234567892", "Polar Journey", "Lorek Polarson", 12.90, "BNY Mellon")
        ).collect(Collectors.toList()));
    }
}
