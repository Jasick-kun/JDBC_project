package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class GetFromDB {
    public static ResultSet getFullPathOfFile(String name) throws SQLException {
            Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
            String str ="WITH RECURSIVE rec (id,name,parent_id) as (\n"+
                    "SELECT f.id,f.name,f.parent_id FROM file f WHERE f.name ='"+name+"' \n "+
                    "UNION\n" +
                    "SELECT c.id,c.name,c.parent_id FROM catalogs c INNER JOIN rec on rec.parent_id=c.id)\n"+
                    "SELECT * FROM rec"
                    ;
            ResultSet resultSet;
            resultSet = statement.executeQuery(str);
            return resultSet;
    }
    public static ResultSet getCountOfFiles(String name) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="WITH RECURSIVE rec (id,name,parent_id) as (\n"+
                "SELECT c.id,c.name,c.parent_id FROM catalogs c WHERE c.name ='"+name+"' \n "+
                "UNION\n" +
                "SELECT c.id,c.name,c.parent_id FROM catalogs c INNER JOIN rec on rec.id=c.parent_id)\n"+
                "SELECT count(*) FROM rec inner join file f on f.id=rec.id"
                ;
        ResultSet resultSet;
        resultSet = statement.executeQuery(str);
        return resultSet;
    }
    public static ResultSet getCostOfFiles(String name) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="WITH RECURSIVE rec (id,name,parent_id) as (\n"+
                "SELECT c.id,c.name,c.parent_id FROM catalogs c WHERE c.name ='"+name+"' \n "+
                "UNION\n" +
                "SELECT c.id,c.name,c.parent_id FROM catalogs c INNER JOIN rec on rec.id=c.parent_id)\n"+
                "SELECT SUM(f.size) FROM rec inner join file f on f.id=rec.id"
                ;
        ResultSet resultSet;
        resultSet = statement.executeQuery(str);
        return resultSet;
    }
    public static ResultSet getFilesFromMask(String name) throws SQLException {
        Statement statement = Objects.requireNonNull(ConnectionDB.connect()).createStatement();
        String str ="select name from file where to_tsvector(name) @@ to_tsquery('"+name+":*')";
        ResultSet resultSet;
        resultSet = statement.executeQuery(str);
        return resultSet;
    }

}
