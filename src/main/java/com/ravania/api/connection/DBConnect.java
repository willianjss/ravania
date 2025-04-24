package com.ravania.api.connection;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.PrintWriter;
import java.sql.*;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnect implements DataSource {
    private String url = "localhost";
    private String dbName = "ravania";
    private Integer port = 3306;
    private String user = "root";
    private String password = "testes1234";
    public Connection con = null;
    public Statement stmt = null;
    public ResultSet rst = null;

    /**
     * <p>
     *     Método que faz a conexão com o banco de dados, usando o {@code MysqlDataSource}.
     * </p>
     * @return {@code Connection con}.
     */
    public Connection conectar(){
        con = null;
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(url);
        dataSource.setPortNumber(port);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(dbName);
        try {
            return con = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * <p>
     *     Método que fecha uma conexão existente.
     * </p>
     * @return {@code Boolean}.
     */
    public boolean fecharConexao(int codigo){
        try {
            if(Objects.equals(codigo, 0)){
                rst.close();
            }
            stmt.close();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
