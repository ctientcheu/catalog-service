package com.polarbookshop.catalogservice.demo;

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

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        bookRepository.saveAll(Stream.of(
            Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "TMC Production"),
            Book.of("1234567892", "Polar Journey", "Lorek Polarson", 12.90, "BNY Mellon")
        ).collect(Collectors.toList()));
    }
}
