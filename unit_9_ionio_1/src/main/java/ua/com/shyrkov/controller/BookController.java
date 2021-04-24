package ua.com.shyrkov.controller;

import ua.com.shyrkov.entity.impl.BookEntity;
import ua.com.shyrkov.service.BookService;
import ua.com.shyrkov.service.impl.BookServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final BookService service = BookServiceImpl.getInstance();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void create() throws IOException {
        while(true) {
            try {
                System.out.println("Please, enter book`s title:");
                String title = reader.readLine();
                System.out.println("Please, enter amount of book`s authors:");
                BookEntity bookEntity = new BookEntity(title, new ArrayList<>());
                int i = Integer.parseInt(reader.readLine());
                for (int j = 0; j < i; j++) {
                    System.out.println("Enter author id:");
                    bookEntity.addAuthorId(Integer.parseInt(reader.readLine()));
                }
                service.create(bookEntity);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input. Please, try again:");
            } catch (RuntimeException e){
                System.out.println(e.getMessage()+"\nPlease, try again:");
            }
        }
    }

    public void readBookById() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter book id:");
                int id = Integer.parseInt(reader.readLine());
                BookEntity byId = service.findById(id);
                System.out.println("Book with id = " + id + ": " + byId.toString());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }
    }

    public void readBookByAuthorId() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter author`s id:");
                int id = Integer.parseInt(reader.readLine());
                List<BookEntity> byId = service.findBooksByAuthorId(id);
                System.out.println("Books by author with id = " + id + ": ");
                for (BookEntity bookEntity : byId) {
                    System.out.println(bookEntity.toString());
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }
    }

    public void readAllBooks() {
        for (BookEntity bookEntity : service.findAll()) {
            System.out.println(bookEntity.toString());
        }
    }

    public void update() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter book`s id you want to update:");
                int id = Integer.parseInt(reader.readLine());
                BookEntity book = service.findById(id);

                System.out.println("Please, enter book`s title:");
                book.setTitle(reader.readLine());
                System.out.println("Do you want to add authors of this book?\n" +
                                           "Press 1 to add author or any key to continue");
                if (reader.readLine().equals("1")) {
                    System.out.println("Enter author`s id:");
                    int authId = Integer.parseInt(reader.readLine());
                    book.addAuthorId(authId);
                }
                service.update(book);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }
    }

    public void delete() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter book`s id you want to delete:");
                int id = Integer.parseInt(reader.readLine());
                service.delete(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }

    }
}
