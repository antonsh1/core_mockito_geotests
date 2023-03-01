package ru.netolgy.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {

    String expectedRussia = "Добро пожаловать";
    String expectedOther = "Welcome";
    @Test
    void localizationService() {

        LocalizationService localizationService = new LocalizationServiceImpl();

        Assertions.assertEquals(expectedRussia, localizationService.locale(Country.RUSSIA));
        Assertions.assertEquals(expectedOther, localizationService.locale(Country.USA));

    }
}
