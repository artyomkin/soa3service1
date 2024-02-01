package services.responses;

import entities.SpaceMarine;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SpaceMarineXMLResponse implements XMLResponse{

    private SpaceMarine spaceMarine;

    public SpaceMarineXMLResponse(SpaceMarine spaceMarine){
        this.spaceMarine = spaceMarine;
    }
    public SpaceMarineXMLResponse(){
    }

    @Override
    public Integer getCode() {
        return 201;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }

    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }
}

