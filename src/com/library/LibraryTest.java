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

    @Test
    public void testBorrowBook() {
        // Add the book to the library
        Book book = new Book("103", "The Adventures Of Tom Sawyer", "Mark Twain", 2022);
        library.addBook(book);

        // Ensure the book is available before borrowing
        assertTrue(library.isBookAvailable("103"), "The book should be available before borrowing.");

        // Borrow the book
        library.borrowBook("103");

        // Assert that the book is no longer available in the library
        assertFalse(library.isBookAvailable("103"), "The book should not be available after being borrowed.");
    }

    @Test
    public void testBookAlreadyBorrowed() {
        // Add a book to the library
        Book book = new Book("105", "The Catcher in the Rye", "J.D. Salinger", 1951);
        library.addBook(book);

        // Borrow the book for the first time
        library.borrowBook("105");

        // Try to borrow the same book again
        try {
            library.borrowBook("105"); // This should throw an IllegalArgumentException
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Verify that the exception message indicates the book is already borrowed
            assertEquals("Already Borrowed", e.getMessage());
        }
    }

    @Test
    public void testReturnBook() {
        // Set up the library and add a book with ISBN "106"
        Book book = new Book("106", "1984", "George Orwell", 1949);
        library.addBook(book);

        // Borrow the book using the correct ISBN
        library.borrowBook("106");

        // Now return the book using the correct ISBN
        library.returnBook("106");

        // Verify that the book is available again using the correct ISBN
        assertTrue(library.getAvailableBooks().stream().anyMatch(b -> b.getIsbn().equals("106")),
                "The book should be available after returning.");
    }

    @Test
    public void testReturnBookNotBorrowed() {
        // Set up the library and add a book with ISBN "107"
        Book book = new Book("107", "To Kill a Mockingbird", "Harper Lee", 1960);
        library.addBook(book);

        // Attempt to return the book without borrowing it first
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("107");
        });

        // Verify the exception message
        assertEquals("Book was not borrowed: 107", exception.getMessage());
    }

    @Test
    public void testReturnBookNotAvailable() {
        // Attempt to return a book with an ISBN that was never added to the library
        String isbnNotInLibrary = "108";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook(isbnNotInLibrary);
        });

        // Verify that the exception message is correct
        assertEquals("Book was not borrowed: 108", exception.getMessage());
    }

    @Test
    public void testBookAlreadyReturned() {
        // Add a book to the library
        Book book = new Book("109", "The Lord of the Rings", "J.R.R. Tolkien", 1954);
        library.addBook(book);

        // Borrow the book
        library.borrowBook("109");

        // Return the book
        library.returnBook("109");

        // Attempt to return the same book again, which should fail
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("109");
        });

        // Verify that the exception message is correct
        assertEquals("Book was not borrowed: 109", exception.getMessage());
    }

    @Test
    public void testViewAvailableBooks() {
        Book book1 = new Book("110", "Pride and Prejudice", "Jane Austen", 1813);
        Book book2 = new Book("111", "Moby-Dick", "Herman Melville", 1851);
        Book book3 = new Book("112", "War and Peace", "Leo Tolstoy", 1869);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.borrowBook("110"); // Borrow one book

        List<Book> availableBooks = library.getAvailableBooks();

        assertEquals(2, availableBooks.size());
        assertTrue(availableBooks.contains(book2));
        assertTrue(availableBooks.contains(book3));
        assertFalse(availableBooks.contains(book1)); // This book should not be in the available list
    }
}
