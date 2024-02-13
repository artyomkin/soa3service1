import services.SpaceMarineServiceImpl;
import services.requests.SpaceMarineSearchRequest;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String... args) throws ClassNotFoundException, SQLException {
        SpaceMarineServiceImpl spaceMarineServiceImpl = new SpaceMarineServiceImpl();
        spaceMarineServiceImpl.getAll(new SpaceMarineSearchRequest());

    }
}
