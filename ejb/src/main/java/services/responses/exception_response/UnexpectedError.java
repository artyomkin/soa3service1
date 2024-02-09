package services.responses.exception_response;

import services.responses.XMLResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="UnexpectedError")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class UnexpectedError implements XMLResponse, Serializable {
    @XmlElement(name="code")
    public Integer code;
    @XmlElement(name="msg")
    public String msg;
    public UnexpectedError(Integer code, String msg){
        this.msg = msg;
        this.code = code;
    }
    public UnexpectedError(String msg){
        this.msg = msg;
        this.code = 500;
    }
    public UnexpectedError(){
        this.msg = "Unexpected server error.";
        this.code = 500;
    }

    public Integer getCode(){
        return this.code;
    }
}
