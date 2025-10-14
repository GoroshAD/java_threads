import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class PhoneBook {
    private Map<String, String> phoneToNumber = new HashMap<>();
    private Map<String, String> numberToPhone = new HashMap<>();
    private SortedSet<String> sortedNames = new TreeSet<>();

    public int add(String name, String number) {
        phoneToNumber.put(name, number);
        numberToPhone.put(number, name);
        sortedNames.add(name);
        return phoneToNumber.size();
    }

    public String findByNumber(String number) {
        return null;
    }
}
