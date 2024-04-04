module com.example.quantfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.quantfx to javafx.fxml;
    exports com.example.quantfx;
}