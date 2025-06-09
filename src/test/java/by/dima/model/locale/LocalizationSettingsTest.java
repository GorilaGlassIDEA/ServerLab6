package by.dima.model.locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;



class LocalizationSettingsTest {

    @Test
    void testFirst() {
        Locale locale = new Locale("ru", "RU");
        String russianLangYes = LocalizationSettings.installLangResource(locale).getString("yes");
        Assertions.assertEquals("да", russianLangYes);
    }
    @Test
    void testSecond() {
        Locale.setDefault(new Locale("ru"));
        String russianLangYes = LocalizationSettings.installLangResource().getString("yes");
        Assertions.assertEquals("да", russianLangYes);
    }

}