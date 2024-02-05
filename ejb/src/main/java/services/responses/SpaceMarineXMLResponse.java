package services.responses;

import entities.SpaceMarine;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlRootElement(name="SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SpaceMarineXMLResponse implements XMLResponse, Serializable {

    private SpaceMarine spaceMarine;
    @XmlTransient
    private Integer code;

    public SpaceMarineXMLResponse(SpaceMarine spaceMarine, Integer code){
        this.spaceMarine = spaceMarine;
        this.code = code;
    }

    public SpaceMarineXMLResponse(SpaceMarine spaceMarine){
        this.spaceMarine = spaceMarine;
        this.code = 201;
    }
    public SpaceMarineXMLResponse(){
        this.code = 201;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public SpaceMarine getSpaceMarine() {
        return spaceMarine;
    }

    public void setSpaceMarine(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }
}

