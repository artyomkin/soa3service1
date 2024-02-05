package services;

import entities.domain.MeleeWeapon;
import services.requests.SpaceMarineRequest;
import services.requests.SpaceMarineSearchRequest;
import services.responses.XMLResponse;

import javax.ejb.Remote;
import java.sql.SQLException;

@Remote
public interface SpaceMarineServiceBean {

    public XMLResponse getAll(SpaceMarineSearchRequest spaceMarineSearchRequest) throws SQLException, ClassNotFoundException;
    public XMLResponse findById(Long id);
    public XMLResponse update(Long id, SpaceMarineRequest spaceMarineRequest);
    public void delete(Long id);
    public void deleteByMeleeWeapon(MeleeWeapon meleeWeapon) ;
    public XMLResponse getMinByCoords() ;
    public Integer countByHealth(Double health);
    public XMLResponse save(SpaceMarineRequest req);
}
