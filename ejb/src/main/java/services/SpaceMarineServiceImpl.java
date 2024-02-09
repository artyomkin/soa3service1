package services;

import entities.domain.MeleeWeapon;
import org.jboss.ejb3.annotation.Pool;
import repo.DB;
import services.requests.SpaceMarineRequest;
import services.requests.SpaceMarineSearchRequest;
import services.responses.*;
import services.responses.exception_response.UnexpectedError;
import entities.SpaceMarine;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Pool(value="soa3ejbSpaceMarinePool")
public class SpaceMarineServiceImpl implements SpaceMarineServiceBean {
    private DB db;

    public SpaceMarineServiceImpl() throws SQLException, ClassNotFoundException {
        this.db = new DB();
    }

    public XMLResponse getAll(SpaceMarineSearchRequest request) throws ClassNotFoundException, SQLException {
        Map<String, String> parameters;
        try {
            parameters = convertToParameters(request);
        } catch (IllegalAccessException e) {
            UnexpectedError response = new UnexpectedError("Cannot process request parameters because of illegal access to fields.");
            return response;
        }
        if (!sortIsValid(parameters.get("sort"), parameters.get("order"))){
            SpaceMarineSearchWrongFieldsXMLResponse response = new SpaceMarineSearchWrongFieldsXMLResponse();
            response.setWrongFields(Arrays.asList("sort", "order"));
            return response;
        }

        Map<String, String> searchParameters = parameters.entrySet().stream()
                .filter(parameter -> !Arrays.asList("page", "size", "sort", "order", "coordinatesX", "coordinatesY", "loyal", "meleeWeapon", "chapterName", "chapterParentLegion", "chapterWorld").contains(parameter.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<SpaceMarine> result = this.db.findAllSpaceMarines(searchParameters);

        Map<String, String> finalParameters = parameters;
        if (parameters.get("coordinatesX") != null){
            try{
                result = result.stream()
                        .filter(spaceMarine -> spaceMarine.getCoordinates().getX().equals(Double.valueOf(finalParameters.get("coordinatesX"))))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e){
                result.clear();
            }
        }
        if (parameters.get("coordinatesY") != null){
            try{
            result = result.stream()
                    .filter(spaceMarine -> Integer.valueOf(spaceMarine.getCoordinates().getY()).equals(Double.valueOf(finalParameters.get("coordinatesY"))))
                    .collect(Collectors.toList());
            } catch (NumberFormatException e){
                result.clear();
            }
        }
        if (parameters.get("loyal") != null){
            result = result.stream()
                    .filter(spaceMarine -> Boolean.toString(spaceMarine.getLoyal()).equals(finalParameters.get("loyal")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("meleeWeapon") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine
                            .getMeleeWeapon()
                            .toString()
                            .equals(finalParameters.get("meleeWeapon")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("chapterName") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getName().equals(finalParameters.get("chapterName")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("chapterParentLegion") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getParentLegion().equals(finalParameters.get("chapterParentLegion")))
                    .collect(Collectors.toList());
        }
        if (parameters.get("chapterWorld") != null){
            result = result.stream()
                    .filter(spaceMarine -> spaceMarine.getChapter().getWorld().equals(finalParameters.get("chapterWorld")))
                    .collect(Collectors.toList());
        }

        return SpaceMarineListXMLResponse.ok(result);
    }
    public XMLResponse save(SpaceMarineRequest spaceMarineRequest) {
        try {
            SpaceMarine spaceMarine = new SpaceMarine(spaceMarineRequest);
            if (spaceMarine.getStarshipId() != null && !this.db.starshipExistsById(spaceMarine.getStarshipId())){
                throw new IllegalArgumentException("starshipId");
            }
            if (this.db.chapterExistsByName(spaceMarine.getChapter().getName())){
                throw new IllegalArgumentException("chapterName");
            }
            long savedSpaceMarineId = this.db.save(spaceMarine);
            SpaceMarine savedSpaceMarine = this.db.findSpaceMarineById(savedSpaceMarineId);
            return new SpaceMarineXMLResponse(savedSpaceMarine);
        } catch (IllegalArgumentException e){
            SpaceMarineSearchWrongFieldsXMLResponse errorResponse = new SpaceMarineSearchWrongFieldsXMLResponse();
            errorResponse.setWrongFields(Arrays.asList(e.getMessage()));
            return errorResponse;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Map<String, String> convertToParameters(SpaceMarineSearchRequest request) throws IllegalAccessException{
       Map<String, String> resultParameters = Arrays.stream(request.getClass().getDeclaredFields())
               .filter(field -> {
                   try {
                       field.setAccessible(true);
                       return field.get(request) != null;
                   } catch (IllegalAccessException e) {
                       throw new RuntimeException(e);
                   }
               })
               .map(field -> {
                   field.setAccessible(true);
                   try {
                       return new AbstractMap.SimpleEntry<String, String>(field.getName(), field.get(request).toString());
                   } catch (IllegalAccessException e) {
                       throw new RuntimeException(e);
                   }
               })
               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return resultParameters;
    }

    private boolean sortIsValid(String sort, String order) {
        return sort == null && order == null || sort != null && order != null;
    }

    public XMLResponse findById(Long id) {
        SpaceMarineXMLResponse response = new SpaceMarineXMLResponse();
        try {
            response.setSpaceMarine(this.db.findSpaceMarineById(id));
            response.setCode(200);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public XMLResponse update(Long id, SpaceMarineRequest spaceMarineRequest) {
        SpaceMarineXMLResponse response = new SpaceMarineXMLResponse();
        try {
            if (this.db.spaceMarineExistsById(id)){
                try {
                    SpaceMarine spaceMarine = new SpaceMarine(spaceMarineRequest);
                    spaceMarine.setId(id);
                    if (!this.db.chapterExistsByName(spaceMarine.getChapter().getName())){
                        this.db.save(spaceMarine.getChapter());
                    }
                    if (spaceMarine.getStarshipId() != null && !this.db.starshipExistsById(spaceMarine.getStarshipId())){
                        throw new IllegalArgumentException("starshipId");
                    }
                    Long savedId = this.db.update(spaceMarine.getId(), spaceMarine);
                    SpaceMarine savedSpaceMarine = this.db.findSpaceMarineById(savedId);
                    response.setSpaceMarine(savedSpaceMarine);
                } catch (IllegalArgumentException e){
                    SpaceMarineSearchWrongFieldsXMLResponse errorResposne = new SpaceMarineSearchWrongFieldsXMLResponse();
                    errorResposne.setWrongFields(Arrays.asList(e.getMessage()));
                    return errorResposne;
                }
            } else {
                SpaceMarineSearchWrongFieldsXMLResponse errorResposne = new SpaceMarineSearchWrongFieldsXMLResponse();
                errorResposne.setWrongFields(Arrays.asList("id"));
                return errorResposne;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public void delete(Long id) {
        try {
            if (!this.db.spaceMarineExistsById(id)){
                return;
            }
            this.db.deleteSpaceMarineById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByMeleeWeapon(MeleeWeapon meleeWeapon) {
        try {
            this.db.findSpaceMarinesByMeleeWeapon(meleeWeapon).stream()
                            .forEach(spaceMarine -> {
                                try {
                                    this.db.deleteSpaceMarineById(spaceMarine.getId());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public XMLResponse getMinByCoords() {
        try {
            List<SpaceMarine> spaceMarines = this.db.findSpaceMarinesByMinCoords();
            return new SpaceMarineXMLResponse(spaceMarines.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer countByHealth(Double health){
        try {
            return this.db.countByHealth(health);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
