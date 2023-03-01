package ru.netolgy.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {
    GeoService geoService = new GeoServiceImpl();
    @ParameterizedTest
    @ValueSource(strings = {"96.44.183.149", "96.44.183.1", "96.44.183.2"})
    void geoUSATest(String sourceIp) {

        Assertions.assertEquals(Country.USA, geoService.byIp(sourceIp).getCountry());
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11", "172.0.32.10", "172.0.32.1"})
    void geoRussiaTest(String sourceIp) {

        Assertions.assertEquals(Country.RUSSIA, geoService.byIp(sourceIp).getCountry());

    }
}
