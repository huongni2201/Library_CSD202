
package bo;

import entity.Book;

/**
 *
 * @author Huong
 */
public class BookManagement {

    public boolean validate(Book book) {
        if (book.getBcode() == null || book.getBcode().isEmpty()) {
            System.out.println("Book code is required.");
            return false;
        }
        if (book.getQuantity() < book.getLended()) {
            System.out.println("Lended books cannot exceed total quantity.");
            return false;
        }
        if (book.getPrice() < 0) {
            System.out.println("Price must be greater than or equal to 0.");
            return false;
        }
        return true;
    }
}
