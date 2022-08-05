package com.example.service;

import com.example.helper.exception.ResourceNotFoundException;
import com.example.model.Book;
import com.example.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceTest {
    static PodamFactory factory = new PodamFactoryImpl();
    private static List<Book> listOfMockBooks;
    private static Book mockBook;

    /**
     * The service that we want to test.
     */
    @Autowired
    private BookService service;

    /**
     * A mock version of the BookRepository for use in our tests.
     */
    @MockBean
    private BookRepository repository;

    @BeforeAll
    static void init() {
        // create mock data
        listOfMockBooks = factory.manufacturePojo(List.class, Book.class);
        mockBook = factory.manufacturePojo(Book.class);

    }

    @Test
    public void findByTitle_WhenRequestedWithValidTitle_ShouldReturnItsBook() throws Exception {
        // ARRANGE
        doReturn(mockBook).when(repository).findByTitle(any());

        // ACT
        Book returnedBook = service.findByTitle(any());

        // ASSERT
        assertThat(returnedBook)
                .isNotNull()
                .isInstanceOf(Book.class)
                .isEqualTo(mockBook);
    }

    @Test
    public void findByTitle_WhenRequestedWithInvalidTitle_ShouldReturnThrowNotFoundException() {
        // ARRANGE
        doReturn(null).when(repository).findByTitle(any());

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findByTitle(any());
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void findAvailableBooks_WhenBooksAreAvailable_ShouldReturnAListOfBook() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(repository).getAvailableBooks();

        // ACT
        List<Book> availableBooks = service.findAvailableBooks();

        // ASSERT
        assertThat(availableBooks)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(listOfMockBooks.size());
    }

    @Test
    public void findAvailableBooks_WhenItReturnsNull_ShouldThrowNotFoundException() throws Exception {
        // ARRANGE
        doReturn(null).when(repository).getAvailableBooks();

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findAvailableBooks();
        }).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    public void findAvailableBooks_WhenItReturnsEmptyList_ShouldThrowNotFoundException() throws Exception {
        // ARRANGE
        doReturn(Collections.emptyList()).when(repository).getAvailableBooks();

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findAvailableBooks();
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void getBooksByUser_WhenTheyExists_ShouldReturnAListOfBook() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(repository).getCustomBooksByUser(any(), any(), any());

        // ACT
        List<Book> availableBooks = service.getBooksByUser(any(), any(), any());

        // ASSERT
        assertThat(availableBooks)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(listOfMockBooks.size());
    }

    @Test
    public void getBooksByUser_WhenItReturnsNull_ShouldThrowNotFoundException() throws Exception {
        // ARRANGE
        doReturn(null).when(repository).getAvailableBooks();

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findAvailableBooks();
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void getBooksByUser_WhenItReturnsEmpty_ShouldThrowNotFoundException() throws Exception {
        // ARRANGE
        doReturn(Collections.emptyList()).when(repository).getAvailableBooks();

        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            service.findAvailableBooks();
        }).isInstanceOf(ResourceNotFoundException.class);
    }
}