package serviceBeans;

import services.SpaceMarineServiceBean;

import services.StarshipServiceBean;
import services.SpaceMarineServiceBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBFactory {
    public static SpaceMarineServiceBean createSpaceMarineServiceFromJNDI() throws NamingException {
        return lookupSpaceMarineServiceBean();
    }
    public static StarshipServiceBean createStarshipServiceFromJNDI() throws NamingException {
        return lookupStarshipServiceBean();
    }
    private static SpaceMarineServiceBean lookupSpaceMarineServiceBean() throws NamingException {
        Context ctx = createInitialContext();
        return (SpaceMarineServiceBean) ctx.lookup("ejb:/ejb-0.0.1-SNAPSHOT/SpaceMarineServiceImpl!services.SpaceMarineServiceBean");
    }
    private static StarshipServiceBean lookupStarshipServiceBean() throws NamingException {
        Context ctx = createInitialContext();
        return (StarshipServiceBean) ctx.lookup("ejb:/ejb-0.0.1-SNAPSHOT/StarshipServiceBeanImpl!services.StarshipServiceBean");
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
