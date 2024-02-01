package serviceBeans;

import services.SpaceMarineService;
import services.StarshipService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBFactory {
    public static SpaceMarineService createSpaceMarineServiceFromJNDI() throws NamingException {
        return lookupSpaceMarineServiceBean();
    }
    public static StarshipService createStarshipServiceFromJNDI() throws NamingException {
        return lookupStarshipServiceBean();
    }

    private static SpaceMarineService lookupSpaceMarineServiceBean() throws NamingException {
        Context ctx = createInitialContext();
        return (SpaceMarineService) ctx.lookup("java:global/ejb-0.0.1-SNAPSHOT/SpaceMarineServiceBean!services.SpaceMarineServiceBean");
    }
    private static StarshipService lookupStarshipServiceBean() throws NamingException {
        Context ctx = createInitialContext();
        return (StarshipService) ctx.lookup("java:global/ejb-0.0.1-SNAPSHOT/StarshipServiceBean!services.StarshipServiceBean");
    }

    private static Context createInitialContext() throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES,
                "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.PROVIDER_URL,
                "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(jndiProperties);
    }
}
