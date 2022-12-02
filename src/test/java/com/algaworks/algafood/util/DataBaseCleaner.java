package com.algaworks.algafood.util;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DataBaseCleaner {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    public static Connection getConnexion() {
        // SQLite connection string
        String url = "jdbc:sqlite:algafood_test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Delete a warehouse specified by the id
     */
    public void deleteAllTables() {
        String sql = "SELECT name, tbl_name FROM sqlite_master " +
                " where type = 'table' and name <> 'flyway_schema_history'";

        Statement stmt  = null;
        ResultSet rs    = null;
        Connection conn = getConnexion();

        try {
            stmt  = conn.createStatement();
            rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                deleteTable(conn, rs.getString("tbl_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                  conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Delete a warehouse specified by the id
     */
    public void deleteTable(Connection conn, String tableName) {
        String sql = "DELETE from " + tableName;
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
