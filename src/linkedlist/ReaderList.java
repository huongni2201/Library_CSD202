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

/**
 *
 * @author Huong
 */
public class ReaderList {

    private Node<Reader> head;
    
    public Node<Reader> getList() {
        return head;
    }

    public void addReaderToEnd(Reader reader) {
        Node<Reader> newNode = new Node<>(reader);
        if (head == null) {
            head = newNode;
        } else {
            Node<Reader> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void traverse() {
        if (head == null) {
            System.out.println("No readers found.");
            return;
        }
        Node<Reader> current = head;
        while (current != null) {
            current.data.display();
            current = current.next;
        }
    }

    public void displayReaderTitle() {
        System.out.printf("%-10s%-25s%-10s\n", "RCode", "Name", "Birth Year");
    }

    public ReaderList searchByName(String name) {
        ReaderList foundReaders = new ReaderList();  
        Node<Reader> current = head;
        while (current != null) {
            if (current.data.getName().toLowerCase().contains(name.toLowerCase())) {
                foundReaders.addReaderToEnd(current.data);  
            }
            current = current.next;
        }
        return foundReaders;  
    }

    public Reader searchByRcode(String rcode) {
        Node<Reader> current = head;
        while (current != null) {
            if (current.data.getRcode().equalsIgnoreCase(rcode)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public boolean deleteByRcode(String rcode) {
        if (head == null) {
            return false;
        }
        if (head.data.getRcode().equalsIgnoreCase(rcode)) {
            head = head.next;
            return true;
        }
        Node<Reader> current = head;
        while (current.next != null) {
            if (current.next.data.getRcode().equalsIgnoreCase(rcode)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isReaderLended(String rcode, LendingList lendingList) {
        Node<Lending> currentNode = lendingList.getLendingList();
        while (currentNode != null) {
            if (currentNode.data.getRcode().equalsIgnoreCase(rcode)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public void listBookNotReturned(String rcode, BookList bookList, LendingList lendingList) {
        Node<Lending> currentNode = lendingList.getLendingList();
        boolean foundReaders = false;
        while (currentNode != null) {
            if (currentNode.data.getRcode().equalsIgnoreCase(rcode)) {
                String bcode = currentNode.data.getBcode();
                Book book = bookList.searchByBcode(bcode);
                if (book != null) {
                    book.display();
                    foundReaders = true;
                }
            }
            currentNode = currentNode.next;
        }

        if (!foundReaders) {
            System.out.println("No readers found who have not returned the book.");
        }
    }

    public boolean loadDataFromFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            head = null;
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String rcode = data[0];
                String name = data[1];
                int byear = Integer.parseInt(data[2]);

                addReaderToEnd(new Reader(rcode, name, byear));
            }
            return true;
        } catch (IOException ex) {
            throw new IOException("Error loading data from file: " + ex.getMessage());
        }
    }

    public boolean saveToFile(String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)))) {
            Node<Reader> current = head;
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

}
