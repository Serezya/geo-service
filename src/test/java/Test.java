import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;


public class Test {

    @org.junit.jupiter.api.Test
    public void testSenderRu() {
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localService);

        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers));
    }

    @org.junit.jupiter.api.Test
    public void testSenderUSA() {
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.USA)).thenReturn("Welcome");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localService);

        Assertions.assertEquals("Welcome", messageSender.send(headers));
    }

    @org.junit.jupiter.api.Test
    public void testLocalIp() {
        Location location = new GeoServiceImpl().byIp(GeoServiceImpl.LOCALHOST);
        Location expectedLoc = new Location(null, null, null, 0);
        Assertions.assertSame(expectedLoc.getCountry(), location.getCountry());
    }

    @org.junit.jupiter.api.Test
    public void testNewYorkIp() {
        Location location = new GeoServiceImpl().byIp(GeoServiceImpl.NEW_YORK_IP);
        Location expectedLoc = new Location(null, Country.USA, null, 0);
        Assertions.assertSame(expectedLoc.getCountry(), location.getCountry());
    }

    @org.junit.jupiter.api.Test
    public void testMoscowkIp() {
        Location location = new GeoServiceImpl().byIp(GeoServiceImpl.MOSCOW_IP);
        Location expectedLoc = new Location(null, Country.RUSSIA, null, 0);
        Assertions.assertSame(expectedLoc.getCountry(), location.getCountry());
    }

    @org.junit.jupiter.api.Test
    public void localeRu() {
        String result = new LocalizationServiceImpl().locale(Country.RUSSIA);
        Assertions.assertEquals(result, "Добро пожаловать");
    }

    @org.junit.jupiter.api.Test
    public void localeUSA() {
        String result = new LocalizationServiceImpl().locale(Country.USA);
        Assertions.assertEquals(result, "Welcome");
    }

    @org.junit.jupiter.api.Test
    public void localeGermany() {
        String result = new LocalizationServiceImpl().locale(Country.GERMANY);
        Assertions.assertEquals(result, "Willkommen");
    }
}
