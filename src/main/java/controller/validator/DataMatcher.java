package controller.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {
    /**
     *
     * @param regex - regular expression tester.
     * @param toBeMatched - the sentence which we are checking for validation
     *
     * @return   matcher.
     */
    public static Matcher matches(String regex, String toBeMatched) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(toBeMatched);
    }

    /**
     * Returns true if the value is valid.
     *
     * @param regex - regular expression tester.
     * @param data - the sentence which we are checking for validation
     *
     * @return  true if the value is valid.
     */
    public static boolean isValid(String regex, String data) {
        Matcher matcher = matches(regex, data);
        return matcher.matches();
    }
}