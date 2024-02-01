package ejb;

import interfaces.HelloEJB;

import javax.ejb.Stateless;

@Stateless
public class HelloEJBImpl implements HelloEJB {
    @Override
    public String hello() {
        return "hello";
    }
}
