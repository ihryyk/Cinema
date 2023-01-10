package controller.validator;

public class Validator {

    /**
     * Returns true if the password is valid.
     *
     * @param password - user's password.
     *
     * @return true if the password is valid.
     */
    public static boolean isValidPassword(String password) {
        return DataMatcher.isValid(DataRegex.PASSWORD, password);
    }
    /**
     * Returns true if the email is valid.
     *
     * @param email - user's email.
     *
     * @return true if the email is valid.
     */
    public static boolean isValidEmail(String email) {
        return DataMatcher.isValid(DataRegex.E_MAIL, email);
    }

    /**
     * Returns true if the phone number is valid.
     *
     * @param phoneNumber - user's email.
     *
     * @return true if the phone number is valid.
     */
    public static boolean isValidPhoneNumber (String phoneNumber){
        return DataMatcher.isValid(DataRegex.PHONE_NUMBER, phoneNumber);
    }
    /**
     * Returns true if the name is valid.
     *
     * @param name - user's name.
     *
     * @return true if the name is valid.
     */
    public static boolean isValidName(String name) {
        return DataMatcher.isValid(DataRegex.NAME, name);
    }
    /**
     * Returns true if the eng words is valid.
     *
     * @param words - user's words.
     *
     * @return true if the eng words is valid.
     */
    public static boolean isValidEng(String words) {
        return DataMatcher.isValid(DataRegex.ENG_LANG, words);
    }
    /**
     * Returns true if the ua words is valid.
     *
     * @param words - user's words.
     *
     * @return true if the ua words is valid.
     */
    public static boolean isValidUkr(String words) {
        return DataMatcher.isValid(DataRegex.UKR_LANG, words);
    }
    /**
     * Returns true if the password is valid.
     *
     * @param year -  year of publication of the book.
     *
     * @return true if the password is valid.
     */
    public static boolean isValidYear(String year) {
        return DataMatcher.isValid(DataRegex.YEAR, year);
    }
    /**
     * Returns true if the number is valid.
     *
     * @param number - number.
     *
     * @return true if the number is valid.
     */
    public static boolean isValidNumber(String number) {
        return DataMatcher.isValid(DataRegex.NUMBER, number);
    }
}