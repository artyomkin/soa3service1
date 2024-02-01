package entities;

import services.requests.StarshipRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Starship {
    @Id
    private Integer id;
    private String name;

    public Starship(){}
    public Starship(StarshipRequest request){
        this.id = request.getId();
        this.name = request.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
