package services;

import services.responses.LandXMLWrongFields;
import services.responses.StarshipWrongFieldsXMLResponse;
import services.responses.StarshipXMLResponse;
import services.responses.XMLResponse;
import services.responses.exception_response.UnexpectedError;
import entities.SpaceMarine;
import entities.Starship;
import repos.SpaceMarineRepository;
import repos.StarshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class StarshipService {
    @Autowired
    StarshipRepo starshipRepo;
    @Autowired
    SpaceMarineRepository spaceMarineRepository;

    public XMLResponse save(Integer id, String name){
        if (starshipRepo.findById(id).isPresent()){
            StarshipWrongFieldsXMLResponse response = new StarshipWrongFieldsXMLResponse();
            response.setWrongFields(Arrays.asList("id"));
            return response;
        }
        Starship starship = new Starship();
        starship.setId(id);
        starship.setName(name);
        StarshipXMLResponse response = new StarshipXMLResponse();
        response.setStarships(Arrays.asList(starshipRepo.save(starship)));
        return response;
    }

    public XMLResponse findAll() {
        StarshipXMLResponse response = new StarshipXMLResponse();
        response.setStarships(starshipRepo.findAll());
        return response;
    }

    public XMLResponse unload(Integer starshipId, Long spaceMarineId) {
        Optional<SpaceMarine> optionalSpaceMarine = spaceMarineRepository.findById(spaceMarineId);
        if (optionalSpaceMarine.isEmpty()){
            LandXMLWrongFields response = new LandXMLWrongFields();
            response.setWrongFields(Arrays.asList("spaceMarineId"));
            return response;
        }
        if (starshipRepo.findById(starshipId).isEmpty()){
            LandXMLWrongFields response = new LandXMLWrongFields();
            response.setWrongFields(Arrays.asList("starshipId"));
            return response;
        }
        SpaceMarine spaceMarine = optionalSpaceMarine.get();
        try{
            if (!spaceMarine.getStarshipId().equals(starshipId)){
                return new UnexpectedError(401, "Space marine was not on starship " + starshipId.toString() + ".");
            }
        } catch (NullPointerException e){
            return new UnexpectedError(401, "Space marine was not on starship " + starshipId.toString() + ".");
        }
        spaceMarine.setStarshipId(null);
        spaceMarineRepository.save(spaceMarine);
        XMLResponse response = new XMLResponse() {
            public Integer getCode() {
                return 204;
            }
        };
        return null;
    }
}
