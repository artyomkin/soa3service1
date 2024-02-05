package controllers;

import dto.XMLParser;
import entities.domain.MeleeWeapon;
import exceptions.NotFoundException;
import serviceBeans.EJBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import services.SpaceMarineServiceBean;
import services.requests.SpaceMarineRequest;
import services.requests.SpaceMarineSearchRequest;
import services.requests.constraints.SortException;
import services.responses.SpaceMarineListXMLResponse;
import services.responses.SpaceMarineSearchWrongFieldsXMLResponse;
import services.responses.SpaceMarineXMLResponse;
import services.responses.XMLResponse;
import services.responses.exception_response.UnexpectedError;

import javax.naming.NamingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import javax.xml.bind.JAXBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Validated
@RequestMapping(value = "/api/v1/space-marines")
public class SpaceMarinesController {
    SpaceMarineServiceBean spaceMarineService = EJBFactory.createSpaceMarineServiceFromJNDI();
    @Autowired
    XMLParser<XMLResponse> parser;

    public SpaceMarinesController() throws NamingException {
    }

    @GetMapping
    public ResponseEntity getAllMarines(@Valid SpaceMarineSearchRequest request) throws SortException, SQLException, ClassNotFoundException {
        XMLResponse response = spaceMarineService.getAll(request);
        try{
            return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        } catch (JAXBException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createSpaceMarine(@Valid @RequestBody SpaceMarineRequest spaceMarineRequest) throws JAXBException {
        XMLResponse response = (XMLResponse) spaceMarineService.save(spaceMarineRequest);
        try {
            return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMarineById(@PathVariable Long id){
        XMLResponse response = spaceMarineService.findById(id);
        try{
            return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        } catch (JAXBException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateSpaceMarine(@Valid @RequestBody SpaceMarineRequest spaceMarineRequest, @PathVariable Long id) throws JAXBException {
        XMLResponse response = spaceMarineService.update(id, spaceMarineRequest);
        try {
            return new ResponseEntity(parser.convertToXML(response), HttpStatus.valueOf(response.getCode()));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSpaceMarine(@PathVariable Long id) {
        spaceMarineService.delete(id);
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/melee-weapon/{meleeWeapon}")
    public ResponseEntity deleteSpaceMarine(@PathVariable MeleeWeapon meleeWeapon){
        spaceMarineService.deleteByMeleeWeapon(meleeWeapon);
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    @GetMapping("/coords/min")
    public ResponseEntity getMinSpaceMarine() {
        XMLResponse response = spaceMarineService.getMinByCoords();
        try {
            return ResponseEntity.ok(parser.convertToXML(response));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/health/{health}")
    public ResponseEntity countWithHealthLessThan(@PathVariable Double health) {
        Integer count = spaceMarineService.countByHealth(health);
        return ResponseEntity.ok("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<SpaceMarinesCount>" + count.toString() + "</SpaceMarinesCount>");
    }

    //@ExceptionHandler({ConstraintViolationException.class})
    //public ResponseEntity handleBindException(ConstraintViolationException exception) throws JAXBException {
    //    SpaceMarineSearchWrongFieldsXMLResponse response = new SpaceMarineSearchWrongFieldsXMLResponse();
    //    List<String> constraintViolations = exception.getConstraintViolations().stream()
    //            .map(violation -> violation.getMessage())
    //            .collect(Collectors.toList());
    //    response.setWrongFields(constraintViolations);
    //    return ResponseEntity.badRequest().body(parser.convertToXML(response));
    //}

    //@ExceptionHandler({MethodArgumentNotValidException.class})
    //public ResponseEntity handleMethodException(MethodArgumentNotValidException exception) throws JAXBException {
    //    SpaceMarineSearchWrongFieldsXMLResponse response = new SpaceMarineSearchWrongFieldsXMLResponse();
    //    List<String> constraintViolations = exception.getBindingResult().getAllErrors().stream()
    //            .map(error -> error.getDefaultMessage())
    //            .collect(Collectors.toList());
    //    response.setWrongFields(constraintViolations);
    //    return ResponseEntity.badRequest().body(parser.convertToXML(response));
    //}

    //@ExceptionHandler({DataIntegrityViolationException.class})
    //public ResponseEntity handleDataIntegrityViolation(DataIntegrityViolationException exception) throws JAXBException {
    //    return ResponseEntity.badRequest().body(parser.convertToXML(new UnexpectedError(400, "Incorrect value in request.")));
    //}

    //@ExceptionHandler({NumberFormatException.class})
    //public ResponseEntity handleNumberFormat(NumberFormatException exception) throws JAXBException {
    //    return ResponseEntity.badRequest().body(parser.convertToXML(new UnexpectedError(400, "Incorrect value in URL.")));
    //}

    //@ExceptionHandler({NoSuchElementException.class})
    //public ResponseEntity handleNoSuchElement(NoSuchElementException exception) throws JAXBException {
    //    return ResponseEntity.badRequest().body(parser.convertToXML(new SpaceMarineXMLResponse()));
    //}

    //@ExceptionHandler({Exception.class})
    //public ResponseEntity handleError(Exception e) throws JAXBException {
    //    return ResponseEntity.internalServerError().body(parser.convertToXML(new UnexpectedError("Unexpected exception.")));
    //}

    //@ExceptionHandler({BindException.class})
    //public ResponseEntity handleError(BindException e) throws JAXBException {
    //    return ResponseEntity.badRequest().body(parser.convertToXML(new UnexpectedError(400,"Incorrect value in filter.")));
    //}

}
