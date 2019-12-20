//Описание подключения к Microsoft SQL Server

package com.test.dog.firsttry;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseMSSQL {

    public static List<Request> getData()  {

        String url = "jdbc:sqlserver://HP-SQL-BASE;database=test_dog;integratedSecurity=true;authenticationScheme=NativeAuthentication;";
        ResultSet resultSet;
        List<Request> list= new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String selectSql = "SELECT id, first_name, last_name, description, address, days_to_complete,  "
                             + "work_cost, pre_payment, date_request FROM vdg_request ";
            resultSet = statement.executeQuery(selectSql);

            while(resultSet.next()) {
                LocalDateTime dt = Utils.convertToLocalDateTimeViaSqlTimestamp(resultSet.getDate("date_request"));
                Request request = new Request( resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("description"),
                        resultSet.getString("address"),
                        resultSet.getInt("days_to_complete"),
                        resultSet.getDouble("work_cost"),
                        resultSet.getBoolean("pre_payment"),
                        dt);
                list.add(request);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
