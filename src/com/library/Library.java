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