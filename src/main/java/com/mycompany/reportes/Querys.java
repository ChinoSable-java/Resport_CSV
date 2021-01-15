/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.reportes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salvador MG
 */
public class Querys {

    private static Connection cx;

    public Querys() {
        try {
            cx = ConexionBD.getInstance().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Querys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet Querys(int num) {
        switch (num) {
            case 1:
                return executeQ("select bancoadmin.tw_comercios.nombre_comercio as COMERCIO,"
                        + "sum(bancoadmin.tw_transacciones.importe_transaccion) as IMPORTE "
                        + "from BANCOADMIN.tw_comercios INNER JOIN BANCOADMIN.tw_transacciones "
                        + "on bancoadmin.tw_comercios.id_comercio=bancoadmin.tw_transacciones.id_comercio "
                        + "where bancoadmin.tw_transacciones.fecha_transaccion "
                        + "BETWEEN '15-02-2020' and '31-03-2020' "
                        + "GROUP BY nombre_comercio order by nombre_comercio");
            case 2:
                return executeQ("select c.id_cliente,  c.nombre ,sum(tr.importe_transaccion) as gasto "
                        + "from BANCOADMIN.tw_clientes c "
                        + "INNER JOIN BANCOADMIN.tw_tarjetas t "
                        + "on t.id_cliente=c.id_cliente "
                        + "INNER JOIN BANCOADMIN.tw_transacciones tr "
                        + "on t.numero_tarjeta=tr.numero_tarjeta "
                        + "where tr.fecha_transaccion BETWEEN '01-01-2020' and '31-03-2020' group by c.id_cliente, c.nombre order by nombre");
            default:
                return null;
        }
    }

    private ResultSet executeQ(String qy) {
        try {
            ResultSet n = cx.prepareStatement(qy).executeQuery();
            return n;
        } catch (SQLException ex) {
            Logger.getLogger(Querys.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
