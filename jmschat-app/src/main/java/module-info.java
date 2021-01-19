module app {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires activemq.client;
    requires jms.api;
    requires java.management;
    
    requires org.slf4j;
    requires java.transaction.xa;
    
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    
    opens br.com.luisfga.jmschat.database.entities to org.hibernate.orm.core;

    opens br.com.luisfga.jmschat to javafx.fxml;
    exports br.com.luisfga.jmschat;
    
}