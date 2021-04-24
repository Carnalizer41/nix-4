package ua.com.shyrkov;

import org.junit.jupiter.api.*;
import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.AuthorService;
import ua.com.shyrkov.service.BookService;
import ua.com.shyrkov.service.impl.AuthorServiceImpl;
import ua.com.shyrkov.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorServiceTest {

    private static final String authorFilePath = "authors1.csv";
    private static final String bookFilePath = "books1.csv";
    private static final AuthorService authorService = AuthorServiceImpl.getInstance(authorFilePath, bookFilePath);
    private static final BookService bookService = BookServiceImpl.getInstance(authorFilePath, bookFilePath);
    private static final String firstName = "Firstname";
    private static final String lastName = "Lastname";
    private static final int initSize = 10;

    @BeforeAll
    public static void init() {
        bookService.create(new BookEntity("Title", new ArrayList<>()));
        List<BookEntity> all = bookService.findAll();
        Integer id = all.get(all.size() - 1).getId();
        for (int i = 0; i < initSize; i++) {
            AuthorEntity author = new AuthorEntity(firstName + (i + 1), lastName + (i + 1), new ArrayList<>());
            author.addBookId(id);
            authorService.create(author);
        }
        Assertions.assertFalse(authorService.findAll().isEmpty());
        Assertions.assertEquals(initSize, authorService.findAll().size());
        Assertions.assertEquals(initSize, bookService.findById(1).getAuthorIds().size());
    }

    @Test
    @Order(1)
    public void findAllAuthorsTest() {
        List<AuthorEntity> all = authorService.findAll();

        Assertions.assertFalse(all.isEmpty());
        Assertions.assertEquals(initSize, all.size());
    }

    @Test
    @Order(2)
    public void createAuthorTest() {
        int previous = authorService.findAll().size();

        AuthorEntity authorEntity = new AuthorEntity(firstName, lastName, new ArrayList<>());
        authorService.create(authorEntity);

        int actual = authorService.findAll().size();

        Assertions.assertEquals(previous + 1, actual);
    }

    @Test
    @Order(3)
    public void findAuthorByIdTest() {
        AuthorEntity authorEntity = authorService.findById(initSize + 1);
        Assertions.assertNotNull(authorEntity);
        Assertions.assertEquals(firstName, authorEntity.getFirstName());
        Assertions.assertEquals(lastName, authorEntity.getLastName());
        Assertions.assertEquals(true, authorEntity.getIsActive());
    }

    @Test
    @Order(4)
    public void findAuthorsByBookTest() {

        List<AuthorEntity> authorsByBook = authorService.findAuthorsByBookId(1);

        Assertions.assertFalse(authorsByBook.isEmpty());
        Assertions.assertEquals(initSize, authorsByBook.size());
    }

    @Test
    @Order(5)
    public void updateAuthorTest() {
        AuthorEntity authorEntity = authorService.findById(initSize+1);
        authorEntity.setFirstName(firstName + 10);
        authorEntity.setLastName(lastName + 10);
        authorService.update(authorEntity);

        AuthorEntity updated = authorService.findById(initSize+1);
        Assertions.assertEquals(firstName + 10, updated.getFirstName());
        Assertions.assertEquals(lastName + 10, updated.getLastName());
    }

    @Test
    @Order(6)
    public void deleteAuthorTest() {
        int previous = authorService.findAll().size();

        authorService.delete(1);
        int actual = authorService.findAll().size();

        Assertions.assertEquals(previous - 1, actual);
    }

}
