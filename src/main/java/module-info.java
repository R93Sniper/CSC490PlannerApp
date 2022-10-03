module capstone {
    requires java.sql;
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;

    opens capstone to javafx.fxml;
    exports capstone;
}
