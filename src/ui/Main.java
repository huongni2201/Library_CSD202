/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import controller.BookController;
import controller.LendingController;
import controller.ReaderController;
import entity.Book;
import entity.Lending;
import entity.Reader;

/**
 *
 * @author Huong
 */
public class Main {

    public static void main(String[] args) {
        String mainMenu = """
                          ----------- Library Management System -----------
                          1. Book Management
                          2. Reader Management
                          3. Lending Management
                          4. Exit
                          """;
        String bookFile = "book.txt";
        String readerFile = "reader.txt";
        String lendingFile = "lending.txt";

        ReaderController readerController = new ReaderController(readerFile);
        BookController bookController = new BookController(bookFile);
        LendingController lendingController
                = new LendingController(bookController, readerController, lendingFile);

        while (true) {
            System.out.println(mainMenu);
            int choice = utils.NumberUtils.getInt("Your choice: ", 1, 4);

            switch (choice) {
                case 1:
                    bookMenu(bookController, readerController, lendingController, bookFile);
                    break;
                case 2:
                    readerMenu(bookController, readerController, lendingController, readerFile);
                    break;
                case 3: {
                    lendingMenu(lendingController, lendingFile);
                    break;
                }
                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void bookMenu(BookController bookController,
            ReaderController readerController,
            LendingController lendingController,
            String fileName) {

        String menu = """
                      ----------- Book Management -----------
                      1. Load data from file
                      2. Add book to the end
                      3. Display All Books
                      4. Save Book list to file
                      5. Search Book by bcode
                      6. Delete Book by bcode
                      7. Sort by bcode
                      8. Add to beginning
                      9. Add after position k
                      10. Delete position k
                      11. Search by title
                      12. Search lended book by bcode
                      13. Quit
                      """;

        while (true) {
            System.out.println(menu);
            int choice = utils.NumberUtils.getInt("Your choice: ", 1, 13);

            switch (choice) {
                case 1:
                    bookController.loadDataFromFile(fileName);
                    break;
                case 2:
                    bookController.addBook(bookController.inputBook());
                    break;
                case 3:
                    System.out.println("");
                    bookController.displayAllBooks();
                    System.out.println("");
                    break;
                case 4:
                    bookController.saveDataToFile(fileName);
                    break;
                case 5:
                    String searchByBcode = utils.NumberUtils.getString("Enter bcode to search: ", "bcode cannot be empty!");
                    Book book = bookController.searchByBcode(searchByBcode);
                    if (book != null) {
                        System.out.println("Found: " + book);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 6:
                    String bcode = utils.NumberUtils.getString("Enter bcode to delete: ", "Bcode invalid!");
                    if (bookController.deleteByBcode(bcode)) {
                        System.out.println("Book deleted successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 7:
                    bookController.sortByBcode();
                    bookController.displayAllBooks();
                    break;
                case 8:
                    Book newBook = bookController.inputBook();
                    if (bookController.addFirst(newBook)) {
                        System.out.println("Added to beginning successfully!");
                    } else {
                        System.out.println("Failed to add!");
                    }
                    break;
                case 9:
                    Book bookAfterPos = bookController.inputBook();
                    int pos = utils.NumberUtils.getInt("Enter position k: ", 0, Integer.MAX_VALUE);
                    if (bookController.addAfterPos(bookAfterPos, pos)) {
                        System.out.println("Added successfully!");
                    } else {
                        System.out.println("Failed to add!");
                    }
                    break;
                case 10:
                    int deletePos = utils.NumberUtils.getInt("Enter position k: ", 0, Integer.MAX_VALUE);
                    Book deletedBook = bookController.deleteByPos(deletePos);
                    if (deletedBook != null) {
                        System.out.println("Deleted Book: " + deletedBook.getTitle());
                    } else {
                        System.out.println("Delete failed!");
                    }
                    break;
                case 11:
                    String searchByTitle = utils.NumberUtils.getString("Enter title to search: ", "title cannot be empty!");
                    Book foundBook = bookController.searchByTitle(searchByTitle);
                    if (foundBook != null) {
                        System.out.println("Found: " + foundBook);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 12: {
                    String searchLendedByBcode = utils.NumberUtils.getString("Enter bcode to search: ", "bcode cannot be empty!");
                    bookController.searchLendedByBcode(searchLendedByBcode,
                            readerController.getReaderList(),
                            lendingController.getLendingList());
                    break;
                }
                case 13: {
                    System.out.println("Exit the menu!");
                    return;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void readerMenu(BookController bookController,
            ReaderController readerController,
            LendingController lendingController,
            String fileName) {

        String menu = """
                      ----------- Reader Management -----------
                      1. Load data from file
                      2. Add Reader
                      3. Display All Readers
                      4. Save reader list to file
                      5. Search Reader by rcode
                      6. Delete by rcode
                      7. Search by name
                      8. Search lending books by rcode
                      9. Quit
                      """;

        while (true) {
            System.out.println(menu);
            int choice = utils.NumberUtils.getInt("Your choice: ", 1, 9);

            switch (choice) {
                case 1:
                    readerController.loadDataFromFile(fileName);
                    break;
                case 2:
                    readerController.addReader(readerController.inputReader());
                    break;
                case 3:
                    readerController.displayAllReaders();
                    break;
                case 4:
                    readerController.saveDataToFile(fileName);
                    break;
                case 5:
                    String searchRcode = utils.NumberUtils.getString("Enter rcode to search: ", "rcode cannot be empty!");
                    Reader foundReader = readerController.searchByRcode(searchRcode);
                    if (foundReader != null) {
                        System.out.println("Found: " + foundReader);
                    } else {
                        System.out.println("Reader not found.");
                    }
                    break;
                case 6:
                    String rcode = utils.NumberUtils.getString("Enter rcode to delete: ", "Rcode invalid!");
                    if (readerController.deleteByRcode(rcode)) {
                        System.out.println("Reader deleted successfully.");
                    } else {
                        System.out.println("Reader not found.");
                    }
                    break;
                case 7:
                    String name = utils.NumberUtils.getString("Enter name to search: ", "Name invalid!");
                    readerController.searchByName(name);
                    break;
                case 8:
                    String lendedByRcode = utils.NumberUtils.getString("Enter rcode to search: ", "Rcode invalid!");
                    readerController.searchLendedByBcode(lendedByRcode,
                            bookController.getBookList(),
                            lendingController.getLendingList());
                    break;
                case 9: {
                    System.out.println("Quit");
                    return;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void lendingMenu(LendingController lendingController, String fileName) {

        String menu = """
                  ----------- Lending Management -----------
                  1. Load data from file
                  2. Lend book
                  3. Display All Lendings
                  4. Save lending list to file
                  5. Sort lendings by bcode + rcode
                  6. Return book
                  7. Exit
                  """;

        while (true) {
            System.out.println(menu);
            int choice = utils.NumberUtils.getInt("Your choice: ", 1, 8);

            switch (choice) {
                case 1: {
                    lendingController.loadDataFromFile(fileName);
                    System.out.println("Data loaded successfully.");
                    break;
                }
                case 2: {
                    Lending newLending = lendingController.inputToLendingBook();
                    break;
                }
                case 3: {
                    lendingController.displayAllLendings();
                    break;
                }
                case 4: {
                    lendingController.saveDataToFile(fileName);
                    System.out.println("Lending list saved successfully.");
                    break;
                }
                case 5: {
                    lendingController.sortByBcodeAndRcode();
                    lendingController.displayAllLendings();
                    break;
                }
                case 6: {
                    String bcodeSearch = utils.NumberUtils.getString("Enter bcode to search: ", "Bcode cannot be empty.");
                    String rcodeSearch = utils.NumberUtils.getString("Enter rcode to search: ", "Rcode cannot be empty.");
                    if (lendingController.returnBook(bcodeSearch, rcodeSearch)) {
                        System.out.println("Return book successfully!");
                    } else {
                        System.out.println("Return faile");
                    }
                    break;
                }
                case 7: {
                    System.out.println("Exit menu!");
                    return;
                }

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
