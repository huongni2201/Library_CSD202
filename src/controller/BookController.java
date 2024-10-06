/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import bo.BookManagement;
import entity.Book;
import java.io.IOException;
import linkedlist.BookList;
import linkedlist.LendingList;
import linkedlist.ReaderList;

/**
 *
 * @author Huong
 */
public class BookController {

    private BookManagement bookBO;
    private BookList bookList;

    public BookController(String fileName) {
        this.bookBO = new BookManagement();
        this.bookList = new BookList();
        loadDataFromFile(fileName);
    }

    public BookList getBookList() {
        return bookList;
    }

    public void loadDataFromFile(String fileName) {
        try {
            bookList.loadDataFromFile(fileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveDataToFile(String fileName) {
        bookList.saveToFile(fileName);
    }

    public boolean addBook(Book book) {
        if (bookBO.validate(book)) {
            bookList.addBookToEnd(book);
            return true;
        }
        return false;
    }

    public void displayBookTitle() {
        bookList.displayBookTitle();
    }

    public void displayAllBooks() {
        bookList.displayBookTitle();
        bookList.traverse();
    }

    public Book searchByBcode(String bcode) {
        return bookList.searchByBcode(bcode);
    }

    public Book searchByTitle(String title) {
        return bookList.searchByTitle(title);
    }

    public boolean deleteByBcode(String bcode) {
        return bookList.deleteByBcode(bcode);
    }

    public void sortByBcode() {
        bookList.sortByBcode();
    }

    public void searchLendedByBcode(String bcode, ReaderList readerList, LendingList lendingList) {
        Book foundBook = searchByBcode(bcode);

        if (foundBook == null) {
            System.out.println("Book not found.");
            return;
        }

        if (bookList.isBookLended(bcode, lendingList)) {
            System.out.println("\nBook not returned by the reader: \n");
            foundBook.display();
            System.out.println("\nReaders who have not returned the book: \n");
            
            readerList.displayReaderTitle();
            bookList.listReadersNotReturned(bcode, readerList, lendingList);
        } else {
            System.out.println("The book is not currently lent out.");
        }
    }

    public boolean addFirst(Book book) {
        if (bookBO.validate(book)) {
            bookList.addBookToBeginning(book);
            return true;
        }
        return false;
    }

    public boolean addAfterPos(Book book, int pos) {
        if (bookBO.validate(book)) {
            return bookList.addBookAfterPos(book, pos);
        }
        return false;
    }

    public Book deleteByPos(int pos) {
        return bookList.deleteByPos(pos);
    }

    public Book inputBook() {
        String bcode;
        while (true) {
            try {
                bcode = utils.NumberUtils.getString("Enter bcode: ", "Please don't push space and enter at least 1 word!");
                if (bookList.searchByBcode(bcode) == null) {
                    break;
                }
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Book code already exists.");
            }
        }

        String title = utils.NumberUtils.getString("Enter title: ", "Please don't push space and enter at least 1 word!");
        String author = utils.NumberUtils.getString("Enter author: ", "Please don't push space and enter at least 1 word!");
        String isbn = utils.NumberUtils.getString("Enter ISBN: ", "Please don't push space and enter at least 1 word!");
        String publisher = utils.NumberUtils.getString("Enter publisher: ", "Please don't push space and enter at least 1 word!");
        int quantity = utils.NumberUtils.getInt("Enter quantity: ", 1, Integer.MAX_VALUE);
        int lended = utils.NumberUtils.getInt("Enter lended: ", 0, quantity);
        double price = utils.NumberUtils.getDouble("Enter price: ", 0.0, Double.MAX_VALUE);

        return new Book(bcode, title, author, isbn, publisher, quantity, lended, price);
    }
}
