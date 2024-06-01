package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.config.DataConfig;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project catalog-service
 * @org Cerebrau
 */
@DataJdbcTest
@Import(DataConfig.class)
@ActiveProfiles("integration")
@AutoConfigureTestDatabase(
    replace =
        AutoConfigureTestDatabase.Replace.NONE) // do not use h2 memory in test, default behavior
class BookRepositoryJdbcTests {
  @Autowired private BookRepository bookRepository;
  @Autowired private JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  void findBookByIsbnWhenExisting() {
    var bookIsbn = "1114561237";
    var book = Book.of(bookIsbn, "Title", "Author", 12.90, "TMC Inc");
    jdbcAggregateTemplate.insert(book);

    Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

    assertThat(actualBook).isPresent();
    assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
  }

  @Test
  void whenCreateBookNotAuthenticatedThenNoAuditMetadata() {
    var bookToCreate = Book.of("4444567891", "Title", "Author", 12.90, "TMC Inc");
    var createdBook = bookRepository.save(bookToCreate);

    assertThat(createdBook.createdBy()).isNull();
    assertThat(createdBook.lastModifiedBy()).isNull();
  }

  @Test
  @WithMockUser("john")
  void whenCreateBookAuthenticatedThenAuditMetadata() {
    var bookToCreate = Book.of("3334567892", "Title", "Author", 13.90, "TMC Inc");
    var createdBook = bookRepository.save(bookToCreate);

    assertThat(createdBook.createdBy()).isEqualTo("john");
    assertThat(createdBook.lastModifiedBy()).isEqualTo("john");
  }
}
