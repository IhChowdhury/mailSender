package com.ibrahim.mailsender.db;

import com.ibrahim.mailsender.model.EmaiTextModel;
import com.ibrahim.mailsender.model.FromEmail;
import com.ibrahim.mailsender.model.ToEmail;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class DBManager {

    private Connection connection;
    private static DBManager dBManager = null;

    public static DBManager getdBManager() {
        if (dBManager == null) {
            dBManager = new DBManager();
        }
        return dBManager;
    }

    public Connection getConDBConnection() throws SQLException {

        if (connection != null) {
            return connection;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
//            LOGGER.fatal(e.getMessage());
        }

        String PUBLIC_DNS = "localhost";
//        String DATABASE = "tinder_testing";
        String DATABASE = "mailsender";
        String REMOTE_DATABASE_USERNAME = "root";
        String DATABASE_USER_PASSWORD = "";
        String PORT = "3306";

        try {
            connection = (Connection) DriverManager.
                    getConnection("jdbc:mysql://" + PUBLIC_DNS + ":" + PORT + "/" + DATABASE, REMOTE_DATABASE_USERNAME, DATABASE_USER_PASSWORD);
        } catch (SQLException ex) {
            throw ex;
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        getConDBConnection().close();
    }

    public List<ToEmail> getToEmail(int numberOfEmail) throws SQLException {
        String sql = "SELECT * FROM `to_email` WHERE `to_email`.`lock_status` = 0 AND `to_email`.`sending_status` = 0 LIMIT " + numberOfEmail;
        PreparedStatement preparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<ToEmail> toEmailList = new LinkedList<ToEmail>();
        String lockSql = "UPDATE `to_email` SET`lock_status`=1 WHERE `to_email`.`id`=?";
        preparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(lockSql);
        while (resultSet.next()) {
            
            int toEmailId = resultSet.getInt("id");
            preparedStatement.setInt(1, toEmailId);
            
            ToEmail toEmail = new ToEmail();
            toEmail.setId(toEmailId);
            toEmail.setEmail(resultSet.getString("email"));
            toEmail.setLockStatus(resultSet.getInt("lock_status"));
            toEmail.setStatus(resultSet.getInt("sending_status"));
            
            toEmailList.add(toEmail);
            
            preparedStatement.addBatch();
            
        }

        preparedStatement.executeBatch();
        
        return toEmailList;
    }

    public FromEmail getFromEmail(int totalEmailSent) throws SQLException {
        String sql = "SELECT * FROM `from_email` WHERE `from_email`.`total_email_sent` < ? AND `from_email`.`status` = 0 AND `from_email`.`lock_status` = 0 LIMIT 1";
        PreparedStatement preparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(sql);
        preparedStatement.setInt(1, totalEmailSent);
        ResultSet resultSet = preparedStatement.executeQuery();
        FromEmail fromEmail = null;
        if (resultSet.next()) {

            String lockSql = "UPDATE `from_email` SET`lock_status`=1 WHERE `from_email`.`id`=?";
            PreparedStatement lockPreparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(lockSql);
            int fromEmailId = resultSet.getInt("id");
            lockPreparedStatement.setInt(1, fromEmailId);
            lockPreparedStatement.execute();
            fromEmail = new FromEmail();
            fromEmail.setId(fromEmailId);
            fromEmail.setEmail(resultSet.getString("email"));
            fromEmail.setPassword(resultSet.getString("password"));
            fromEmail.setLockStatus(resultSet.getInt("lock_status"));
            fromEmail.setStatus(resultSet.getInt("status"));
            fromEmail.setTotalEmailSent(resultSet.getInt("total_email_sent"));
            
        }
        return fromEmail;

    }
    
    public void updateFromEmail(FromEmail fromEmail) throws SQLException{
        String sql = "UPDATE `from_email` SET `total_email_sent`=?,`status`=?,`loginReport`=? WHERE `id`=?";
        PreparedStatement preparedStatement = (PreparedStatement) DBManager.getdBManager().getConDBConnection().prepareStatement(sql);
        preparedStatement.setInt(1, fromEmail.getTotalEmailSent());
        preparedStatement.setInt(2, fromEmail.getStatus());
        preparedStatement.setString(3, fromEmail.getLoginReport());
        preparedStatement.setInt(4, fromEmail.getId());
        
        preparedStatement.execute();
    }
    
    public void updateToEmail(List<ToEmail> toEmails) throws SQLException{
        String sql = "UPDATE `to_email` SET `sending_status`=? WHERE `id`=?";
        PreparedStatement preparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(sql);
        for(ToEmail toEmail : toEmails){
            preparedStatement.setInt(1, toEmail.getStatus());
            preparedStatement.setInt(2, toEmail.getId());
            
            preparedStatement.addBatch();
        }
        
        preparedStatement.executeBatch();
    }
    
    public EmaiTextModel getEmailText() throws SQLException{
        String sql = "SELECT `id`, `subject`, `body` FROM `email_message_model` LIMIT 1";
        PreparedStatement preparedStatement = (PreparedStatement) getConDBConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        EmaiTextModel textModel = null;
        if(resultSet.next()){
            textModel = new EmaiTextModel();
            textModel.setId(resultSet.getInt("id"));
            textModel.setSubject(resultSet.getString("subject"));
            textModel.setBody(resultSet.getString("body"));
        }
        
        return textModel;
    }

}
