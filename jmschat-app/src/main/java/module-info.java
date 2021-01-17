module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires activemq.all;
    requires java.naming;

    opens br.com.luisfga.jmschat to javafx.fxml;
    exports br.com.luisfga.jmschat;
}