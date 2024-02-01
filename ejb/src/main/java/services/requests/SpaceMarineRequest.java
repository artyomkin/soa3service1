package services.requests;

import entities.domain.Chapter;
import entities.domain.Coordinates;
import entities.domain.MeleeWeapon;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineRequest {
    @NotNull(message = "name")
    private String name;
    @NotNull(message = "coordinates")
    private Coordinates coordinates;
    @Min(value = 0, message = "health")
    @Max(value = 999999999, message = "health")
    @NotNull(message = "health")
    private float health;
    @Nullable
    private String loyal;
    @Min(value = 0, message = "height")
    @Max(value = 999999999, message = "height")
    @Nullable
    private Double height;
    @NotNull(message = "meleeWeapon")
    private MeleeWeapon meleeWeapon;
    @NotNull(message = "chapter")
    private Chapter chapter;
    private Integer starshipId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    @Nullable
    public String getLoyal() {
        return loyal;
    }

    public void setLoyal(@Nullable String loyal) {
        this.loyal = loyal;
    }

    @Nullable
    public Double getHeight() {
        return height;
    }

    public void setHeight(@Nullable Double height) {
        this.height = height;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Integer getStarshipId() {
        return starshipId;
    }

    public void setStarshipId(Integer starshipId) {
        this.starshipId = starshipId;
    }
}

