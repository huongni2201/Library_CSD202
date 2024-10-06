package linkedlist;

import entity.Book;
import entity.Lending;
import entity.Reader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookList {

    private Node<Book> head;

    public boolean loadDataFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            head = null;
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 8) {
                    continue;
                }
                addBookToEnd(new Book(data[0], data[1], data[2], data[3], data[4],
                        Integer.parseInt(data[5]), Integer.parseInt(data[6]),
                        Double.parseDouble(data[7])));
            }
            return true;
        } catch (IOException ex) {
            throw new IOException();
        }
    }

    public boolean saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
            Node<Book> current = head;
            while (current != null) {
                bw.write(current.data.toString());
                bw.newLine();
                current = current.next;
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
        return false;
    }

    public void addBookToEnd(Book book) {
        Node<Book> newNode = new Node<>(book);
        if (head == null) {
            head = newNode;
        } else {
            Node<Book> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void addBookToBeginning(Book book) {
        Node<Book> newNode = new Node<>(book);
        newNode.next = head;
        head = newNode;
    }

    public boolean addBookAfterPos(Book book, int pos) {
        Node<Book> posNode = getNodeByPos(pos);
        if (posNode == null) {
            return false;
        }

        Node<Book> newNode = new Node<>(book);
        newNode.next = posNode.next;
        posNode.next = newNode;
        return true;
    }

    private Node<Book> getNodeByPos(int pos) {
        if (head == null || pos < 0) {
            return null;
        }
        Node<Book> curr = head;
        int k = 0;
        while (curr != null) {
            if (k == pos) {
                return curr;
            }
            k++;
            curr = curr.next;
        }
        return null;
    }

    public void displayBookTitle() {
        System.out.printf("%-10s%-25s%-25s%-25s%-30s%-10s%-10s%-10s\n",
                "BCode", "Title", "Author", "ISBN", "Publisher", "Quantity", "Lended", "Price");
    }

    public void displayBook(Book b) {
        System.out.printf("%-10s%-25s%-25s%-25s%-30s%-10s%-10s%-10s\n", b.getBcode(),
                b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublisher(),
                b.getQuantity(), b.getLended(), b.getPrice());
    }

    public void traverse() {
        if (head == null) {
            System.out.println("No books found.");
            return;
        }
        Node<Book> current = head;
        while (current != null) {
            displayBook(current.data);
            current = current.next;
        }
    }

    public Book searchByBcode(String bcode) {
        return searchByField(bcode, "bcode");
    }

    public Book searchByTitle(String title) {
        return searchByField(title, "title");
    }

    public boolean deleteByBcode(String bcode) {
        return deleteByField(bcode, "bcode");
    }

    public Book deleteByPos(int pos) {
        if (head == null || pos < 0) {
            return null;
        }

        Node<Book> curr = head;

        if (pos == 0) {
            head = curr.next;
            return curr.data;
        }
        int k = 0;
        Node<Book> prev = null;

        while (curr != null) {
            if (k == pos) {
                prev.next = curr.next;
                return curr.data;
            }
            prev = curr;
            curr = curr.next;
            k++;
        }

        return null;
    }

    private Book searchByField(String value, String field) {
        Node<Book> current = head;
        while (current != null) {
            if ((field.equals("bcode") && current.data.getBcode().equalsIgnoreCase(value))
                    || (field.equals("title") && current.data.getTitle().equalsIgnoreCase(value))) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    private boolean deleteByField(String value, String field) {
        if (head == null) {
            return false;
        }
        if ((field.equals("bcode") && head.data.getBcode().equalsIgnoreCase(value))) {
            head = head.next;
            return true;
        }

        Node<Book> current = head;
        while (current.next != null) {
            if ((field.equals("bcode") && current.next.data.getBcode().equals(value))) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void sortByBcode() {
        if (head == null) {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            Node<Book> current = head;

            while (current.next != null) {
                if (current.data.compareTo(current.next.data) > 0) {
                    Book temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }
    
    public boolean isBookLended(String bcode, LendingList lendingList) {
        Node<Lending> currentNode = lendingList.getLendingList();
        while (currentNode != null) {
            if (currentNode.data.getBcode().equalsIgnoreCase(bcode)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public void listReadersNotReturned(String bcode, ReaderList readerList, LendingList lendingList) {
        Node<Lending> currentNode = lendingList.getLendingList();
        boolean foundReaders = false;
        while (currentNode != null) {
            if (currentNode.data.getBcode().equals(bcode) && currentNode.data.getState() == 1) {
                String rcode = currentNode.data.getRcode();
                Reader reader = readerList.searchByRcode(rcode);
                if (reader != null) {
                    reader.display();
                    foundReaders = true;
                }
            }
            currentNode = currentNode.next;
        }

        if (!foundReaders) {
            System.out.println("No readers found who have not returned the book.");
        }
    }
}
