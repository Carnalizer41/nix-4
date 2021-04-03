package ua.com.shyrkov.controller;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.ConsoleHelperService;

public class MainController {

    private final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();
    private final AuthorController authorController = new AuthorController();
    private final BookController bookController = new BookController();

    public void run(){
        printOprions();
        do {
            chooseOperation();
            printOprions();
        } while (consoleHelper.readIntegerFromConsole()!=0);
    }

    private void printOprions(){
        consoleHelper.printMessage("Enter number from console to choose operation:\n" +
                                           "1: Create new author\n" +
                                           "2: Get all authors\n" +
                                           "3: Get author by id\n" +
                                           "4: Get author`s books\n" +
                                           "5: Update author info\n" +
                                           "6: Delete author\n" +
                                           "7: Create new book\n" +
                                           "8: Get all books\n" +
                                           "9: Get book by id\n" +
                                           "10: Get book`s authors\n" +
                                           "11: Update book info\n" +
                                           "12: Delete book\n" +
                                           "0: exit");
    }

    private void chooseOperation(){
        int choice = consoleHelper.readIntegerFromConsole();
        switch (choice){
            case 1:
                authorController.create();
                break;
            case 2:
                authorController.findAllAuthors();
                break;
            case 3:
                authorController.findAuthorById();
                break;
            case 4:
                bookController.findBooksByAuthorId();
                break;
            case 5:
                authorController.updateAuthor();
                break;
            case 6:
                authorController.deleteAuthor();
                break;
            case 7:
                bookController.create();
                break;
            case 8:
                bookController.findAllBooks();
                break;
            case 9:
                bookController.findBookById();
                break;
            case 10:
                authorController.findAuthorByBookId();
                break;
            case 11:
                bookController.updateBook();
                break;
            case 12:
                bookController.deleteBook();
                break;
            default:
                consoleHelper.printMessage("Wrong option!");
        }
    }
}
