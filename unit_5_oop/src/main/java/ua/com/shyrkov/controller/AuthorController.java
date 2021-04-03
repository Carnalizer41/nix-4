package ua.com.shyrkov.controller;

import ua.com.shyrkov.entity.AuthorEntity;
import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.AuthorService;
import ua.com.shyrkov.service.ConsoleHelperService;
import ua.com.shyrkov.service.impl.AuthorServiceImpl;

import java.util.List;

public class AuthorController {

    private final AuthorService authorService = new AuthorServiceImpl();
    private final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public void create(){
        AuthorEntity authorEntity = new AuthorEntity();
        consoleHelper.printMessage("Enter author`s first name:");
        authorEntity.setFirstName(consoleHelper.readStringFromConsole());
        consoleHelper.printMessage("Enter author`s last name:");
        authorEntity.setLastName(consoleHelper.readStringFromConsole());
        authorService.create(authorEntity);
    }

    public void findAuthorById(){
        consoleHelper.printMessage("Enter author`s id:");
        int id = consoleHelper.readIntegerFromConsole();
        AuthorEntity byId = authorService.findById(id);
        System.out.println("Author: "+byId.toString());
    }

    public void findAllAuthors(){
        List<AuthorEntity> all = authorService.findAll();
        consoleHelper.printMessage("All authors list:");
        for (AuthorEntity authorEntity : all) {
            consoleHelper.printMessage(authorEntity.toString());
        }
    }

    public void findAuthorByBookId(){
        consoleHelper.printMessage("Enter book id to find it`s authors:");
        int id = consoleHelper.readIntegerFromConsole();
        List<AuthorEntity> authorsByBook = authorService.findAuthorsByBook(id);
        consoleHelper.printMessage("Book`s authors list:");
        for (AuthorEntity authorEntity : authorsByBook) {
            consoleHelper.printMessage(authorEntity.toString());
        }
    }

    public void updateAuthor(){
        consoleHelper.printMessage("Enter author`s id you want to update:");
        int id = consoleHelper.readIntegerFromConsole();
        AuthorEntity authorEntity = authorService.findById(id);
        consoleHelper.printMessage("Enter first and last name you want to set:");
        String[] name = consoleHelper.readStringFromConsole().split("\\s+");
        authorEntity.setFirstName(name[0]);
        authorEntity.setLastName(name[1]);
        authorService.update(authorEntity);
        consoleHelper.printMessage("You updated author`s info to "+authorEntity.toString());
    }

    public void deleteAuthor(){
        consoleHelper.printMessage("Enter author`s id you want to delete:");
        int id = consoleHelper.readIntegerFromConsole();
        AuthorEntity authorEntity = authorService.findById(id);
        authorService.delete(id);
        consoleHelper.printMessage("You deleted author "+authorEntity.toString());
    }
}
