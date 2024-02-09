package services;

import org.jboss.ejb3.annotation.Pool;
import repo.DB;
import services.responses.*;
import services.responses.exception_response.UnexpectedError;
import entities.SpaceMarine;
import entities.Starship;

import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.Arrays;

@Stateless
@Pool(value="soa3ejbStarshipPool")
public class StarshipServiceBeanImpl implements StarshipServiceBean {

    private DB db;

    public StarshipServiceBeanImpl(){
        try {
            this.db = new DB();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public XMLResponse save(Integer id, String name){
        try {
            if (this.db.starshipExistsById(id)){
                StarshipWrongFieldsXMLResponse response = new StarshipWrongFieldsXMLResponse();
                response.setWrongFields(Arrays.asList("id"));
                return response;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Starship starship = new Starship();
        starship.setId(id);
        starship.setName(name);
        StarshipXMLResponse response = new StarshipXMLResponse();
        try {
            this.db.save(starship);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.setStarships(Arrays.asList(starship));
        return response;
    }

    public XMLResponse findAll() {
        StarshipXMLResponse response = new StarshipXMLResponse();
        try {
            response.setStarships(this.db.findAllStarships());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public XMLResponse unload(Integer starshipId, Long spaceMarineId) {
        try {
            if (!this.db.spaceMarineExistsById(spaceMarineId)){
                LandXMLWrongFields response = new LandXMLWrongFields();
                response.setWrongFields(Arrays.asList("spaceMarineId"));
                return response;
            }
            if (!this.db.starshipExistsById(starshipId)){
                LandXMLWrongFields response = new LandXMLWrongFields();
                response.setWrongFields(Arrays.asList("starshipId"));
                return response;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SpaceMarine spaceMarine = null;
        try {
            spaceMarine = this.db.findSpaceMarineById(spaceMarineId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            if (!spaceMarine.getStarshipId().equals(starshipId)){
                return new UnexpectedError(401, "Space marine was not on starship " + starshipId.toString() + ".");
            }
        } catch (NullPointerException e){
            return new UnexpectedError(401, "Space marine was not on starship " + starshipId.toString() + ".");
        }
        spaceMarine.setStarshipId(null);
        try {
            this.db.update(spaceMarineId, spaceMarine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        XMLResponse response = new UnloadResponse();
        return response;
    }
}
