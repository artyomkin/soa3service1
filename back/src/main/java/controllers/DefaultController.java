package controllers;

import dto.XMLParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import services.responses.XMLResponse;
import services.responses.exception_response.UnexpectedError;

import javax.xml.bind.JAXBException;

@Controller
public class DefaultController {
    XMLParser<XMLResponse> parser = new XMLParser<>();
    @RequestMapping("/**")
    public ResponseEntity defaultController() throws JAXBException {
        return new ResponseEntity(parser.convertToXML(new UnexpectedError(404, "SOA3 endpoint not found.")), HttpStatus.valueOf(404));
    }

}
