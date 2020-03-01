package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

public class ConnectionFactory {
    private String DB_SERVER_NAME = "localhost";
    private String DB_NAME = "classicmodels";
    private String DB_USER = "root";
    private String DB_PASSWORD = "kopytko1";
    private int DB_PORT = 3306;
    private MysqlDataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    public ConnectionFactory() throws SQLException {
        this("/database.properties");
    }

    public ConnectionFactory(String fileName) throws SQLException {
        Properties dataBaseProperties = getDataBaseProperties(fileName);
        dataSource = new MysqlDataSource();
        dataSource.setServerName(dataBaseProperties.getProperty("pl.sda.jdbc.db.server"));
        dataSource.setDatabaseName(dataBaseProperties.getProperty("pl.sda.jdbc.db.name"));
        dataSource.setUser(dataBaseProperties.getProperty("pl.sda.jdbc.db.user"));
        dataSource.setPassword(dataBaseProperties.getProperty("pl.sda.jdbc.db.password"));
        dataSource.setPort(Integer.parseInt(dataBaseProperties.getProperty("pl.sda.jdbc.db.port")));
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF-8");
    }


    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            if (propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }


    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {

ConnectionFactory connectionFactory=new ConnectionFactory("/database.properties");
    }
}
