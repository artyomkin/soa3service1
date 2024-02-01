package services;

import entities.domain.MeleeWeapon;
import org.springframework.beans.factory.annotation.Autowired;
import services.requests.SpaceMarineRequest;
import services.requests.SpaceMarineSearchRequest;
import services.responses.XMLResponse;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote
public class SpaceMarineServiceBean {

    @Autowired
    SpaceMarineService spaceMarineService;
    public XMLResponse getAll(SpaceMarineSearchRequest spaceMarineSearchRequest){
        return spaceMarineService.getAll(spaceMarineSearchRequest);
    }
    public XMLResponse findById(Long id) {
        return spaceMarineService.findById(id);
    }
    public XMLResponse update(Long id, SpaceMarineRequest spaceMarineRequest) {
        return spaceMarineService.update(id, spaceMarineRequest);
    }
    public void delete(Long id) {
        spaceMarineService.delete(id);
    }
    public void deleteByMeleeWeapon(MeleeWeapon meleeWeapon) {
        spaceMarineService.deleteByMeleeWeapon(meleeWeapon);
    }

    public XMLResponse getMinByCoords() {
        return spaceMarineService.getMinByCoords();
    }

    public Integer countByHealth(Double health){
        return spaceMarineService.countByHealth(health);
    }

}
