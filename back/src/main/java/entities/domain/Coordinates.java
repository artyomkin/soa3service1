package entities.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@XmlRootElement(name="coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @XmlTransient
    private Integer id;
    @NotNull(message = "coordinatesX")
    private Long x;
    @NotNull(message = "coordinatesY")
    private int y;

    public Coordinates(){};
    public Coordinates(Long coordinatesX, int coordinatesY) {
        this.x = coordinatesX;
        this.y = coordinatesY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
