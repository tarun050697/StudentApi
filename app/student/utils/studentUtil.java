package student.utils;

import java.util.UUID;

public class studentUtil {
    public String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
