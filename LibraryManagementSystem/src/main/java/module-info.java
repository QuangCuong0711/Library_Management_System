module sourceCode {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;

    opens sourceCode to javafx.fxml;
    exports sourceCode;
    exports sourceCode.Controllers;
    opens sourceCode.Controllers to javafx.fxml;
    exports sourceCode.Models;
    opens sourceCode.Models to javafx.fxml;
}