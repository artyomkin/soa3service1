import services.SpaceMarineServiceImpl;
import services.requests.SpaceMarineSearchRequest;

import java.sql.*;

public class App {
    public static void main(String... args) throws ClassNotFoundException, SQLException {
        SpaceMarineServiceImpl spaceMarineServiceImpl = new SpaceMarineServiceImpl();
        spaceMarineServiceImpl.getAll(new SpaceMarineSearchRequest());
    }
}
