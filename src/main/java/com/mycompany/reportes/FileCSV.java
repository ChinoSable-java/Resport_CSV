/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.reportes;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salvador MG
 */
public class FileCSV {

    public FileCSV(String nombreDeArchivo, int nqy) {
        crearArchivoCSV(nombreDeArchivo, ",", nqy);
    }

    private void crearArchivoCSV(String file, String delim, int nqy) {
        Querys n = new Querys();
        final String NEXT_LINE = "\n";
        try {
            FileWriter fw = new FileWriter(file + ".csv");
            ResultSet qy = n.Querys(nqy);
            ResultSetMetaData g = qy.getMetaData();
            boolean actv = true;
            while (qy.next()) {
                if (actv) {
                    for (int x = 0; x < g.getColumnCount(); x++) {
                        fw.append(g.getColumnName(x+1)).append(delim);
                    }
                    fw.append(NEXT_LINE);
                    actv = false;
                }
                for (int x = 0; x < g.getColumnCount(); x++) {
                    fw.append(qy.getString(x + 1)).append(delim);
                }
                fw.append(NEXT_LINE);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(FileCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new FileCSV("Reporte_1", 1);
        new FileCSV("Reporte_2", 2);        
    }

}
