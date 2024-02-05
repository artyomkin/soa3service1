package repo;

import entities.SpaceMarine;
import entities.Starship;
import entities.domain.Chapter;
import entities.domain.Coordinates;
import entities.domain.MeleeWeapon;
import org.jetbrains.annotations.NotNull;
import services.responses.SpaceMarineListXMLResponse;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DB {
    public String sql;

    public DB(String sql) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        this.sql = sql;
    }
    public DB() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        this.sql = "";
    }

    public DB sel(String col){
        this.sql += "select " + col + " ";
        return this;
    }

    public DB sel(List<String> cols){
        this.sql += "select " + String.join(", ", cols) + " ";
        return this;
    }

    public DB f(String table){
        this.sql += "from " + table + " ";
        return this;
    }

    public DB where(String cond){
        if (cond != null && !cond.isBlank()){
            this.sql += "where " + cond;
        }
        return this;
    }

    public DB insert(String table, List<String> cols){
        this.sql += "insert into " + table + " (" + String.join(", ", cols) + ") ";
        return this;
    }

    public DB values(List<String> vals){
        this.sql += "values (" + String.join(", ", vals) + ") ";
        return this;
    }

    public DB del(String table){
        this.sql += "delete from " + table;
        return this;
    }

    public DB update(String table){
        this.sql += "update " + table;
        return this;
    }

    public DB set(Map<String, String> values){
        this.sql += "set " + String.join(",", values.entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.toList())) + " ";
        return this;
    }

    public ResultSet fetch(@NotNull Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        System.out.println(this.sql);
        ResultSet resultSet = stmt.executeQuery(this.sql);
        this.sql = "";
        return resultSet;
    }

    public void execute(@NotNull Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        System.out.println(this.sql);
        stmt.execute(this.sql);
        this.sql = "";
    }

    public ResultSet execute(@NotNull Connection con, String... returning) throws SQLException {
        this.sql += "returning " + String.join(", ", returning);
        Statement stmt = con.createStatement();
        System.out.println(this.sql);
        ResultSet resultSet = stmt.executeQuery(this.sql);
        this.sql = "";
        return resultSet;
    }

    public SpaceMarine findSpaceMarineById(@NotNull Long id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("*").f("space_marines").where("id = " + id.toString()).fetch(con);
        if (rs.next()) {
            SpaceMarine sm = new SpaceMarine();
            sm.setId(rs.getInt("id"));
            sm.setName(rs.getString("name"));
            sm.setCoordinates(this.findCoordinatesById(rs.getInt("coordinates_id")));
            sm.setCreationDate(rs.getDate("creation_date"));
            sm.setCreationDateStr(rs.getString("creation_date_str"));
            sm.setHealth(rs.getFloat("health"));
            sm.setLoyal(rs.getBoolean("loyal"));
            sm.setHeight(rs.getDouble("height"));
            sm.setMeleeWeapon(MeleeWeapon.valueOf(rs.getString("melee_weapon")));
            sm.setChapter(this.findChapterByName(rs.getString("chapter_name")));
            sm.setStarshipId(rs.getInt("starship_id"));
            return sm;
        }
        return null;
    }

    public Coordinates findCoordinatesById(@NotNull Integer id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = this.sel("*").f("coordinates").where("id = " + id.toString()).fetch(con);
        Coordinates coordinates = new Coordinates();
        rs.next();
        coordinates.setId(rs.getInt("id"));
        coordinates.setX(rs.getLong("x"));
        coordinates.setY(rs.getInt("y"));
        con.close();
        return coordinates;
    }

    public Chapter findChapterByName(String name) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = this.sel("*").f("chapters").where("name = \'" + name + "\'").fetch(con);
        Chapter chapter = new Chapter();
        rs.next();
        chapter.setName(rs.getString("name"));
        chapter.setParentLegion(rs.getString("parent_legion"));
        chapter.setWorld(rs.getString("world"));
        con.close();
        return chapter;
    }

    public List<SpaceMarine> findAllSpaceMarines(@NotNull Map<String, String> parameters) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = this
                .sel("*")
                .f("space_marines")
                .where(
                        parameters.entrySet().stream()
                                .map(entry -> entry.getKey() + " = " + entry.getValue())
                                .collect(Collectors.joining())
                )
                .fetch(con);
        List<SpaceMarine> result = new ArrayList<>();
        while (rs.next()) {
            SpaceMarine sm = new SpaceMarine();
            sm.setId(rs.getInt("id"));
            sm.setName(rs.getString("name"));
            sm.setCoordinates(this.findCoordinatesById(rs.getInt("coordinates_id")));
            sm.setCreationDate(rs.getDate("creation_date"));
            sm.setCreationDateStr(rs.getString("creation_date_str"));
            sm.setHealth(rs.getFloat("health"));
            sm.setLoyal(rs.getBoolean("loyal"));
            sm.setHeight(rs.getDouble("height"));
            sm.setMeleeWeapon(MeleeWeapon.valueOf(rs.getString("melee_weapon")));
            sm.setChapter(this.findChapterByName(rs.getString("chapter_name")));
            sm.setStarshipId(rs.getInt("starship_id"));
            result.add(sm);
        }
        con.close();
        return result;
    }

    public List<Starship> findAllStarships() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("*").f("starships").fetch(con);
        List<Starship> res = new ArrayList<>();
        while (rs.next()){
            Starship starship = new Starship();
            starship.setId(rs.getInt("id"));
            starship.setName(rs.getString("name"));
            res.add(starship);
        }
        return res;
    }

    public List<SpaceMarine> findSpaceMarinesByMinCoords() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet coordsRs = sel("min(id)").f("coordinates").fetch(con);
        coordsRs.next();
        Integer minCoordsId = coordsRs.getInt("min");
        ResultSet spaceMarinesRs = sel("*")
                .f("space_marines")
                .where("coordinates_id = " + minCoordsId)
                .fetch(con);
        List<SpaceMarine> result = new ArrayList<>();
        while (spaceMarinesRs.next()) {
            SpaceMarine sm = new SpaceMarine();
            sm.setId(spaceMarinesRs.getInt("id"));
            sm.setName(spaceMarinesRs.getString("name"));
            sm.setCoordinates(this.findCoordinatesById(spaceMarinesRs.getInt("coordinates_id")));
            sm.setCreationDate(spaceMarinesRs.getDate("creation_date"));
            sm.setCreationDateStr(spaceMarinesRs.getString("creation_date_str"));
            sm.setHealth(spaceMarinesRs.getFloat("health"));
            sm.setLoyal(spaceMarinesRs.getBoolean("loyal"));
            sm.setHeight(spaceMarinesRs.getDouble("height"));
            sm.setMeleeWeapon(MeleeWeapon.valueOf(spaceMarinesRs.getString("melee_weapon")));
            sm.setChapter(this.findChapterByName(spaceMarinesRs.getString("chapter_name")));
            sm.setStarshipId(spaceMarinesRs.getInt("starship_id"));
            result.add(sm);
        }
        con.close();
        return result;
    }

    public List<SpaceMarine> findSpaceMarinesByMeleeWeapon(MeleeWeapon meleeWeapon) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("*")
                .f("space_marines")
                .where("melee_weapon = " + meleeWeapon.toString())
                .fetch(con);
        List<SpaceMarine> result = new ArrayList<>();
        while (rs.next()) {
            SpaceMarine sm = new SpaceMarine();
            sm.setId(rs.getInt("id"));
            sm.setName(rs.getString("name"));
            sm.setCoordinates(this.findCoordinatesById(rs.getInt("coordinates_id")));
            sm.setCreationDate(rs.getDate("creation_date"));
            sm.setCreationDateStr(rs.getString("creation_date_str"));
            sm.setHealth(rs.getFloat("health"));
            sm.setLoyal(rs.getBoolean("loyal"));
            sm.setHeight(rs.getDouble("height"));
            sm.setMeleeWeapon(MeleeWeapon.valueOf(rs.getString("melee_weapon")));
            sm.setChapter(this.findChapterByName(rs.getString("chapter_name")));
            sm.setStarshipId(rs.getInt("starship_id"));
            result.add(sm);
        }
        con.close();
        return result;
    }

    public int save(@NotNull Coordinates coordinates) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = insert("coordinates", Arrays.asList("x", "y"))
                .values(Arrays.asList(coordinates.getX().toString(), String.valueOf(coordinates.getY())))
                .execute(con, "id");
        rs.next();
        int id = rs.getInt("id");
        con.close();
        return id;
    }

    public int save(@NotNull Starship starship) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = insert("starship", Arrays.asList("id", "name"))
                .values(Arrays.asList(starship.getId().toString(), "\'" + starship.getName() + "\'"))
                .execute(con, "id");
        rs.next();
        int id = rs.getInt("id");
        con.close();
        return id;
    }
    public String save(@NotNull Chapter chapter) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = insert("chapters", Arrays.asList("name", "parent_legion", "world"))
                .values(Arrays.asList("\'" + chapter.getName() + "\'", "\'" + chapter.getParentLegion() + "\'", "\'" + chapter.getWorld() + "\'"))
                .execute(con, "name");
        rs.next();
        String name = rs.getString("name");
        con.close();
        return name;
    }
    public long save(@NotNull SpaceMarine sm) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        int coordinatesId = save(sm.getCoordinates());
        String chapterName = save(sm.getChapter());
        ResultSet rs = insert("space_marines", Arrays.asList(
                "name",
                "coordinates_id",
                "creation_date",
                "creation_date_str",
                "health",
                "loyal",
                "height",
                "melee_weapon",
                "chapter_name",
                "starship_id"))
                .values(Arrays.asList(
                        "\'" + sm.getName() + "\'",
                        String.valueOf(coordinatesId),
                        "\'" + sm.getCreationDate().toString() + "\'",
                        "\'" + sm.getCreationDateStr() + "\'",
                        String.valueOf(sm.getHealth()),
                        String.valueOf(sm.getLoyal()),
                        String.valueOf(sm.getHeight()),
                        "\'" + sm.getMeleeWeapon().toString() + "\'",
                        "\'" + chapterName + "\'",
                        String.valueOf(sm.getStarshipId())
                ))
                .execute(con, "id");
        rs.next();
        int id = rs.getInt("id");
        con.close();
        return id;
    }

    public boolean starshipExistsById(@NotNull Integer id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("count(*)").f("starships").where("id = " + id.toString()).fetch(con);
        rs.next();
        int count = rs.getInt("count");
        con.close();
        return count > 0;
    }

    public boolean chapterExistsByName(String name) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("count(*)").f("chapters").where("name = " + "\'" + name + "\'").fetch(con);
        rs.next();
        int count = rs.getInt("count");
        con.close();
        return count > 0;
    }

    public boolean spaceMarineExistsById(Long id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("count(*)").f("space_marines").where("id = " + id.toString()).fetch(con);
        rs.next();
        int count = rs.getInt("count");
        con.close();
        return count > 0;
    }

    public void deleteChapterByName(String name) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        del("chapters").where("name = " + name).execute(con);
        con.close();
    }

    public void deleteCoordinatesById(Integer id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        del("coordinates").where("id = " + id.toString()).execute(con);
        con.close();
    }

    public void deleteSpaceMarineById(Long id) throws SQLException {
        SpaceMarine sm = findSpaceMarineById(id);
        if (sm == null){
            return;
        }
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        del("coordinates").where("id = " + sm.getCoordinates().getId()).execute(con);
        del("chapters").where("name = " + sm.getChapter().getName()).execute(con);
        del("space_marines").where("id = " + id);
        con.close();
    }

    public Integer countByHealth(Double health) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        ResultSet rs = sel("count(*)").f("space_marines").where("health < " + health.toString()).fetch(con);
        rs.next();
        Integer res = rs.getInt("count");
        con.close();
        return res;
    }

    public void update(Coordinates coordinates) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        Map<String, String> setValues = new HashMap<>();
        setValues.put("id", coordinates.getId().toString());
        setValues.put("x", coordinates.getX().toString());
        setValues.put("y", String.valueOf(coordinates.getY()));
        update("coordinates").set(setValues).execute(con);
        con.close();
    }

    public void update(Chapter chapter) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        Map<String, String> setValues = new HashMap<>();
        setValues.put("name", chapter.getName());
        setValues.put("parent_legion", "\'" + chapter.getParentLegion() + "\'");
        setValues.put("world", "\'" + chapter.getWorld() + "\'");
        update("chapters").set(setValues);
        con.close();
    }

    public Long update(SpaceMarine sm) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soa3", "artem", "artem");
        SpaceMarine oldSm = findSpaceMarineById(sm.getId());
        update(oldSm.getCoordinates());
        update(oldSm.getChapter());
        int coordinatesId = sm.getCoordinates().getId();
        String chapterName = sm.getChapter().getName();
        Map<String, String> setValues = new HashMap<>();
        setValues.put("id", String.valueOf(sm.getId()));
        setValues.put("name", "\'" + sm.getName() + "\'");
        setValues.put("coordinates_id", String.valueOf(coordinatesId));
        setValues.put("creation_date", sm.getCreationDate().toString());
        setValues.put("creation_date_str", "\'" + sm.getCreationDateStr().toString() + "\'");
        setValues.put("health", String.valueOf(sm.getHealth()));
        setValues.put("loyal", String.valueOf(sm.getLoyal()));
        setValues.put("height", String.valueOf(sm.getHeight()));
        setValues.put("melee_weapon", sm.getMeleeWeapon().toString());
        setValues.put("chapter_name", sm.getChapter().getName());
        setValues.put("starhip_id", sm.getStarshipId().toString());
        ResultSet rs = update("space_marines").set(setValues).execute(con, "id");
        rs.next();
        Long updatedId = rs.getLong("id");
        con.close();
        return updatedId;
    }
}
