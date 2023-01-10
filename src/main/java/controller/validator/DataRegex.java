package controller.validator;

public class DataRegex {
    public final static String E_MAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    public final static String PHONE_NUMBER = "^\\+?3?8?(0\\d{9})$";
    public final static String YEAR = "^[0-9]{3,4}+$";
    public final static String ENG_LANG = "^[a-zA-z '.,-]+$";
    public final static String UKR_LANG = "^[А-Яа-яёЁЇїІіЄєҐґ '.,-]+$";
    public final static String PASSWORD = "^([a-zA-Z0-9@*#]{4,10})$";
    public final static String NAME = "^[A-Za-z]+$";
    public final static String NUMBER = "[0-9]+";
    private DataRegex() {
    }
}