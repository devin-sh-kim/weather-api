package net.ujacha.weather.controller;

import net.ujacha.weather.WeatherInfo;
import net.ujacha.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class WeatherRestController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("now")
    public WeatherInfo get(@RequestParam String location) {

        WeatherInfo weatherInfo = weatherService.getWeatherInfo(location);

        return weatherInfo;

    }

}
