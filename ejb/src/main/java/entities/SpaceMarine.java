package entities;

import services.requests.SpaceMarineRequest;
import entities.domain.Chapter;
import entities.domain.Coordinates;
import entities.domain.MeleeWeapon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "space_marine")
@XmlRootElement(name="spaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(nillable = true)
    private long id;
    @XmlElement(nillable = true)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="coordinates_id", referencedColumnName = "id")
    @XmlElement(nillable = true)
    private Coordinates coordinates;
    @XmlTransient
    private Date creationDate;

    @XmlElement(name = "creationDate")
    private String creationDateStr;
    @XmlElement(nillable = true)
    private float health;
    @XmlElement(nillable = true)
    private boolean loyal;
    @XmlElement(nillable = true)
    private double height;

    @XmlElement(nillable = true)
    private MeleeWeapon meleeWeapon;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="chapter_name", referencedColumnName = "name")
    @XmlElement(nillable = true)
    private Chapter chapter;
    @XmlElement(nillable = true)
    private Integer starshipId;

    public Integer getStarshipId() {
        return starshipId;
    }

    public SpaceMarine(){
    }
    public SpaceMarine(SpaceMarineRequest req){
        this.name = req.getName();
        this.coordinates = req.getCoordinates();
        this.creationDate = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        DateFormat df = new SimpleDateFormat(pattern);
        this.creationDateStr = df.format(this.creationDate);
        this.creationDateStr = this.creationDateStr.replaceAll("\\s+", "T");
        this.creationDateStr += "Z";
        this.health = req.getHealth();
        if (req.getLoyal() == null || req.getLoyal().equals("true")){
            this.loyal = true;
        } else if (req.getLoyal().equals("false")) {
            this.loyal = false;
        } else {
            throw new IllegalArgumentException("loyal");
        }

        this.height = req.getHeight();
        this.meleeWeapon = req.getMeleeWeapon();
        this.chapter = req.getChapter();
        this.starshipId = req.getStarshipId();
    }

    public boolean getLoyal() {
        return this.loyal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isLoyal() {
        return loyal;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    public void setStarshipId(Integer starshipId) {
        this.starshipId = starshipId;
    }
}
