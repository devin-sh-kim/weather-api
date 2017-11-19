package net.ujacha.weather;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WeatherInfo {

    private String requestLocation;
    private LocationGrid locationGrid;

    private String temperature;
    private String humidity;

    public String getRequestLocation() {
        return requestLocation;
    }

    public void setRequestLocation(String requestLocation) {
        this.requestLocation = requestLocation;
    }

    public LocationGrid getLocationGrid() {
        return locationGrid;
    }

    public void setLocationGrid(LocationGrid locationGrid) {
        this.locationGrid = locationGrid;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
