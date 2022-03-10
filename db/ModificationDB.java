package db;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ModificationDB {
    public static boolean moveCatalog(String name, String moveTo) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="UPDATE catalogs SET parent_id=" +
                "(SELECT id FROM catalogs WHERE name='"+moveTo+"')" +
                " WHERE name ='"+name+"'";

        return statement.execute(str);
    }

    public static boolean addFile(String name,Integer size, String parent) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="INSERT INTO file VALUES ('"+name+"'," +size+"," +
                "select id from catalogs where name='"+parent+"')";

        return statement.execute(str);
    }



    public static boolean addCatalog(String name, String parent) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="INSERT INTO catalogs VALUES ('"+name+"'," +
                "select id from catalogs where name='"+parent+"')";

        return statement.execute(str);
    }

    public static boolean addCatalog(String name) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="INSERT INTO catalogs VALUES ('"+name+"', null)";
        return statement.execute(str);
    }

    public static boolean deleteFile(String name) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="DELETE FROM file where name ='"+name+"'";
        return statement.execute(str);
    }

}
