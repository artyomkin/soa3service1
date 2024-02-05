package services.responses;

import entities.Starship;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name="Starships")
@XmlAccessorType(XmlAccessType.FIELD)
public class StarshipXMLResponse implements XMLResponse, Serializable {
    @XmlElement(name="starship")
    private List<Starship> starships;

    public StarshipXMLResponse(){}

    public void setStarships(List<Starship> starships){
        this.starships = starships;
    }
    @Override
    public Integer getCode() {
        return 201;
    }
}
