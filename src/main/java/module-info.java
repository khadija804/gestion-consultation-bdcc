module ma.enset.gestionconsultationbdcc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;


    opens ma.enset.gestionconsultationbdcc to javafx.fxml;
    opens ma.enset.gestionconsultationbdcc.controller to javafx.fxml;
    opens ma.enset.gestionconsultationbdcc.entities to  javafx.base;
    opens ma.enset.gestionconsultationbdcc.views to javafx.fxml;
    exports ma.enset.gestionconsultationbdcc;
    exports ma.enset.gestionconsultationbdcc.entities;
}