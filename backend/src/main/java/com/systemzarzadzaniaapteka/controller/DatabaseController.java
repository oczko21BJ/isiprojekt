package com.systemzarzadzaniaapteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa kontrolera do zarządzania bazą danych w systemie zarządzania apteką.
 * 
 * <p>Klasa DatabaseController zapewnia metody do obsługi bazy danych,
 * w tym pobieranie listy tabel w bazie danych.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/tables")
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            try (ResultSet rs = metaData.getTables(null, null, "%", types)) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    tables.add(tableName);
                }
            }
        }
        return tables;
    }
}
