import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneBookTest {
    @Test
    public void testAddContact() {
        PhoneBook phoneBook = new PhoneBook();
        int count = phoneBook.add("Alice", "123-456-789");
        assertEquals(1, count);

        count = phoneBook.add("Bob", "987-654-321");
        assertEquals(2, count);
    }

    @Test
    public void testFindByNumber() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Alice", "123-456-789");
        phoneBook.add("Bob", "987-654-321");

        String name = phoneBook.findByNumber("123-456-789");
        assertEquals("Alice", name);

        name = phoneBook.findByNumber("987-654-321");
        assertEquals("Bob", name);
    }
}
