import services.SpaceMarineService;
import services.requests.SpaceMarineSearchRequest;
import services.responses.SpaceMarineListXMLResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String... args) throws ClassNotFoundException, SQLException {
        SpaceMarineService spaceMarineService = new SpaceMarineService();
        spaceMarineService.getAll(new SpaceMarineSearchRequest());
    }
}
