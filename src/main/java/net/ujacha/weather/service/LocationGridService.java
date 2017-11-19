package net.ujacha.weather.service;

import net.ujacha.weather.LocationGrid;
import net.ujacha.weather.dao.LocationGridRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationGridService {

    @Autowired
    private LocationGridRepository locationGridRepository;

    public LocationGrid getLocationGrid(String location){

        LocationGrid locationGrid = locationGridRepository.findTopByLevel3IsStartingWith(location);

        if(locationGrid == null){
            locationGrid = locationGridRepository.findTopByLevel2IsStartingWith(location);

            if(locationGrid == null){
                locationGrid = locationGridRepository.findTopByLevel1IsStartingWith(location);
            }

        }



        return locationGrid;

    }

    public LocationGrid getDefault() {

        LocationGrid locationGrid = locationGridRepository.findTopByLevel1("서울특별시");

        return locationGrid;

    }
}
