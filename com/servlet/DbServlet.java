package com.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbServlet {
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public static void save(String a, String b, String c) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into signup(Name,Mail,password,DateofSignup) values('" + a + "','" + b + "','"
                    + c + "',now())");
            
            stmt.executeUpdate("insert into userBalance(balance) values('80000')");
            con.close();
            stmt.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }
    }

    public static int signInValidation(String mailin, String passin) {
        int count = 0;
        try {

            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("select * from signup");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String passinen = Hello.encryptPassword(passin);
                String mail = rs.getString("Mail");
                String password = rs.getString("password");
                if (mailin.equals(mail) && passinen.equals(password)) {
                    count++;
                }
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return count;
    }

    public static String extractingName(String mail) {
        String Username = "";
        try {

            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");

            PreparedStatement ps = connection.prepareStatement("SELECT Name FROM signup WHERE Mail = ?");
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Username = rs.getString("Name");

            }
            rs.close();
            ps.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Username;
    }

    public static int extractingID(String mail) {
        int id = 0;
        try {

            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");

            PreparedStatement ps = connection.prepareStatement("SELECT ID FROM signup WHERE Mail = ?");
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ID");

            }
            rs.close();
            ps.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String mailChecking(String mail) {
        String MAIL = "";
        try {

            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("SELECT Mail FROM signup WHERE Mail = ?");
            ps.setString(1, mail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MAIL = rs.getString("Mail");

            }
            rs.close();
            ps.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MAIL;
    }

    public static void planInserting(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long id = (Long) jsonObject.get("id");
            Long mn = (Long) jsonObject.get("modelNO");
            String name = (String) jsonObject.get("name");
            String company = (String) jsonObject.get("company");
            String model = (String) jsonObject.get("model");
            String processor = (String) jsonObject.get("processor");
            String memory = (String) jsonObject.get("memory");
            String harddrive = (String) jsonObject.get("harddrive");
            String additional = (String) jsonObject.get("additional");

            stmt.executeUpdate(
                    "INSERT INTO requestTable(Quantity,ID,ModelNo,Name,Company,Model,Processor,Memory,HardDrive,Additional,DateofRequest,Status) VALUES (1,"
                            + id + ","
                            + mn + ", '" + name + "', '" + company + "', '" + model + "','" + processor + "','" + memory
                            + "','" + harddrive + "','" + additional + "', NOW(),'R')");

            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.INFO, "Inserted plan data into database");

            stmt.close();
            con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }
    }

    public static JSONArray requestSuperAdmin() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("select * from requestTable");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                int uid = rs.getInt("ID");
                int q = rs.getInt("Quantity");
                int id = rs.getInt("OrderID");
                String mn = rs.getString("ModelNo");
                String name = rs.getString("Name");
                String Comp = rs.getString("Company");
                String Model = rs.getString("Model");
                String Processor = rs.getString("Processor");
                String Memory = rs.getString("Memory");
                String hd = rs.getString("HardDrive");
                String add = rs.getString("Additional");
                String dor = rs.getString("DateofRequest");
                String status = rs.getString("Status");
                obj.put("Quantity", q);
                obj.put("UID", uid);
                obj.put("ID", id);
                obj.put("Name", name);
                obj.put("ModelNo", mn);
                obj.put("Company", Comp);
                obj.put("Model", Model);
                obj.put("Processor", Processor);
                obj.put("Memory", Memory);
                obj.put("HardDrive", hd);
                obj.put("Additional", add);
                obj.put("DateofRequest", dor);
                obj.put("Status", status);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void approveRecord(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long id = (Long) jsonObject.get("del");
            Long q = (Long) jsonObject.get("sp");
            int count=0;

            PreparedStatement ps1 = con
                    .prepareStatement("select Price from availableServer as Price where ModelNo=(select ModelNo from requestTable where OrderID='"+id+"' and Quantity='"+q+"'); ");
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()) {
                
                count = rs1.getInt("Price");
                
            }
            



            PreparedStatement ps = con.prepareStatement("select * from requestTable");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int qq = rs.getInt("Quantity");
                int idid = rs.getInt("OrderID");
                if(id==idid&&q==qq){
                
                
                String mn = rs.getString("ModelNo");
                String name = rs.getString("Name");
                String Comp = rs.getString("Company");
                String Model = rs.getString("Model");
                String Processor = rs.getString("Processor");
                String Memory = rs.getString("Memory");
                String hd = rs.getString("HardDrive");
                String add = rs.getString("Additional");
                String dor = rs.getString("DateofRequest");
                String status = rs.getString("Status");
                
                simpleMain.bill(idid,dor,status,mn,name,Comp,Model,Processor,Memory,hd,add,count);
                }
            }
            stmt.executeUpdate(
                    "update requestTable set Status='A' where OrderID='" + id + "' and Quantity='" + q + "'");
            stmt.executeUpdate(
                    "UPDATE availableServer SET AvailableCount = AvailableCount - 1 WHERE ModelNo = (SELECT ModelNo FROM requestTable WHERE OrderID = '"
                            + id + "'and Quantity='" + q + "')"

            );
            stmt.executeUpdate(
                    "UPDATE userBalance SET balance = balance - (SELECT Price FROM availableServer WHERE ModelNo = (SELECT ModelNo FROM requestTable WHERE OrderID = '"
                            + id + "'and Quantity='" + q + "')) WHERE ID = (Select ID from requestTable where OrderID='"
                            + id + "' and Quantity='" + q + "' );");
            stmt.executeUpdate(
                    "update adminTable set Balance=Balance+(SELECT Price FROM availableServer WHERE ModelNo = (SELECT ModelNo FROM requestTable WHERE OrderID = '"
                            + id + "'and Quantity='" + q + "'));");

                            rs.close();
                            ps.close();
                            stmt.close();
                            con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }

    }

    public static JSONArray userSuperAdmin() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("select ID,Name,Mail from signup");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String mail = rs.getString("Mail");

                obj.put("Name", name);
                obj.put("Mail", mail);
                obj.put("ID", id);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void denyRecord(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long id = (Long) jsonObject.get("del");
            Long q = (Long) jsonObject.get("sp");
            stmt.executeUpdate(
                    "update requestTable set Status='D' where OrderID='" + id + "' and Quantity='" + q + "'");
                    stmt.close();
                    con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }

    }

    public static void pendingRecord(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long id = (Long) jsonObject.get("del");
            Long q = (Long) jsonObject.get("sp");
            stmt.executeUpdate(
                    "update requestTable set Status='P' where OrderID='" + id + "' and Quantity='" + q + "'");
                    stmt.close();
                    con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }

    }

    public static void blockRecord(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long id = (Long) jsonObject.get("del");

            stmt.executeUpdate("delete from signup where ID='" + id + "'");
            stmt.close();
            con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }

    }

    public static int superSignIn(String mailin, String passin) {
        int count = 0;
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");

            String query = "SELECT COUNT(*) AS count FROM adminTable WHERE Username = ? AND Password = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, mailin);
                ps.setString(2, passin);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        count = rs.getInt("count");
                    }
                }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static JSONArray demandServer() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection
                    .prepareStatement(
                            "select ModelNo,count(ModelNo) as Count  from requestTable where Status='R' group by ModelNo;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                String add = rs.getString("ModelNo");
                String dor = rs.getString("Count");
                obj.put("ModelNo", add);
                obj.put("Count", dor);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONArray remainingServer() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("select * from availableServer");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                String add = rs.getString("ModelNo");
                String dor = rs.getString("AvailableCount");
                obj.put("ModelNo", add);
                obj.put("Remaining", dor);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONArray approvedCount() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection
                    .prepareStatement("select count(*) as RowCount from requestTable where Status='A';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                int count = rs.getInt("RowCount");
                obj.put("RowCount", count);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void refillServer(JSONObject jsonObject) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            Long Mid = (Long) jsonObject.get("MN");
            Long Quantity = (Long) jsonObject.get("Quantity");
            PreparedStatement ps = con.prepareStatement(
                    "select count(*) as PendingCount from requestTable where Status='P' and ModelNo='" + Mid + "';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Long count = rs.getLong("PendingCount");
                if (count < Quantity) {
                    Long diff = Quantity - count;
                    stmt.executeUpdate(
                            "update requestTable set Status='A' where ModelNo='" + Mid + "' and Status='P';");
                    stmt.executeUpdate("update availableServer set AvailableCount=AvailableCount+'" + diff
                            + "' where ModelNo='" + Mid + "';");

                } else {
                    stmt.executeUpdate("update requestTable set Status='A' where ModelNo='" + Mid
                            + "' and Status='P' limit " + Quantity + ";");
                }
            }
rs.close();
ps.close();
stmt.close();
con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }
    }

    public static void cartRecord(JSONObject data) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();

            JSONArray cartArray = (JSONArray) data.get("Cart");
            int itemCount = cartArray.size();
            for (int i = 0; i < itemCount; i++) {
                JSONObject cartObject = (JSONObject) cartArray.get(i);

                Long id = (Long) cartObject.get("id");
                Long mn = (Long) cartObject.get("modelNO");
                String name = (String) cartObject.get("name");
                String company = (String) cartObject.get("company");
                String model = (String) cartObject.get("model");
                String processor = (String) cartObject.get("processor");
                String memory = (String) cartObject.get("memory");
                String harddrive = (String) cartObject.get("harddrive");
                String additional = (String) cartObject.get("additional");

                stmt.executeUpdate(
                        "INSERT INTO requestTable (Quantity, ID, ModelNo, Name, Company, Model, Processor, Memory, HardDrive, Additional, DateofRequest, Status) VALUES ("
                                + (i + 1) + ", "
                                + id + ", "
                                + mn + ", '" + name + "', '" + company + "', '" + model + "', '" + processor + "', '"
                                + memory
                                + "', '" + harddrive + "', '" + additional + "', NOW(), 'R')"

                );

                if (i > 0) {
                    stmt.executeUpdate(
                            "CREATE TEMPORARY TABLE TempTable AS (SELECT MAX(OrderID) AS MaxOrderID FROM requestTable);");
                    stmt.executeUpdate("UPDATE requestTable SET OrderID = OrderID - " + i
                            + " WHERE OrderID = (SELECT MaxOrderID FROM TempTable);");
                    stmt.executeUpdate("DROP TEMPORARY TABLE TempTable;");
                }

            }

            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.INFO, "Inserted plan data into database");

            stmt.close();
            con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }
    }

    public static void returnServer(JSONObject jsonObject) {
        try {
            Long q = (Long) jsonObject.get("Quantity");
            Long order = (Long) jsonObject.get("OrderID");
            Long mn = (Long) jsonObject.get("ModelNo");
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            int count;
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT COUNT(*) AS count FROM requestTable WHERE Status = 'P' and ModelNo='" + mn + "'");
            if (resultSet.next()) {
                count = resultSet.getInt("count");

                if (count == 0) {
                    stmt.executeUpdate(
                            "update userBalance set balance=balance+(SELECT Price FROM availableServer WHERE ModelNo = '"
                                    + mn + "')");
                    stmt.executeUpdate(
                            "update adminTable set Balance=Balance-(SELECT Price FROM availableServer WHERE ModelNo = '"
                                    + mn + "')");
                    stmt.executeUpdate(
                            "update availableServer set AvailableCount=AvailableCount+1 where ModelNo='" + mn + "'");
                } else {
                    stmt.executeUpdate("update requestTable set Status='RS' where OrderID='" + order
                            + "' and Quantity='" + q + "'");
                    stmt.executeUpdate(
                            "update userBalance set balance=balance-(SELECT Price FROM availableServer WHERE ModelNo = '"
                                    + mn + "') where ID=(select ID from requestTable where Status='P' and ModelNo='"
                                    + mn + "' limit 1 )");
                    stmt.executeUpdate(
                            "update requestTable set Status='A' where Status='P' and ModelNo='" + mn + "' limit 1;");
                    stmt.executeUpdate(
                            "update userBalance set balance=balance+(SELECT Price FROM availableServer WHERE ModelNo = '"
                                    + mn + "') where ID=(select ID from requestTable where OrderID='" + order
                                    + "' and Quantity='" + q + "')");


                    stmt.executeUpdate(
                             "UPDATE userBalance SET balance = balance + (SELECT Price FROM availableServer WHERE ModelNo = '"
                                    + mn + "') WHERE ID = (SELECT ID FROM requestTable WHERE OrderID = '" + order + "' AND Quantity = '" + q + "')"
                                    );


                                    

                                    
                }
            }
            resultSet.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            Logger logger = Logger.getLogger("MyLog");
            logger.log(Level.SEVERE, "Error inserting plan data into database: " + e.getMessage());
        }

    }



    public static JSONArray userBalance() {
        JSONArray array = new JSONArray();
        try {
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ServerManagement", "deva", "deva");
            PreparedStatement ps = connection.prepareStatement("select * from userBalance");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                String add = rs.getString("ID");
                String dor = rs.getString("balance");
                obj.put("ID", add);
                obj.put("balance", dor);
                array.add(obj);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

}
