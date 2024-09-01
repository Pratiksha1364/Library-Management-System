package com.library;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class LibraryTest {
    Library library = new Library();

    @Test
    public void testAddBook() {

        Book book = new Book("101", "The Great Gatsby", "F. Scott Fitzgerald", 2000);
        library.addBook(book);
        assertTrue(library.getAvailableBooks().contains(book));
    }

    @Test
    public void testAddDuplicateIsbn() {

        Book book1 = new Book("102", "1984", "George Orwell", 1949); // First book with ISBN 102
        Book book2 = new Book("102", "Animal Farm", "George Orwell", 1945); // Second book with the same ISBN

        library.addBook(book1); // Add the first book to the library

        try {
            library.addBook(book2); // Attempt to add the second book, which should trigger the exception
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("A book with this ISBN already exists: 102", e.getMessage());
        }
    }

    @Test
    public void testAddWithNullIsbn() {

        Book book = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 2000);

        try {
            library.addBook(book); // This should throw an IllegalArgumentException due to null ISBN
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("ISBN cannot be null or empty", e.getMessage());
        }
    }

    @Test
    public void testAddWithInvalidPublicationYear() {
        Library library = new Library();
        Book book = new Book("104", "The Great Gatsby", "F. Scott Fitzgerald", 200);

        try {
            library.addBook(book); // This should throw an IllegalArgumentException due to invalid publication year
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Verify that the exception message matches the expected message
            assertEquals("Publication Year is invalid", e.getMessage());
        }
    }

}