package services;

import services.responses.XMLResponse;

import javax.ejb.Remote;

@Remote
public interface StarshipServiceBean {
    public XMLResponse save(Integer id, String name);

    public XMLResponse findAll();
    public XMLResponse unload(Integer starshipId, Long spaceMarineId);
}
