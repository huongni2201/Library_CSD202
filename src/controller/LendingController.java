package controller;

import entity.Book;
import entity.Lending;
import java.io.IOException;
import linkedlist.LendingList;

/**
 *
 * @author Huong
 */
public class LendingController {

    private LendingList lendingList;
    private BookController bookController;
    private ReaderController readerController;

    public LendingController(BookController bookController, ReaderController readerController, String fileName) {
        this.readerController = readerController;
        this.bookController = bookController;
        lendingList = new LendingList();
        loadDataFromFile(fileName);
    }

    public LendingList getLendingList() {
        return lendingList;
    }

    public boolean loadDataFromFile(String fileName) {
        try {
            return lendingList.loadDataFromFile(fileName);
        } catch (IOException ex) {
            System.out.println("Failed to load data: " + ex.getMessage());
            return false;
        }
    }

    public void saveDataToFile(String fileName) {
        try {
            lendingList.saveToFile(fileName);
        } catch (IOException ex) {
            System.out.println("Failed to save data: " + ex.getMessage());
        }
    }

    public void displayAllLendings() {
        lendingList.displayTitle();
        lendingList.displayAllLendings();
    }

    public void sortByBcodeAndRcode() {
        lendingList.sortByBcodeAndRcode();
    }

    public boolean returnBook(String bcode, String rcode) {
        if (lendingList.returnBook(bcode, rcode)) {
            Book book = bookController.searchByBcode(bcode);
            book.setQuantity(book.getQuantity() + 1);
            book.setLended(book.getLended() - 1);
            return true;
        }
        return false;
    }

    public void addLendingToBeginning(Lending lending) {
        lendingList.addLendingToBeginning(lending);
    }

    public Lending inputToLendingBook() {
        String bcode, rcode;
        while (true) {
            try {
                bcode = utils.NumberUtils.getString("Enter bcode: ", "Please don't push space and enter at least 1 word!").trim();
                rcode = utils.NumberUtils.getString("Enter rcode: ", "Please don't push space and enter at least 1 word!").trim();

                if (bookController.getBookList().searchByBcode(bcode) == null) {
                    throw new IllegalArgumentException("Book code does not exist.");
                }

                if (readerController.getReaderList().searchByRcode(rcode) == null) {
                    throw new IllegalArgumentException("Reader code does not exist.");
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        Book foundBook = bookController.getBookList().searchByBcode(bcode);

        if (foundBook.getQuantity() > 0) {
            foundBook.setQuantity(foundBook.getQuantity() - 1);
            foundBook.setLended(foundBook.getLended() + 1);

            Lending lending = new Lending(bcode, rcode);
            lendingList.addLendingToBeginning(lending);
            if (lending != null) {
                System.out.println("Lending info: " + lending); 
            }

            Book updatedBook = bookController.getBookList().searchByBcode(bcode);
            System.out.println("Updated quantity for book " + bcode + ": " + updatedBook.getQuantity());
            System.out.println("Lending recorded successfully.");
            return lending;
        } else {
            System.out.println("No available copies of the book to lend.");
            return null;
        }
    }
}
