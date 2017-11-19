package net.ujacha.weather;

import net.ujacha.weather.service.LocationGridService;
import net.ujacha.weather.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApiTest {

    @Autowired
    private LocationGridService locationGridService;

    @Autowired
    private WeatherService weatherService;

    @Test
    public void test(){

        LocationGrid locationGrid = locationGridService.getLocationGrid("미아");

        System.out.println(locationGrid);

        WeatherInfo weatherInfo =  weatherService.getWeatherInfo("");

        System.out.println(weatherInfo);




    }

}
