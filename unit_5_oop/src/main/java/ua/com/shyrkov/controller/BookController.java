package ua.com.shyrkov.controller;

import ua.com.shyrkov.entity.AuthorEntity;
import ua.com.shyrkov.entity.BookEntity;
import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.BookService;
import ua.com.shyrkov.service.ConsoleHelperService;
import ua.com.shyrkov.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final BookService bookService = new BookServiceImpl();
    private final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public void create(){
        BookEntity bookEntity = new BookEntity();

        consoleHelper.printMessage("Enter book`s title:");
        bookEntity.setName(consoleHelper.readStringFromConsole());
        consoleHelper.printMessage("Enter amount of book`s authors:");
        int amount = consoleHelper.readIntegerFromConsole();

        List<Integer> authIds = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            consoleHelper.printMessage("Enter "+i+" author`s id:");
            int id = consoleHelper.readIntegerFromConsole();
            authIds.add(id);
        }
        bookEntity.setAuthorsIds(authIds);

        bookService.create(bookEntity);
    }

    public void findBookById(){
        consoleHelper.printMessage("Enter book`s id:");
        int id = consoleHelper.readIntegerFromConsole();
        BookEntity byId = bookService.findById(id);
        System.out.println("Book: "+byId.toString());
    }

    public void findAllBooks(){
        List<BookEntity> all = bookService.findAll();
        consoleHelper.printMessage("All books list:");
        for (BookEntity bookEntity : all) {
            consoleHelper.printMessage(bookEntity.toString());
        }
    }

    public void findBooksByAuthorId(){
        consoleHelper.printMessage("Enter author id to find his books:");
        int id = consoleHelper.readIntegerFromConsole();
        List<BookEntity> booksByAuthor = bookService.findBooksByAuthor(id);
        consoleHelper.printMessage("Author`s books list:");
        for (BookEntity bookEntity : booksByAuthor) {
            consoleHelper.printMessage(bookEntity.toString());
        }
    }

    public void updateBook(){
        consoleHelper.printMessage("Enter book`s id you want to update:");
        int id = consoleHelper.readIntegerFromConsole();
        BookEntity bookEntity = bookService.findById(id);
        consoleHelper.printMessage("Enter title you want to set:");
        String name = consoleHelper.readStringFromConsole();
        bookEntity.setName(name);
        bookService.update(bookEntity);
        consoleHelper.printMessage("You updated book`s info to "+bookEntity.toString());
    }

    public void deleteBook(){
        consoleHelper.printMessage("Enter book`s id you want to delete:");
        int id = consoleHelper.readIntegerFromConsole();
        BookEntity bookEntity = bookService.findById(id);
        bookService.delete(id);
        consoleHelper.printMessage("You deleted book "+bookEntity.toString());
    }
}
