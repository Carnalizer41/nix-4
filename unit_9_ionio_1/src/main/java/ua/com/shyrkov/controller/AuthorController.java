package ua.com.shyrkov.controller;

import ua.com.shyrkov.entity.impl.AuthorEntity;
import ua.com.shyrkov.service.AuthorService;
import ua.com.shyrkov.service.impl.AuthorServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthorController {

    private final AuthorService service = AuthorServiceImpl.getInstance();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void create() throws IOException {
        System.out.println("Please, enter author`s first name:");
        String firstName = reader.readLine();
        System.out.println("Please, enter author`s last name:");
        String lastName = reader.readLine();
        service.create(new AuthorEntity(firstName, lastName, new ArrayList<>()));
    }

    public void readAuthorById() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter author`s id:");
                int id = Integer.parseInt(reader.readLine());
                AuthorEntity byId = service.findById(id);
                System.out.println("Author with id = " + id + ": " + byId.toString());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }
    }

    public void readAuthorByBookId() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter book`s id:");
                int id = Integer.parseInt(reader.readLine());
                List<AuthorEntity> byId = service.findAuthorsByBookId(id);
                System.out.println("Authors of book with id = " + id + ": ");
                for (AuthorEntity authorEntity : byId) {
                    System.out.println(authorEntity.toString());
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input format. Please, enter id number again:");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage() + "\nPlease, enter id number again:");
            }
        }
    }

    public void readAllAuthors() {
        for (AuthorEntity authorEntity : service.findAll()) {
            System.out.println(authorEntity.toString());
        }
    }

    public void update() throws IOException {
        while (true) {
            try {
                System.out.println("Please, enter author`s id you want to update:");
                int id = Integer.parseInt(reader.readLine());
                AuthorEntity author = service.findById(id);

                System.out.println("Please, enter author`s first name:");
                author.setFirstName(reader.readLine());
                System.out.println("Please, enter author`s last name:");
                author.setLastName(reader.readLine());
                System.out.println("Do you want to add book to author`s book collection?\n" +
                                           "Press 1 to add book or any key to continue");
                if (reader.readLine().equals("1")) {
                    System.out.println("Enter book id:");
                    int bookId = Integer.parseInt(reader.readLine());
                    author.addBookId(bookId);
                }
                service.update(author);
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
                System.out.println("Please, enter author`s id you want to delete:");
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
