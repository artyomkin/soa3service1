package entities.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@Entity
@Getter
@Setter
@XmlRootElement(name="coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
