package ua.com.shyrkov;

import org.junit.jupiter.api.*;
import ua.com.shyrkov.entity.AuthorEntity;
import ua.com.shyrkov.entity.BookEntity;
import ua.com.shyrkov.service.AuthorService;
import ua.com.shyrkov.service.BookService;
import ua.com.shyrkov.service.impl.AuthorServiceImpl;
import ua.com.shyrkov.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorServiceTest {

    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookService bookService = new BookServiceImpl();
    private static final String firstName = "Firstname";
    private static final String lastName = "Lastname";
    private static final int initSize = 10;

    @BeforeAll
    public static void init() {
        for (int i = 0; i < initSize; i++) {
            AuthorEntity author = new AuthorEntity();
            author.setFirstName(firstName + i);
            author.setLastName(lastName + i);
            authorService.create(author);
        }
        Assertions.assertFalse(authorService.findAll().isEmpty());
        Assertions.assertEquals(initSize, authorService.findAll().size());
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

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName(firstName);
        authorEntity.setLastName(lastName);
        authorService.create(authorEntity);

        int actual = authorService.findAll().size();

        Assertions.assertEquals(previous + 1, actual);
    }

    @Test
    @Order(3)
    public void findAuthorByIdTest() {
        AuthorEntity authorEntity = authorService.findById(initSize+1);
        Assertions.assertNotNull(authorEntity);
        Assertions.assertEquals(firstName, authorEntity.getFirstName());
        Assertions.assertEquals(lastName, authorEntity.getLastName());
    }

    @Test
    @Order(4)
    public void findAuthorsByBookTest() {
        List<Integer> authorIds = new ArrayList<>();
        authorIds.add(1);
        authorIds.add(2);
        authorIds.add(3);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("book name");
        bookEntity.setAuthorsIds(authorIds);
        bookService.create(bookEntity);

        List<AuthorEntity> authorsByBook = authorService.findAuthorsByBook(1);

        Assertions.assertFalse(authorsByBook.isEmpty());
        Assertions.assertEquals(3, authorsByBook.size());
    }

    @Test
    @Order(5)
    public void updateAuthorTest() {
        AuthorEntity authorEntity = authorService.findById(initSize);
        authorEntity.setFirstName(firstName + 10);
        authorEntity.setLastName(lastName + 10);
        authorService.update(authorEntity);

        AuthorEntity updated = authorService.findById(initSize);
        Assertions.assertEquals(firstName+10, updated.getFirstName());
        Assertions.assertEquals(lastName+10, updated.getLastName());
    }

    @Test
    @Order(6)
    public void deleteAuthorTest(){
        int previous = authorService.findAll().size();

        authorService.delete(1);
        int actual = authorService.findAll().size();

        Assertions.assertEquals(previous-1, actual);
    }

}
