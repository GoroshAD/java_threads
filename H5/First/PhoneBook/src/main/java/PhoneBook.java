import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    static Map<String, String> phoneToNumber = new HashMap<>();
    static Map<String, String> numberToPhone = new HashMap<>();

    public static int add(String name, String number) {
        phoneToNumber.put(name, number);
        numberToPhone.put(number, name);
        return phoneToNumber.size();
    }
}
