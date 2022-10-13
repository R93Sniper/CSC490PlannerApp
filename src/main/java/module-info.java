module capstone {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires json;
    opens capstone to javafx.fxml;
    exports capstone;
    
}
