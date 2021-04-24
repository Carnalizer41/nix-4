package ua.com.shyrkov.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainController {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final AuthorController authorController = new AuthorController();
    private static final BookController bookController = new BookController();

    public static void run(){
        while(true) {
            System.out.println("Please, choose operation with library database:\n" +
                                       "1: Create new author\n" +
                                       "2: Create new book\n" +
                                       "3: Read author by id\n" +
                                       "4: Read book by id\n" +
                                       "5: Read author by book id\n" +
                                       "6: Read book by author id\n" +
                                       "7: Read all authors\n" +
                                       "8: Read all books\n" +
                                       "9: Update author\n" +
                                       "10: Update book\n" +
                                       "11: Delete author\n" +
                                       "12: Delete book\n" +
                                       "0: exit");

            try {
                switch (Integer.parseInt(reader.readLine())) {
                    case 1: authorController.create(); break;
                    case 2: bookController.create(); break;
                    case 3: authorController.readAuthorById(); break;
                    case 4: bookController.readBookById(); break;
                    case 5: authorController.readAuthorByBookId(); break;
                    case 6: bookController.readBookByAuthorId(); break;
                    case 7: authorController.readAllAuthors(); break;
                    case 8: bookController.readAllBooks(); break;
                    case 9: authorController.update(); break;
                    case 10: bookController.update(); break;
                    case 11: authorController.delete(); break;
                    case 12: bookController.delete(); break;
                    case 0: System.exit(0);
                    default: throw new IOException();
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Wrong input. Try again");
            }
        }
    }
}
