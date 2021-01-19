package br.com.luisfga.jmschat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    private static Scene scene;
    
    private static EntityManager em;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("jmschat"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static EntityManager getEntityManager(){
        return em;
    }
    
    public static void main(String[] args) {
        
        logger.debug("Initializing JPA");
        
        Properties jpaUnitProperties = new Properties();

        jpaUnitProperties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
        jpaUnitProperties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:data/jmschat-app-db");
        jpaUnitProperties.put("javax.persistence.jdbc.user", "sa");
        jpaUnitProperties.put("javax.persistence.jdbc.password", "");
        jpaUnitProperties.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        
        
        //if its not empty, create a database with this suffix. This permits runing 2+ apps at same time. Mainly to develpment porpoise.
        //In 'production' its not needed, a priori. But can be useful to implement mult-user capabilities on same device.
        if (args.length != 0) {
            jpaUnitProperties.replace("javax.persistence.jdbc.url", "jdbc:hsqldb:file:data/jmschat-app-db-"+args[0]);
        }

        //Load entityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("applicationJpaUnit", jpaUnitProperties);
        em = emf.createEntityManager();
        
        logger.debug("JPA ready!");
        
        //TODO: Check user data
        
        launch();
    }

}