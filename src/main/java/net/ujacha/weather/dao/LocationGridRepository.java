package net.ujacha.weather.dao;

import net.ujacha.weather.LocationGrid;
import org.springframework.data.repository.CrudRepository;


public interface LocationGridRepository extends CrudRepository<LocationGrid, Long> {

    public LocationGrid findTopByLevel3IsStartingWith(String level3);

    public LocationGrid findTopByLevel2IsStartingWith(String level2);

    public LocationGrid findTopByLevel1IsStartingWith(String level1);

    public LocationGrid findTopByLevel1(String level1);


}
