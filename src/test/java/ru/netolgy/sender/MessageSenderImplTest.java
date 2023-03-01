package ru.netolgy.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;


public class MessageSenderImplTest {
    private static String expectedRussia;
    private static String expectedOther;
    private static LocalizationService localizationServiceMock;
    private static GeoService geoServiceMock;

    @BeforeAll
    static void initSenderTest() {
        expectedRussia = "Добро пожаловать";
        expectedOther = "Welcome";
        //Данные для проверки MessageSender
        geoServiceMock = Mockito.spy(GeoService.class);
        Mockito.when(geoServiceMock.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, null,  0));
        Mockito.when(geoServiceMock.byIp("96.44.183.1")).thenReturn(new Location("New York", Country.USA, null,  0));
        Mockito.when(geoServiceMock.byIp("96.44.183.2")).thenReturn(new Location("New York", Country.USA, null,  0));

        Mockito.when(geoServiceMock.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null,  0));
        Mockito.when(geoServiceMock.byIp("172.0.32.10")).thenReturn(new Location("Moscow", Country.RUSSIA, null,  0));
        Mockito.when(geoServiceMock.byIp("172.0.32.1")).thenReturn(new Location("Moscow", Country.RUSSIA, null,  0));

        localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn(expectedRussia);
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn(expectedOther);
    }

    @ParameterizedTest
    @ValueSource(strings = {"172.0.32.11", "172.0.32.10", "172.0.32.1"})
    void senderRussianTest(String sourceIp) {

        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, sourceIp);
        String result = messageSender.send(headers);
        Assertions.assertEquals(expectedRussia, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.44.183.149", "96.44.183.1", "96.44.183.2"})
    void senderUSATest(String sourceIp) {

        MessageSender messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, sourceIp);

        Assertions.assertEquals(expectedOther, messageSender.send(headers));
    }
}
