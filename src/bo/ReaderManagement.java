
package bo;

import entity.Reader;

public class ReaderManagement {

    public boolean validate(Reader reader) {
        if (reader.getRcode() == null || reader.getRcode().isEmpty()) {
            System.out.println("Reader code is required.");
            return false;
        }
        if (reader.getByear() < 18) {
            System.out.println("Reader must be at least 18 years old.");
            return false;
        }
        return true;
    }
}
