package by.dima.model.locale;


import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class LocalizationSettings {

    public static ResourceBundle installLangResource(Locale locale) {
        Locale.setDefault(locale);
        return ResourceBundle.getBundle("interface", Locale.getDefault(), ResourceBundle.Control.getControl(Control.FORMAT_PROPERTIES));
    }
}
