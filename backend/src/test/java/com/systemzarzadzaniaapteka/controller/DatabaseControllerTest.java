package com.systemzarzadzaniaapteka.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DatabaseControllerTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private DatabaseMetaData metaData;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private DatabaseController databaseController;

   @Test
void getTables_Success() throws SQLException {
    // Given
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getTables(any(), any(), any(), any())).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, true, false);
    when(resultSet.getString("TABLE_NAME")).thenReturn("table1", "table2");

    // When
    List<String> tables = databaseController.getTables();

    // Then
    assertTrue(tables.containsAll(Arrays.asList("table1", "table2")));
}

    @Test
    void getTables_Error() throws SQLException {
        // Given
        when(dataSource.getConnection()).thenThrow(SQLException.class);

        // When
        assertThrows(SQLException.class, () -> {
            databaseController.getTables();
        });

    }
}