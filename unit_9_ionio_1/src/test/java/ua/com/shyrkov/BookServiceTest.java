package ua.com.shyrkov;

import lombok.NonNull;
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
public class BookServiceTest {

    private static final String authorFilePath = "authors.csv";
    private static final String bookFilePath = "books.csv";
    private static final AuthorService authorService = AuthorServiceImpl.getInstance(authorFilePath, bookFilePath);
    private static final BookService bookService = BookServiceImpl.getInstance(authorFilePath, bookFilePath);
    private static final String name = "Book Title";
    private static final int initSize = 10;

    @BeforeAll
    public static void init() {
        authorService.create(new AuthorEntity("FName", "LName", new ArrayList<>()));
        List<AuthorEntity> all = authorService.findAll();
        Integer id = all.get(all.size() - 1).getId();
        for (int i = 0; i < initSize; i++) {
            BookEntity book = new BookEntity(name + (i + 1), new ArrayList<>());
            book.addAuthorId(id);
            bookService.create(book);
        }
        Assertions.assertFalse(bookService.findAll().isEmpty());
        Assertions.assertEquals(initSize, bookService.findAll().size());
        Assertions.assertEquals(initSize, authorService.findById(1).getBookIds().size());
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

        BookEntity bookEntity = new BookEntity(name, new ArrayList<>());
        bookService.create(bookEntity);

        int actual = bookService.findAll().size();

        Assertions.assertEquals(previous + 1, actual);
    }

    @Test
    @Order(3)
    public void findBookByIdTest() {
        BookEntity bookEntity = bookService.findById(initSize + 1);
        Assertions.assertNotNull(bookEntity);
        Assertions.assertEquals(name, bookEntity.getTitle());
        Assertions.assertEquals(true, bookEntity.getIsActive());
    }

    @Test
    @Order(4)
    public void findBookByAuthorTest() {

        List<BookEntity> booksByAuthor = bookService.findBooksByAuthorId(1);

        Assertions.assertFalse(booksByAuthor.isEmpty());
        Assertions.assertEquals(initSize, booksByAuthor.size());
    }

    @Test
    @Order(5)
    public void updateBookTest() {
        BookEntity bookEntity = bookService.findById(initSize + 1);
        bookEntity.setTitle(name + 10);
        bookService.update(bookEntity);

        BookEntity updated = bookService.findById(initSize+1);
        Assertions.assertEquals(name + 10, updated.getTitle());
    }

    @Test
    @Order(6)
    public void deleteBookTest() {
        int previous = bookService.findAll().size();

        bookService.delete(1);
        int actual = bookService.findAll().size();

        Assertions.assertEquals(previous - 1, actual);
    }
}
