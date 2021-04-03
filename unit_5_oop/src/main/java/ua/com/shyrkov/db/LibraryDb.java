package ua.com.shyrkov.db;

import lombok.NonNull;
import ua.com.shyrkov.entity.AuthorEntity;
import ua.com.shyrkov.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryDb {

    private List<AuthorEntity> authorTable = new ArrayList<>();
    private List<BookEntity> bookTable = new ArrayList<>();

    private static LibraryDb instance;

    private LibraryDb() {

    }

    public static LibraryDb getInstance() {
        if (instance == null) {
            instance = new LibraryDb();
        }
        return instance;
    }

    public void createAuthor(@NonNull AuthorEntity author) {
        int id = authorTable.size() + 1;
        author.setId(id);
        authorTable.add(author);

        System.out.println("CREATED USER: " + author.toString());
    }

    public void createBook(@NonNull BookEntity book) {
        int id = bookTable.size() + 1;
        book.setId(id);
        bookTable.add(book);

        System.out.println("CREATED BOOK" + book.toString());
    }

    public List<AuthorEntity> findAllAuthors() {
        return authorTable;
    }

    public List<BookEntity> findAllBooks() {
        return bookTable;
    }

    public AuthorEntity findAuthorById(int id) {
        return authorTable.stream()
                          .filter(author -> author.getId() == id)
                          .findFirst().get();
    }

    public BookEntity findBookById(int id) {
        return bookTable.stream()
                        .filter(author -> author.getId() == id)
                        .findFirst().get();
    }

    public List<BookEntity> findBooksByAuthorId(int authorId) {
        try {
            return bookTable.stream()
                            .filter(book -> book.getAuthorsIds().contains(authorId))
                            .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<AuthorEntity> findAuthorsByBookId(int bookId) {
        try {
            List<Integer> authorsIds = bookTable.stream()
                                                .filter(book -> book.getId() == bookId)
                                                .findFirst().get()
                                                .getAuthorsIds();

            return authorTable.stream()
                              .filter(author -> authorsIds.contains(author.getId()))
                              .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public void updateAuthor(AuthorEntity author) {
        AuthorEntity authorById = findAuthorById(author.getId());
        authorById.setFirstName(author.getFirstName());
        authorById.setLastName(author.getLastName());
    }

    public void updateBook(BookEntity book) {
        BookEntity bookById = findBookById(book.getId());
        bookById.setName(book.getName());
        bookById.setAuthorsIds(book.getAuthorsIds());
    }

    public void deleteAuthor(int authorId) {
        authorTable.removeIf(author -> author.getId() == authorId);
    }

    public void deleteBook(int bookId) {
        bookTable.removeIf(book -> book.getId() == bookId);
    }

    public boolean isAuthorExists(int id) {
        return authorTable.stream().anyMatch(author -> author.getId() == id);
    }

    public boolean isBookExists(int id) {
        return bookTable.stream().anyMatch(book -> book.getId() == id);
    }
}
