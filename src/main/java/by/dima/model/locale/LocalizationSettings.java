package by.dima.model.locale;


import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class LocalizationSettings {
    private static final String prefixName = "interface";

    public static ResourceBundle installLangResource(Locale locale) {
        Locale.setDefault(locale);
        return ResourceBundle.getBundle(prefixName, Locale.getDefault(), ResourceBundle.Control.getControl(Control.FORMAT_PROPERTIES));
    }

    public static ResourceBundle installLangResource() {
        Locale.setDefault(Locale.getDefault());
        return ResourceBundle.getBundle(prefixName, Locale.getDefault(), ResourceBundle.Control.getControl(Control.FORMAT_PROPERTIES));
    }
}
