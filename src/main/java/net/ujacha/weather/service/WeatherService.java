package net.ujacha.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import net.ujacha.weather.LocationGrid;
import net.ujacha.weather.WeatherInfo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String SERVICE_KEY = "avR48C7VIsIY3L0jHkh8GXUjVcj5ymiNhtXw4jEx4M3UIdCuh77LBj%2Bn%2BjvGhN5GP5MwdlVmZvwTHTtzn4qndg%3D%3D";

    // 초단기실황조회
    private final static String FORECAST_GRIB_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";

    @Autowired
    private LocationGridService locationGridService;

    public WeatherInfo getWeatherInfo(String location) {


        LocationGrid locationGrid = locationGridService.getLocationGrid(location);

        if (locationGrid == null) {
            locationGrid = locationGridService.getDefault();
        }

        WeatherInfo weatherInfo = null;
        try {
            weatherInfo = getForecastNowByGrid(locationGrid.getGridX(), locationGrid.getGridY());
            weatherInfo.setRequestLocation(location);
            weatherInfo.setLocationGrid(locationGrid);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return weatherInfo;

    }

    private WeatherInfo getForecastNowByGrid(int gridX, int gridY) throws IOException {

        WeatherInfo weatherInfo = new WeatherInfo();

        StringBuffer sb = new StringBuffer(FORECAST_GRIB_URL);

        Date now = new Date();
        SimpleDateFormat formatBaseDate = new SimpleDateFormat("yyyyMMdd");
        String today = formatBaseDate.format(now);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.HOUR, -1);
        SimpleDateFormat formatBaseTime = new SimpleDateFormat("HH00");

        String baseTime = formatBaseTime.format(cal.getTime());


        sb.append("?ServiceKey=").append(SERVICE_KEY)
                .append("&base_date=").append(today)
                .append("&base_time=").append(baseTime)
                .append("&nx=").append(gridX)
                .append("&ny=").append(gridY)
                .append("&_type=json");

        HttpGet get = new HttpGet(sb.toString());

        logger.debug("REQUEST URL: {}", get.getURI());

        CloseableHttpClient client = HttpClients.createDefault();

        String responseBody = client.execute(get, response -> {
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                logger.warn("Unexpected response status: {}", statusCode);
                return null;
            }

        });

        logger.debug("RESPONSE BODY : {}", responseBody);


        if (responseBody != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseBody);

//            logger.debug("{}", node.path("response").path("body").path("items").path("item").isArray());

            JsonNode itemArray = node.path("response").path("body").path("items").path("item");

            for (JsonNode item : itemArray) {
                String category = item.path("category").asText();

//                logger.debug("CATEGORY : {}", category);
                if ("T1H".equals(category)) {
                    // 기온
                    String value = item.path("obsrValue").asText();
                    weatherInfo.setTemperature(value);
                }

                if ("REH".equals(category)) {
                    // 습도
                    String value = item.path("obsrValue").asText();
                    weatherInfo.setHumidity(value);
                }
            }
        }

        return weatherInfo;

    }


}
