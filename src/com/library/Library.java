package com.library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<String> borrowedBooks = new ArrayList<>();

    public void addBook(Book book) {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (book.getPublicationYear() < 1000) {
            throw new IllegalArgumentException("Publication Year is invalid");
        }
        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                throw new IllegalArgumentException("A book with this ISBN already exists: " + book.getIsbn());
            }
        }
        books.add(book);
    }

    public void borrowBook(String isbn) {
        if (!isBookAvailable(isbn)) {
            if (borrowedBooks.contains(isbn)) {
                throw new IllegalArgumentException("Already Borrowed");
            } else {
                throw new IllegalArgumentException("Book is not available in library: " + isbn);
            }
        }
        borrowedBooks.add(isbn);
    }

    public boolean isBookAvailable(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !borrowedBooks.contains(isbn)) {
                return true;
            }
        }
        return false;
    }

    public void returnBook(String isbn) {
        // Check if the book was actually borrowed
        if (!borrowedBooks.contains(isbn)) {
            throw new IllegalArgumentException("Book was not borrowed: " + isbn);
        }
        // Remove the book from the borrowedBooks list to mark it as returned
        borrowedBooks.remove(isbn);
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (!borrowedBooks.contains(book.getIsbn())) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

}