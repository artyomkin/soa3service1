package services;

import entities.domain.MeleeWeapon;
import services.requests.SpaceMarineRequest;
import services.requests.SpaceMarineSearchRequest;
import services.responses.XMLResponse;

import javax.ejb.Stateful;
import java.sql.SQLException;

@Stateful
public class SpaceMarineServiceBeanImpl implements SpaceMarineServiceBean {

    SpaceMarineService spaceMarineService = new SpaceMarineService();

    public SpaceMarineServiceBeanImpl() throws SQLException, ClassNotFoundException {
    }

    public XMLResponse getAll(SpaceMarineSearchRequest spaceMarineSearchRequest) throws SQLException, ClassNotFoundException {
        System.out.println("getAll bean.");
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
    public XMLResponse save(SpaceMarineRequest req) { return spaceMarineService.save(req);}

}
