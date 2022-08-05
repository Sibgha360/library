package com.example.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.helper.exception.ResourceNotFoundException;
import com.example.model.Book;
import com.example.model.GenderEnum;
import com.example.model.User;
import com.example.model.UserHasBook;
import com.example.service.BookService;
import com.example.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Title", "Author", "Genre", "Published"};
    public static String preDefinedDateFormat = "MM/dd/yyyy";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(preDefinedDateFormat)
            .withLocale(Locale.GERMANY);

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }


    public static List<UserHasBook> csvToUserHasBook(InputStream is, UserService userService, BookService bookService) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<UserHasBook> usersHasBooks = new ArrayList<>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            User borrower = getUser(userService, csvRecord.get("Borrower"));

            if (borrower == null) {
                System.out.println("Skipping the entry with user: " + csvRecord.get("Borrower") + ". Format of the name should be firstname,lastname");
                continue;
            }

            UserHasBook userHasBook = new UserHasBook(
                    getBook(bookService, csvRecord.get("Book")),
                    borrower,
                    LocalDate.parse(csvRecord.get("borrowed from"), formatter),
                    LocalDate.parse(csvRecord.get("borrowed to"), formatter)
            );
            usersHasBooks.add(userHasBook);
        }
        return usersHasBooks;
    }

    private static Book getBook(BookService bookService, String title) throws Exception {
        Book book = null;
        try {
            book = bookService.findByTitle(title);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Book with title " + title + " not found. Tip: Import Books Data");
        }
        return book;
    }

    private static User getUser(UserService userService, String borrower) throws ResourceNotFoundException {
        User byFirstNameLastName = null;

        String[] split = borrower.split(",");
        if (split.length != 2) {
            return null;
        }
        String firstName = split[0];
        String lastName = split[1];


        byFirstNameLastName = userService.findByFirstNameLastName(firstName, lastName);

        if (byFirstNameLastName == null) {
            throw new ResourceNotFoundException("User with name " + borrower + " not found. Tip: Import Users Data");
        }
        return byFirstNameLastName;
    }

    public static List<Book> csvToBooks(InputStream is) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<Book> books = new ArrayList<>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            String title = csvRecord.get("Title");
            String author = csvRecord.get("Author");
            String genre = csvRecord.get("Genre");
            String publisher = csvRecord.get("Publisher");
            if (!validateParam(title) && !validateParam(title) && !validateParam(title) && !validateParam(title)) {
                System.out.println("Skipping entry. parameter missing");
                continue;
            }

            Book book = new Book(
                    title,
                    author,
                    genre,
                    publisher
            );
            books.add(book);
        }
        return books;

    }


    private static CSVParser getCsvParser(InputStream is) throws IOException {
        CSVParser csvParser = null;

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        return csvParser;
    }


    public static List<User> csvToUsers(InputStream is) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<User> users = new ArrayList<>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();


        for (CSVRecord csvRecord : csvRecords) {

            String name = csvRecord.get("Name");
            String firstName = csvRecord.get("First Name");
            String memberSince = csvRecord.get("Member since");
            String memberTill = csvRecord.get("Member till");
            String gender = csvRecord.get("gender");

            if (!validateParam(name) && !validateParam(firstName) && !validateParam(memberSince) && !validateParam(memberTill) && !validateParam(gender)) {
                System.out.println("Skipping entry. parameter missing");
                continue;
            }

            User user = new User(
                    name,
                    firstName,
                    LocalDate.parse(memberSince, formatter),
                    memberTill.isEmpty() || memberTill.equals("") ? null : LocalDate.parse(memberTill, formatter),
                    getGenderEnum(gender)
            );

            users.add(user);
        }
        return users;
    }

    private static boolean validateParam(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }

        return true;
    }

    private static GenderEnum getGenderEnum(String gender) {
        GenderEnum genderEnum;

        if (gender.equals("m")) {
            genderEnum = GenderEnum.Male;
        } else if (gender.equals("f")) {
            genderEnum = GenderEnum.Female;
        } else {
            genderEnum = GenderEnum.Unspecified;
        }
        return genderEnum;
    }
}
