import java.util.*;

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
        return numberToPhone.get(number);
    }

    public String findByName(String name) {
        return phoneToNumber.get(name);
    }

    public List<String> printAllNames() {
        return new ArrayList<>(sortedNames);
    }
}
