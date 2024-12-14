module com.example.pathfindingtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pathfindingtest to javafx.fxml;
    exports com.example.pathfindingtest;
}