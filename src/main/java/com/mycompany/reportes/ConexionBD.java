/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.reportes;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Salvador MG
 */
public class ConexionBD {

    private Connection con;
    private static ConexionBD instancia;
    private String url, username, password;

    private ConexionBD() {

        try {

            Properties prop = new Properties();
            prop.load(new FileInputStream("datasource.properties.txt"));

            Class.forName(prop.getProperty("driver"));

            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String database = prop.getProperty("database");
            url = "jdbc:oracle:thin:@" + host + ":" + port + "/" + database;
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (Exception e) {
            System.out.println("Parametros de conexion incorrectos.");
        }
    }

    public static ConexionBD getInstance() {

        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {

        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(url, username, password);
        }
        return con;
    }

    public void close() throws SQLException {

        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}
