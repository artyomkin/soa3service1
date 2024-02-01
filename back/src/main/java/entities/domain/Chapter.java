package entities.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="chapter")
public class Chapter {
    @NotNull(message = "chapterName")
    private String name;
    @NotNull(message = "parentLegion")
    private String parentLegion;
    @NotNull(message = "world")
    private String world;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
