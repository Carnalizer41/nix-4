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
public class BookServiceTest {

    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final BookService bookService = new BookServiceImpl();
    private static final String name = "book name";
    private static final int initSize = 10;

    @BeforeAll
    public static void init() {
        for (int i = 0; i < initSize; i++) {
            BookEntity book = new BookEntity();
            List<Integer> authIds = new ArrayList<>();
            authIds.add(i+1);
            book.setName(name + i);
            book.setAuthorsIds(authIds);
            bookService.create(book);
        }
        Assertions.assertFalse(bookService.findAll().isEmpty());
        Assertions.assertEquals(initSize, bookService.findAll().size());
    }

    @Test
    @Order(1)
    public void findAllBooksTest() {
        List<BookEntity> all = bookService.findAll();

        Assertions.assertFalse(all.isEmpty());
        Assertions.assertEquals(initSize, all.size());
    }

    @Test
    @Order(2)
    public void createBookTest() {
        int previous = bookService.findAll().size();

        List<Integer> authIds = new ArrayList<>();
        authIds.add(1);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(name);
        bookEntity.setAuthorsIds(authIds);
        bookService.create(bookEntity);

        int actual = bookService.findAll().size();

        Assertions.assertEquals(previous + 1, actual);
    }

    @Test
    @Order(3)
    public void findBookByIdTest() {
        BookEntity bookEntity = bookService.findById(initSize+1);
        Assertions.assertNotNull(bookEntity);
        Assertions.assertEquals(name, bookEntity.getName());
    }

    @Test
    @Order(4)
    public void findBookByAuthorTest() {

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName("first");
        authorEntity.setLastName("last");
        authorService.create(authorEntity);

        List<BookEntity> booksByAuthor = bookService.findBooksByAuthor(1);

        Assertions.assertFalse(booksByAuthor.isEmpty());
        Assertions.assertEquals(2, booksByAuthor.size());
    }

    @Test
    @Order(5)
    public void updateAuthorTest() {
        BookEntity bookEntity = bookService.findById(initSize);
        bookEntity.setName(name + 10);
        bookService.update(bookEntity);

        BookEntity updated = bookService.findById(initSize);
        Assertions.assertEquals(name+10, updated.getName());
    }

    @Test
    @Order(6)
    public void deleteAuthorTest(){
        int previous = bookService.findAll().size();

        bookService.delete(1);
        int actual = bookService.findAll().size();

        Assertions.assertEquals(previous-1, actual);
    }
}
