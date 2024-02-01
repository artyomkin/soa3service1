package interfaces;


import javax.ejb.Remote;

@Remote
public interface HelloEJB {
    public String hello();
}