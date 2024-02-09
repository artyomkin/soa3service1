package services.responses;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="unloadResponse")
public class UnloadResponse implements XMLResponse, Serializable {
    @Override
    public Integer getCode() {
        return 204;
    }
}
