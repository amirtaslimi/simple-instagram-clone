module com.psudoinstagram {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;

    requires javafx.media;

    opens com.psudoinstagram to javafx.fxml;
    exports com.psudoinstagram;
    exports com.psudoinstagram.model;
    opens com.psudoinstagram.model to javafx.fxml;
}