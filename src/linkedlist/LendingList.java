package linkedlist;

import entity.Lending;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Huong
 */
public class LendingList {

    private Node<Lending> head;

    public Node<Lending> getLendingList() {
        return head;
    }

    public void addLendingToEnd(Lending lending) {
        Node<Lending> newNode = new Node<>(lending);
        if (head == null) {
            head = newNode;
        } else {
            Node<Lending> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void addLendingToBeginning(Lending lending) {
        Node<Lending> newNode = new Node<>(lending);
        newNode.next = head;
        head = newNode;
    }

    public void displayAllLendings() {
        if (head == null) {
            System.out.println("No lending records found.");
            return;
        }
        Node<Lending> current = head;
        while (current != null) {
            displayLending(current.data);
            current = current.next;
        }
    }

    public void displayTitle() {
        System.out.printf("%-10s%-10s%-25s%-15s%-15s\n",
                "BCode", "RCode", "Lending Date", "Return Date", "Status");
    }

    public void displayLending(Lending l) {
        System.out.printf("%-10s%-10s%-25s%-15s%-15s\n",
                l.getBcode(), l.getRcode(), l.getLdate(), l.getRdate(), l.getState());
    }

    public boolean returnBook(String bcode, String rcode) {
        Node<Lending> current = head;
        while (current != null) {
            if (current.data.getBcode().equalsIgnoreCase(bcode)
                    && current.data.getRcode().equalsIgnoreCase(rcode)
                    && current.data.getState() == 1) {
                current.data.setRdate(LocalDate.now());
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void sortByBcodeAndRcode() {
        if (head == null) {
            return;
        }
        boolean swapped;
        do {
            swapped = false;
            Node<Lending> current = head;
            while (current.next != null) {
                if (current.data.compareTo(current.next.data) > 0) {
                    Lending temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public boolean loadDataFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            head = null;
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) {
                    System.err.println("Skipping line due to insufficient data: " + line);
                    continue;
                }
                Lending lending = new Lending(data[0], data[1]);
                lending.setLdate(LocalDate.parse(data[2]));

                if (data[3] != null && !data[3].trim().equalsIgnoreCase("null") && !data[3].trim().isEmpty()) {
                    lending.setRdate(LocalDate.parse(data[3]));
                } else {
                    lending.setRdate(null);
                }

                lending.setState(Integer.parseInt(data[4])); // Set state
                addLendingToEnd(lending);
            }
            return true;
        } catch (IOException ex) {
            throw new IOException("Error loading lending data: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public boolean saveToFile(String path) throws IOException {
        try (FileWriter fw = new FileWriter(new File(path)); BufferedWriter bw = new BufferedWriter(fw)) {
            Node<Lending> current = head;
            while (current != null) {
                Lending lending = current.data;
                String returnDate = lending.getRdate() != null ? lending.getRdate().toString() : "null";
                bw.write(lending.getBcode() + "," + lending.getRcode() + "," + lending.getLdate() + "," + returnDate + "," + lending.getState());
                bw.newLine();
                current = current.next;
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
