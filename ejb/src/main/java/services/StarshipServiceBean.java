package services;

import org.springframework.beans.factory.annotation.Autowired;
import services.responses.XMLResponse;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote
public class StarshipServiceBean {
    @Autowired
    StarshipService starshipService;

    public XMLResponse save(Integer id, String name){
        return starshipService.save(id, name);
    }

    public XMLResponse findAll() {
        return starshipService.findAll();
    }

    public XMLResponse unload(Integer starshipId, Long spaceMarineId) {
        return starshipService.unload(starshipId, spaceMarineId);
    }
}
