/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import linkedlist.Node;

/**
 *
 * @author Huong
 */
public class Book implements Comparable<Book> {

    private String bcode;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int quantity;
    private int lended;
    private double price;

    private Node<Reader> head;

    public Book(String bcode, String title, String author, String isbn, String publisher, int quantity, int lended, double price) {
        this.bcode = bcode;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.quantity = quantity;
        this.lended = lended;
        this.price = price;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLended() {
        return lended;
    }

    public void setLended(int lended) {
        this.lended = lended;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Book o) {
        return this.getBcode().compareToIgnoreCase(o.getBcode());
    }

    @Override
    public String toString() {
        return bcode + "," + title + "," + author + "," + isbn
                + "," + publisher + "," + quantity + "," + lended
                + "," + price;
    }

    public void swap(Book other) {
        String tempBcode = this.bcode;
        this.bcode = other.bcode;
        other.bcode = tempBcode;

        String tempTitle = this.title;
        this.title = other.title;
        other.title = tempTitle;

        String tempAuthor = this.author;
        this.author = other.author;
        other.author = tempAuthor;

        String tempIsbn = this.isbn;
        this.isbn = other.isbn;
        other.isbn = tempIsbn;

        String tempPublisher = this.publisher;
        this.publisher = other.publisher;
        other.publisher = tempPublisher;

        int tempQuantity = this.quantity;
        this.quantity = other.quantity;
        other.quantity = tempQuantity;

        int tempLended = this.lended;
        this.lended = other.lended;
        other.lended = tempLended;

        double tempPrice = this.price;
        this.price = other.price;
        other.price = tempPrice;
    }

    public void display() {
        System.out.printf("%-10s%-25s%-25s%-25s%-30s%-10d%-10d%-10.2f\n",
                bcode, title, author, isbn, publisher, quantity, lended, price);
    }

    public Node<Reader> getReaderNotReturn() {
        return head;
    }

    public void addReaderNotReturned(Reader reader) {
        if (head == null) {
            head = new Node<>(reader);
            return;
        }
        Node<Reader> curr = head;
        while (curr != null) {
            curr = curr.next;
        }
        curr = new Node<>(reader);
    }

    public boolean removeReaderReturned(Reader reader) {
        if (head == null) {
            return false;  
        }
        if (head.data.equals(reader)) {
            head = head.next;  
            return true;
        }
        Node<Reader> curr = head;
        while (curr.next != null) {
            if (curr.next.data.equals(reader)) {
                curr.next = curr.next.next;  
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

}
