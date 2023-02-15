import java.sql.*;

public class DBTest {
    private static final String USER_NAME = "sql12596781";
    private static final String DATABASE_NAME = "sql12596781";
    private static final String PASSWORD = "V99tikqyKV";
    private static final String PORT = "3306";
    private static final String SERVER = "sql12.freemysqlhosting.net";

    /*
    config_id – (primary key, int, not null)
o config_name – (varchar[45], not null)
o config_data – (varchar[100], not null)
     */

    public static void main(String[] args) throws SQLException {
        String s = ("jdbc:mysql://"+SERVER+":"+PORT);
        Connection con = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT, USER_NAME, PASSWORD);
        System.out.println(con);
//        createDogTable(con);
//        insertDog(con,"buck", "pintcher", 13);
//        insertDog(con,"rex", "bulldog", 3);
//        insertDog(con,"lucky", "golden retriever", 13);
//        updateDogAgeByBreed(con,15 ,"bulldog");
//        printDogsTable(con);
//        deleteThirdDog(con);
        printDogsTable(con);

        con.close();
    }

    private static void createDogTable(Connection con) throws SQLException {
        String statementToExecute = "CREATE TABLE " + DATABASE_NAME + ".`dogs`(`dog_id` INT NOT NULL,`name` VARCHAR(40) NOT NULL, `breed` VARCHAR(30) NOT NULL, `age` INT NOT NULL, PRIMARY KEY (`dog_id`));";
        con.createStatement().execute(statementToExecute);
    }

    private static void insertDog(Connection con, String name, String breed, int age) throws SQLException {
        int id = getDogId(con)+1;
        String statementToExecute = "INSERT INTO " + DATABASE_NAME + ".dogs (`dog_id`, `name`, `breed`, `age`  ) VALUES ('" + id + "', '" + name + "', '" + breed + "', '" + age + "');";
        con.createStatement().execute(statementToExecute);
    }

    private static int getDogId(Connection con) throws SQLException {
        int result=0;
        String statementToExecute = "SELECT * FROM " + DATABASE_NAME + ".dogs;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(statementToExecute);
        while(rs.next()){
            //Retrieve by column name
            int id  = rs.getInt("dog_id");
            result=id;
        }
        rs.close();
        return result;
    }

    private static void printDogsTable(Connection con) throws SQLException {
        String statementToExecute = "SELECT * FROM " + DATABASE_NAME + ".dogs;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(statementToExecute);
        while(rs.next()){
            //Retrieve by column name
            int id  = rs.getInt("dog_id");
            String name  = rs.getString("name");
            String breed  = rs.getString("breed");
            int age  = rs.getInt("age");

            System.out.println("ID: " + id);
            System.out.println("name: " + name);
            System.out.println("breed: " + breed);
            System.out.println("age: " + age);

        }
        rs.close();
    }
    private static void deleteThirdDog(Connection con) throws SQLException {
        if (getDogId(con)<3 && getDogId(con)>0){
            String statementToExecute = "DELETE FROM `" + DATABASE_NAME + "`.`dogs` WHERE `dog_id`='"+getDogId(con)+"';";
            con.createStatement().execute(statementToExecute);
        } else {
            String statementToExecute = "DELETE FROM `" + DATABASE_NAME + "`.`dogs` WHERE `dog_id`='"+3+"';";
            con.createStatement().execute(statementToExecute);
        }

    }

    private static void updateDogAgeByBreed(Connection con, int age, String breed) throws SQLException {
        String statementToExecute = "UPDATE `" + DATABASE_NAME + "`.`dogs` SET `age`='"+age+"' WHERE `breed`='"+breed+"';";
        con.createStatement().executeUpdate(statementToExecute);
    }
}