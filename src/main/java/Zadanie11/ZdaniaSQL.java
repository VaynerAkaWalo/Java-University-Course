package Zadanie11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ZdaniaSQL implements GeneratorZdan{

    private String path;

    @Override
    public void plikBazyDanych(String filename) {
        String[] result = filename.split("jdbc:sqlite:");
        path = result[result.length - 1];
    }

    @Override
    public String zbudujZdanie(int zdanieID) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            statement = connection.createStatement();
            statement.setQueryTimeout(10);

            String query = "select imie, plec, Czynnosc.Nazwa as CzynnoscNazwa, Przedmiot.Nazwa as PrzedmiotNazwa" +
                    " from zdanie natural join imie natural join Czynnosc join Przedmiot on Zdanie.PrzedmiotID = Przedmiot.PrzedmiotID" +
                    " where Zdanie.ZdanieID = " + zdanieID;
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                stringBuilder.setLength(0);
                stringBuilder.append(resultSet.getString("Imie"));
                stringBuilder.append(" ");
                stringBuilder.append(resultSet.getString("CzynnoscNazwa"));
                stringBuilder.append(resultSet.getInt("plec") == 1 ? "" : "a");
                stringBuilder.append(" ");
                stringBuilder.append(resultSet.getString("PrzedmiotNazwa"));
                stringBuilder.append(".");
            }

            return stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
