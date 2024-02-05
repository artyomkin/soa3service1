package services;

import services.responses.XMLResponse;

import javax.ejb.Remote;
import javax.ejb.Stateless;

public class StarshipServiceBeanImpl implements StarshipServiceBean {
    StarshipService starshipService = new StarshipService();

    //public XMLResponse save(Integer id, String name){
    //    return starshipService.save(id, name);
    //}

    //public XMLResponse findAll() {
    //    return starshipService.findAll();
    //}

    //public XMLResponse unload(Integer starshipId, Long spaceMarineId) {
    //    return starshipService.unload(starshipId, spaceMarineId);
    //}
}
