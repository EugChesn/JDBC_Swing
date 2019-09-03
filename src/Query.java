import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Query {
    final private static String pizza = "pizza";
    final private static String orders = "orders";
    final private static String suppliers = "suppliers";
    final private static String ingredients = "ingredients";

    public static ArrayList<String>getColumnsName(String nameTable){
        ArrayList<String>ColumnsName = new ArrayList<>();
        if(Connect.Con != null){
            try{
                DatabaseMetaData meta = Connect.Con.getMetaData();
                ResultSet rs = meta.getColumns("complete_bd",null,nameTable,null);
                while(rs.next()){
                    if(!rs.getString("COLUMN_NAME").contains("id"))
                        ColumnsName.add(rs.getString("COLUMN_NAME"));
                }
            }catch (SQLException ex){
                System.out.println("COLUMN_NAME get Problem");
            }
        }
        return ColumnsName;
    }
    public static ArrayList<String>getName(String nameTable,String nameField) {//получить имя из таблицы по указанному названию поля
        ResultSet result;
        ArrayList<String> listPizza = new ArrayList<>();
        if (Connect.Con != null) {
            try {
                String query;
                if(nameTable==pizza) {
                    query = "SELECT " + nameField + " FROM " + nameTable + " where ArchiveOff = ?;";
                    PreparedStatement stmt=Connect.Con.prepareStatement(query);
                    stmt.setInt(1,0);
                    result=stmt.executeQuery();
                }else{
                    query="SELECT " + nameField + " FROM " + nameTable + ";";
                    PreparedStatement stmt=Connect.Con.prepareStatement(query);
                    result=stmt.executeQuery();
                }
                int columns = result.getMetaData().getColumnCount(); //количество столбцов
                while (result.next()) {
                    for (int i = 1; i <= columns; i++) {
                        listPizza.add(result.getString(1));
                    }
                }
            } catch (SQLException ex) {
                System.out.println("getName problem");
            }
        }
        return listPizza;
    }
    public static Integer getId(String NameTables,String idName,String NameField ,String Value){ //получения id
        ResultSet result;
        Integer Ing = null;
        if (Connect.Con != null) {
            try {
                String query;
                query = "SELECT " +idName+ " FROM " +NameTables+ " where " +NameField+ " = ?;";
                PreparedStatement stmt=Connect.Con.prepareStatement(query);
                stmt.setString(1,Value);
                result=stmt.executeQuery();

                int columns = result.getMetaData().getColumnCount(); //количество столбцов
                while (result.next()) {
                    for (int i = 1; i <= columns; i++) {
                        Ing = result.getInt(1);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("getIdIngredients problem");
            }
        }
        return Ing;
    }
    public static ArrayList<String> ShowTablesQuery() { //Чтение имен таблиц бд
        ResultSet tables;
        ArrayList <String> NamesTable = new ArrayList<>();
        if (Connect.Con != null) {
            try {
                Statement statement;
                statement = Connect.Con.createStatement();
                String queryTables = "show tables;";
                tables = statement.executeQuery(queryTables);
                int columns = tables.getMetaData().getColumnCount();//количество столбцов
                while (tables.next()) {
                    for (int i = 1; i <= columns; i++) {
                        if (!tables.getString(i).contains("_")){
                            NamesTable.add(tables.getString(i));
                        }
                    }
                }
            } catch (SQLException ex) {
            }
        }
        return NamesTable;
    }
    public static ArrayList<String> linkTable(String nameTable){
        ArrayList<String>result = new ArrayList<>();
        if(nameTable.equals(pizza)){
            result.add(orders);
            result.add(ingredients);
        }
        else if(nameTable.equals(ingredients)){
            result.add(pizza);
            result.add(suppliers);
        }
        else if(nameTable.equals(orders)){
            result.add(pizza);
        }
        else if(nameTable.equals(suppliers)){
            result.add(ingredients);
        }
        return  result;
    }
    public static String[][] ReadAllQuery(String nameTable,Vector<String>header) { //Чтение указанной таблицы из бд
        ResultSet result = null;
        String[][] rs = null;
        if (Connect.Con != null) {
            try {
                String query = "SELECT * FROM " + nameTable + ";";
                PreparedStatement stmt=Connect.Con.prepareStatement(query);
                result = stmt.executeQuery();
                ResultSetMetaData data=result.getMetaData();

                int ColumnCount = data.getColumnCount();
                for(int i = 1;i <= ColumnCount;i++){
                    header.add(data.getColumnName(i));
                }

                int RowCount;
                result.last();
                RowCount=result.getRow();
                result.beforeFirst();

                rs = new String[RowCount][];
                int i=0;
                while(result.next()){
                    rs[i] = new String[ColumnCount];
                    for(int j = 0;j < ColumnCount; j++){
                        rs[i][j]= result.getString(j+1);
                    }
                    i++;
                }
            } catch (SQLException ex) {
            }
        }
        return rs;
    }
    public static void InsertPizzaQuery(String NamePiz,Integer Diametr,
                                        Integer Price,ArrayList<Integer>idIngr){ //вставка пиццы в бд
        if (Connect.Con != null) {
            try {
                String queryPiz = "INSERT INTO pizza (NamePiz,Diametr,Price,ArchiveOff) values(?,?,?,?);";
                PreparedStatement stmtPiz = Connect.Con.prepareStatement(queryPiz, Statement.RETURN_GENERATED_KEYS);
                stmtPiz.setString(1, NamePiz);
                stmtPiz.setInt(2, Diametr);
                stmtPiz.setInt(3, Price);
                stmtPiz.setInt(4, 0);
                try {
                    stmtPiz.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("insert pizza problem");
                }
                int idPiz = 0;
                int idIng = 0;

                try (ResultSet getKeyPiz = stmtPiz.getGeneratedKeys()) {
                    if (getKeyPiz.next()) {
                        idPiz = getKeyPiz.getInt(1);
                        System.out.println(idPiz);
                    }
                } catch (SQLException ex) {
                    System.out.println("getKeyPiz");
                }

                for (Integer in:idIngr
                     ) {
                    try {
                        String queryPizIng = "INSERT INTO pizza_ingredients (idPizza,idIngredient) values(?,?);";
                        PreparedStatement stmtPizIng = Connect.Con.prepareStatement(queryPizIng);
                        stmtPizIng.setInt(1, idPiz);
                        stmtPizIng.setInt(2, in);
                        stmtPizIng.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("insert pizza_ingredients problem");
                    }
                }
            }
            catch (SQLException ex){

            }
        }
    }
    public static void InsertOrdersQuery(String NameClient,String DateOrd,
                                         String TimeOrd,String AdressOrd,
                                         ArrayList<Integer>idPiz) { //вставка заказа в бд
        if (Connect.Con != null) {
            try {
                String queryOrd = "INSERT INTO orders (NameClient,DateOrder,TimeOrder,Adress) values(?,?,?,?);";
                PreparedStatement stmtOrd = Connect.Con.prepareStatement(queryOrd, Statement.RETURN_GENERATED_KEYS);
                stmtOrd.setString(1, NameClient);
                stmtOrd.setDate(2, java.sql.Date.valueOf(DateOrd));
                stmtOrd.setTime(3, java.sql.Time.valueOf(TimeOrd));
                stmtOrd.setString(4, AdressOrd);
                try {
                    stmtOrd.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("insert orders");
                }
                int idOrderGen = 0;
                try (ResultSet getKey = stmtOrd.getGeneratedKeys()) {
                    if (getKey.next()) {
                        idOrderGen = getKey.getInt(1);
                        System.out.println(idOrderGen);
                    }
                } catch (SQLException ex) {
                    System.out.println("getKey");
                }
                for (Integer in : idPiz
                        ) {
                    try {
                        String queryPizOrd = "INSERT INTO pizza_orders(idPizza,idOrder) values(?,?);";
                        PreparedStatement stmtPizOrd = Connect.Con.prepareStatement(queryPizOrd);
                        stmtPizOrd.setInt(1, in);
                        stmtPizOrd.setInt(2, idOrderGen);
                        System.out.println(stmtPizOrd.executeUpdate());

                    } catch (SQLException ex) {
                        System.out.println("insert piz_orders");
                    }
                }
            } catch (SQLException ex) {
            }
        }
    }
    public static void InsertSuppliersQuery(String NameSup,String ContactName,
                                            String ContactPhone,String NameIng,String UnitPrice) {
        if (Connect.Con != null) {
            try {
                String querySup = "INSERT INTO suppliers(NameSup,ContactName,ContactNumberPhone) values(?,?,?);";
                PreparedStatement stmtSupp = Connect.Con.prepareStatement(querySup,Statement.RETURN_GENERATED_KEYS);
                stmtSupp.setString(1, NameSup);
                        stmtSupp.setString(2, ContactName);
                        stmtSupp.setString(3, ContactPhone);
                        try {
                            stmtSupp.executeUpdate();
                        }
                        catch (SQLException ex){
                        }
                        int idSuppGen=0;
                        try(ResultSet getKeySup=stmtSupp.getGeneratedKeys()){//получаем id поставщика ингредиента
                            if (getKeySup.next()) {
                                idSuppGen = getKeySup.getInt(1);
                                System.out.println(idSuppGen);
                            }
                        }
                        catch (SQLException ex){
                            System.out.println("getKey");
                        }

                        String queryIng="INSERT INTO ingredients (idSupplier,NameIng,UnitPrice) values (?,?,?);";
                        PreparedStatement stmtIng=Connect.Con.prepareStatement(queryIng);
                        stmtIng.setInt(1,idSuppGen);
                        stmtIng.setString(2,NameIng);
                        stmtIng.setInt(3,Integer.parseInt(UnitPrice));
                        try{
                            stmtIng.executeUpdate();
                        }catch (SQLException ex){
                            System.out.println("insert inredients problem");
                        }
            } catch (SQLException ex) {
            }
        }
    }
    public static void UpdateQuery(String nameTable,String nameField,String newValField,
                            String FieldSearch,String ValFieldSearch) {
        if (Connect.Con != null) {
            try {
                        String queryPiz="UPDATE " +nameTable+ " SET " +nameField+ " = ? where " +FieldSearch+ " = ? ";
                        PreparedStatement stmtPiz = Connect.Con.prepareStatement(queryPiz);
                        stmtPiz.setString(1,newValField);
                        stmtPiz.setString(2, ValFieldSearch);
                        try{
                            stmtPiz.executeUpdate();
                        }catch (SQLException ex){
                            JOptionPane.showMessageDialog(null, "Update problem,probably data type", "Error", JOptionPane.ERROR_MESSAGE);
                        }
            } catch (SQLException ex) {
            }
        }
    }
    public static void DeleteQuery(String nameTable,String nameField,String ValField){
        if(Connect.Con!=null){
            try{
                String queryOrd="DELETE FROM " +nameTable+ " where " +nameField+ " = ?;";
                PreparedStatement stmtOrd=Connect.Con.prepareStatement(queryOrd);
                stmtOrd.setString(1,ValField);
                try{
                    stmtOrd.executeUpdate();
                }catch (SQLException ex){
                    System.out.println("Delete orders");
                }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Delete problem,probably data type", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static String[][] SelectJoin(ArrayList<String>FieldString,
                                        ArrayList<String>joinTable,
                                        Vector<String>header){
        ResultSet result = null;
        String[][] rs = null;
        if (Connect.Con != null) {
            try {
                String query = "SELECT ";
                for (int i = 0;i < FieldString.size();i++){
                    query += FieldString.get(i);
                    if(i == FieldString.size()-1)
                        break;
                    query += ",";
                }
                query += " FROM " + joinTable.get(0) + " \n"; //sourceTable
                for(int i = 1;i < joinTable.size();i++){
                    if(joinTable.get(i).equals(orders)){
                        query += "JOIN pizza_orders ON pizza_orders.idPizza = pizza.idPizza ";
                        query += "JOIN " +joinTable.get(i)+ " ON pizza_orders.idOrder = orders.idOrder ";
                    }
                    else if(joinTable.get(i).equals(pizza) && joinTable.get(0).equals(orders)){
                        query += "JOIN pizza_orders ON pizza_orders.idOrder = orders.idOrder ";
                        query += "JOIN " +joinTable.get(i)+ " ON pizza_orders.idPizza = pizza.idPizza ";
                    }
                    else if(joinTable.get(i).equals(pizza) && joinTable.get(0).equals(ingredients)){
                        query += "JOIN pizza_ingredients ON pizza_ingredients.idIngredient = ingredients.idIngredient ";
                        query += "JOIN " +joinTable.get(i)+ " ON pizza_ingredients.idPizza = pizza.idPizza ";
                    }
                    else if(joinTable.get(i).equals(suppliers) && joinTable.get(0).equals(ingredients)){
                        query += "JOIN " +joinTable.get(i)+ " ON suppliers.idSupplier = ingredients.idSupplier ";
                    }
                    else if(joinTable.get(i).equals(ingredients) && joinTable.get(0).equals(pizza)){
                        query += "JOIN pizza_ingredients ON pizza_ingredients.idPizza = pizza.idPizza ";
                        query += "JOIN " +joinTable.get(i)+ " ON pizza_ingredients.idIngredient = ingredients.idIngredient ";

                    }
                    else if(joinTable.get(i).equals(ingredients) && joinTable.get(0).equals(suppliers)){
                        query += "JOIN " +joinTable.get(i)+ " ON suppliers.idSupplier = ingredients.idSupplier ";
                    }
                }

                PreparedStatement stmt=Connect.Con.prepareStatement(query);
                result = stmt.executeQuery();
                ResultSetMetaData data=result.getMetaData();
                int ColumnCount = data.getColumnCount();
                for(int i = 1;i <= ColumnCount;i++){
                    header.add(data.getColumnName(i));
                }
                int RowCount;
                result.last();
                RowCount=result.getRow();
                result.beforeFirst();

                rs = new String[RowCount][];
                int i=0;
                while(result.next()){
                    rs[i] = new String[ColumnCount];
                    for(int j = 0;j < ColumnCount; j++){
                        rs[i][j]= result.getString(j+1);
                    }
                    i++;
                }
            } catch (SQLException ex) {
                System.out.println("Join problem");
            }
        }
        return rs;
    }
}