package services.responses;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="StarshipWrongFields")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class StarshipWrongFieldsXMLResponse implements XMLResponse {
    @XmlElement(name="wrongField")
    private List<String> wrongFields;

    @Override
    public Integer getCode() {
        return 400;
    }

    public List<String> getWrongFields() {
        return wrongFields;
    }

    public void setWrongFields(List<String> wrongFields) {
        this.wrongFields = wrongFields;
    }
}
