import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    
    static Connection connection = null;
    static Statement state;
    static String url = "jdbc:derby:pdc;create=true";
    static String dbUserName = "pdc";
    static String dbPassword = "";
    
    public Database() throws SQLException {
    	
    	connection = DriverManager.getConnection(url, dbUserName, dbPassword);
        state = connection.createStatement();
    	
    }
        
    public static void saveProfile(Portfolio pf) {

        try {
            
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(null, null, "DATA", null);
            
            if (resultSet.next()) {
                // nothing
            } else {
                
                state.execute("CREATE TABLE data (" +
                    "symbol VARCHAR(45), " +
                    " amount DECIMAL," +
                    " pricePaid DECIMAL," +
                    " priceBoughtAt DECIMAL, " +
                    " profile VARCHAR(45), " +
                    " date INTEGER)");
            }
            
            state.executeUpdate("DELETE FROM data WHERE profile = '" + pf.name + "'");
            
            for(StockHeld sh : pf.stockList){
            
                state.execute("INSERT INTO data(symbol, amount, pricePaid, priceBoughtAt, profile) VALUES ('" + 
                        sh.getSymbol() + "', " + 
                        sh.getAmount() + ", " + 
                        sh.getPricePaid() + ", " + 
                        sh.getPriceBoughtAt() + ", '" + 
                        pf.name + "')");
            
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }  
    	
    }
    
    public ResultSet getData(String name) throws SQLException {
    	
    	ResultSet rs = state.executeQuery("SELECT symbol, amount, pricePaid, priceBoughtAt, profile FROM data WHERE profile = '" + name + "'");
    	
    	return rs;
    	
    }
    
}
