module com.vendas.vendas {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    // requires sqlite.jdbc;  // Se usando SQLite

    opens com.vendas.vendas to javafx.fxml;  // Permite FXML instanciar controllers
    opens com.vendas.vendas.controller to javafx.fxml;  // ← ADICIONE ESSA LINHA

    exports com.vendas.vendas;
}
