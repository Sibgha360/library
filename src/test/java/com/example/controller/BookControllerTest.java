package com.example.controller;

import com.example.SlashyDateConverter;
import com.example.helper.exception.BadRequestException;
import com.example.model.Book;
import com.example.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookControllerTest {
    static PodamFactory factory = new PodamFactoryImpl();
    private static List<Book> listOfMockBooks;

    /**
     * The service that we want to test.
     */
    @Autowired
    private LibraryController controller;

    /**
     * A mock version of the BookRepository for use in our tests.
     */
    @MockBean
    private BookService service;

    @BeforeAll
    static void init() {
        // create mock data
        listOfMockBooks = factory.manufacturePojo(List.class, Book.class);
    }

    @Test
    public void getAvailableBooks_WhenAvailableBooksExist_ShouldReturnTheListOfBooksAndSucessResponse() throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(service).findAvailableBooks();

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getAvailableBooks();

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().hasSize(listOfMockBooks.size()));
    }

    @ParameterizedTest
    @CsvSource({"1, 2018/11/25, 2020/12/25",
            "12, 2018/10/25, 2018/12/25",
            "20, 2018/07/25, 2019/12/25"})
    public void getBooksByUser_WithValidArguments_ShouldReturnTheListOfBooksAndSucessResponse(Integer userId, @ConvertWith(SlashyDateConverter.class) LocalDate from, @ConvertWith(SlashyDateConverter.class) LocalDate to) throws Exception {
        // ARRANGE
        doReturn(listOfMockBooks).when(service).getBooksByUser(any(), any(), any());

        // ACT
        ResponseEntity<List<Book>> responseEntity = controller.getBooksByBorrower(userId, from, to);

        // ASSERT
        assertThat(responseEntity)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class)
                .satisfies(status -> assertThat(status.getStatusCode()).isNotNull()
                        .satisfies(code -> assertThat(code)).isEqualTo(HttpStatus.OK))
                .satisfies(body -> assertThat(body.getBody()).isNotNull().hasSize(listOfMockBooks.size()));
    }


    @ParameterizedTest
    @CsvSource({"0, 2018/11/25, 2020/12/25",
            "-1, 2018/10/25, 2018/12/25",
            "12, 2019/07/25, 2018/12/25"})
    public void getBooksByUser_WithInvalidArguments_ShouldThrowBadRequestException(Integer userId, @ConvertWith(SlashyDateConverter.class) LocalDate from, @ConvertWith(SlashyDateConverter.class) LocalDate to) throws Exception {
        // ASSERT
        assertThatThrownBy(() -> {
            // ACT
            controller.getBooksByBorrower(userId, from, to);
        }).isInstanceOf(BadRequestException.class);
    }
}

