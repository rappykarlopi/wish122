module com.example.wish122full {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
                requires com.almasb.fxgl.all;
    requires javafx.media;
    requires mp3agic;

    opens com.example.wish122 to javafx.fxml;
    exports com.example.wish122;
}