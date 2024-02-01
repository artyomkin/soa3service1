package services.responses;

import entities.SpaceMarine;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="SpaceMarines")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SpaceMarineListXMLResponse implements XMLResponse{

    @XmlElement(name="spaceMarines")
    private List<SpaceMarine> spaceMarines;

    public SpaceMarineListXMLResponse(List<SpaceMarine> spaceMarines){
        this.spaceMarines = spaceMarines;
    }
    public SpaceMarineListXMLResponse(){
    }

    public static SpaceMarineListXMLResponse ok(List<SpaceMarine> spaceMarines){
        return new SpaceMarineListXMLResponse(spaceMarines);
    }

    @Override
    public Integer getCode() {
        return 200;
    }

    public List<SpaceMarine> getSpaceMarines() {
        return spaceMarines;
    }

    public void setSpaceMarines(List<SpaceMarine> spaceMarines) {
        this.spaceMarines = spaceMarines;
    }
}
