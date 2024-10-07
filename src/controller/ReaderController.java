/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import bo.ReaderManagement;
import entity.Reader;
import java.io.IOException;
import linkedlist.BookList;
import linkedlist.LendingList;
import linkedlist.ReaderList;

/**
 *
 * @author Huong
 */
public class ReaderController {

    private ReaderList readerList;
    private ReaderManagement readerBO;

    public ReaderController(String fileName) {
        this.readerList = new ReaderList();
        this.readerBO = new ReaderManagement();
        loadDataFromFile(fileName);
    }

    public ReaderList getReaderList() {
        return readerList;
    }

    public void loadDataFromFile(String fileName) {
        try {
            readerList.loadDataFromFile(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDataToFile(String fileName) {
        try {
            readerList.saveToFile(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addReader(Reader reader) {
        if (readerBO.validate(reader)) {
            readerList.addReaderToEnd(reader);
            return true;
        }
        return false;
    }

    public void displayAllReaders() {
        readerList.displayReaderTitle();
        readerList.traverse();
    }

    public void searchByName(String name) {
        ReaderList foundList = readerList.searchByName(name);
        if (foundList.getList() == null) {
            System.out.println("List is empty!");
            return;
        }
        foundList.displayReaderTitle();
        foundList.traverse();
    }

    public Reader searchByRcode(String rcode) {
        return readerList.searchByRcode(rcode);
    }

    public void searchLendedByBcode(String rcode, BookList bookList, LendingList lendingList) {
        Reader foundReader = searchByRcode(rcode);

        if (foundReader == null) {
            System.out.println("Reader not found.");
            return;
        }

        if (readerList.isReaderLended(rcode, lendingList)) {
            System.out.println("\nReader who has lent the book");
            foundReader.display();
            System.out.println("\n---------- Books not returned by the reader ----------\n");
            bookList.displayBookTitle();
            readerList.listBookNotReturned(rcode, bookList, lendingList);
            System.out.println("");
        } else {
            System.out.println("The book is not currently lent out.");
        }
    }

    public boolean deleteByRcode(String rcode) {
        return readerList.deleteByRcode(rcode);
    }

    public Reader inputReader() {
        String rcode;
        while (true) {
            try {
                rcode = utils.NumberUtils.getString("Enter rcode: ", "Please don't push space and enter at least 1 word!");
                if (readerList.searchByRcode(rcode) == null) {
                    break;
                }
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Your code is exist ");
            }
        }

        String name = utils.NumberUtils.getString("Enter name: ", "Please don't push space and enter atleast 1 word!");
        int byear = utils.NumberUtils.getInt("Enter byear: ", 18, Integer.MAX_VALUE);

        Reader r = new Reader(rcode, name, byear);
        return new Reader(rcode, name, byear);
    }
}
