package by.dima.model.locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;



class LocalizationSettingsTest {

    @Test
    void test() {

        Locale locale = new Locale("ru", "RU");
        String russianLangYes = LocalizationSettings.installLangResource(locale).getString("yes");
        Assertions.assertEquals("да", russianLangYes);
    }

}