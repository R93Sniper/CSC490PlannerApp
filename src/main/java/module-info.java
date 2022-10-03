module capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens capstone to javafx.fxml;
    exports capstone;
}
