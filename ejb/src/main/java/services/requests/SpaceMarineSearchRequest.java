package services.requests;

import services.requests.constraints.SortConstraint;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@XmlRootElement(name="SpaceMarine")
public class SpaceMarineSearchRequest implements Serializable {
    private Integer id;


    private String creationDate;
    @Min(value = 1, message = "page")
    @Max(value = 999999999, message = "page")
    private Integer page = 1;
    @Min(value = 1, message = "size")
    @Max(value = 999999999, message = "size")
    private Integer size = 1000;
    @SortConstraint
    private String sort;
    private CustomSortDirection order;
    private String name;
    @Digits(integer = 999999999, fraction = 5, message = "coordinatesX")
    private Long coordinatesX;
    @Digits(integer = 999999999, fraction = 5, message = "coordinatesY")
    private Integer coordinatesY;
    private String loyal;
    @Min(value = 0, message = "health")
    @Digits(integer = 999999999, fraction = 5, message = "health")
    @Max(value = 999999999, message = "health")
    private Double health;
    @Min(value = 0, message = "height")
    @Digits(integer = 999999999, fraction = 5, message = "height")
    @Max(value = 999999999, message = "health")
    private Double height;
    private String meleeWeapon;
    private String chapterName;
    private String chapterParentLegion;
    private String chapterWorld;
    private Integer starshipId;
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(Long coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public Integer getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(Integer coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public CustomSortDirection getOrder() {
        return order;
    }

    public void setOrder(CustomSortDirection order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLoyal() {
        return loyal;
    }

    public void setLoyal(String loyal) {
        this.loyal = loyal;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(String meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterParentLegion() {
        return chapterParentLegion;
    }

    public void setChapterParentLegion(String chapterParentLegion) {
        this.chapterParentLegion = chapterParentLegion;
    }

    public String getChapterWorld() {
        return chapterWorld;
    }

    public void setChapterWorld(String chapterWorld) {
        this.chapterWorld = chapterWorld;
    }

    public Integer getStarshipId() {
        return starshipId;
    }

    public void setStarshipId(Integer starshipId) {
        this.starshipId = starshipId;
    }
}
