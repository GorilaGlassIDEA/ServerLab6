package by.dima.model.service.util;

public class GetSecondArgFromArgsUtil {
    public static String getSecondArg(String[] args) {
        if (args.length > 1) {
            if (args.length > 2) {
                System.err.println("Incorrect input!");
            } else {
                return args[1];
            }
        }
        return "";
    }
}
