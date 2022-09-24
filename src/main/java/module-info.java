module capstone {
    requires javafx.controls;
    requires javafx.fxml;

    opens capstone to javafx.fxml;
    exports capstone;
}
