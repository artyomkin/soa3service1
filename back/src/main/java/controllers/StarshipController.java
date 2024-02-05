package controllers;

import dto.XMLParser;
import serviceBeans.EJBFactory;
import services.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.StarshipServiceBean;
import services.responses.XMLResponse;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("/api/v1/starships")
public class StarshipController {
    @Autowired
    XMLParser<XMLResponse> parser;
    StarshipServiceBean starshipService = EJBFactory.createStarshipServiceFromJNDI();

    public StarshipController() throws NamingException {
    }

    @PostMapping("{id}/{name}")
    public String /*ResponseEntity*/ createStarship(@PathVariable("id") Integer id, @PathVariable("name") String name){
        return "Starship get by id";
        //XMLResponse response = starshipService.save(id, name);
        //try {
        //    return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        //} catch (JAXBException e) {
        //    return ResponseEntity.internalServerError().body(e.getMessage());
        //}
    }

    @GetMapping
    public String /*ResponseEntity*/ getStarships(){
        return "Starship get starships";
        //XMLResponse response = starshipService.findAll();
        //try {
        //    return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(200));
        //} catch (JAXBException e) {
        //    return ResponseEntity.internalServerError().body(e.getMessage());
        //}
    }

    @PutMapping("/{starshipId}/unload/{spaceMarineId}")
    public String /*ResponseEntity*/ unload(@PathVariable("starshipId") Integer starshipId, @PathVariable("spaceMarineId") Long spaceMarineId) {
        return "Starship unload";
        //XMLResponse response = starshipService.unload(starshipId, spaceMarineId);
        //try {
        //    if (response == null){
        //       return new ResponseEntity(HttpStatus.valueOf(204));
        //    }
        //    return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        //} catch (JAXBException e) {
        //    return ResponseEntity.internalServerError().body(e.toString());
        //}
    }
}
