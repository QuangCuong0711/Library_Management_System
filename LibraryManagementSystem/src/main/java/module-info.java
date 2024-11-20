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
    requires commons.logging;
    requires annotations;
    requires opencensus.api;

    opens sourceCode to javafx.fxml;
    exports sourceCode;
    exports sourceCode.Models;
    opens sourceCode.Models to javafx.fxml;
    exports sourceCode.Services;
    opens sourceCode.Services to javafx.fxml;
    exports sourceCode.AdminControllers;
    opens sourceCode.AdminControllers to javafx.fxml;
    exports sourceCode.UserControllers;
    opens sourceCode.UserControllers to javafx.fxml;
}