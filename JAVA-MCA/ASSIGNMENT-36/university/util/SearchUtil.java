package university.util;

public class SearchUtil {

    public static boolean match(String source, String target) {

        return source.toLowerCase().contains(target.toLowerCase());

    }

}