package pl.sda.jdbc.starter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassicModelsManager {
    private static ConnectionFactory connectionFactory;
    //private ConnectionFactory connectionFactory;

    public ClassicModelsManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void printAllOffices() throws SQLException {
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            String query = "SELECT * FROM offices;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String officeCode = result.getString("officeCode");
                System.out.println("officeCode = " + officeCode);
                String city = result.getString("city");
                System.out.println("city = " + city);
                String phone = result.getString("phone");
                System.out.println("phone = " + phone);
                String addressLine1 = result.getString("addressLine1");
                String addressLine2 = result.getString("addressLine2");
                System.out.println("address = " + addressLine1 + " " + addressLine2);
                String state = result.getString("state");
                System.out.println("state = " + state);
                String country = result.getString("country");
                System.out.println("country = " + country);
                String postalCode = result.getString("postalCode");
                System.out.println("postalCode = " + postalCode);
                String territory = result.getString("territory");
                System.out.println("territory = " + territory);
            }
        }

    }

    public void updateProductPrices(double updateRate) {
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            // String query = "INSERT INTO table_1(id, name) VALUES(1, ‘Bob’);";
            // statement.executeUpdate(query);
            String query = "UPDATE products SET buyPrice=buyPrice*1.1";
            statement.executeUpdate(query);
            query = "SELECT buyPrice FROM products";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                double buyPrice = result.getDouble("buyPrice");
                System.out.println("buyPrice = " + buyPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllCustomersWithSalesRepName() {
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            //String query = "SELECT _id FROM OFFICES);";
            //statement.executeUpdate(query);
            String query = "SELECT customerName, salesRepEmployeeNumber, lastName, firstName " +
                    "FROM customers " +
                    "LEFT JOIN employees " +
                    "ON customers.salesRepEmployeeNumber = employees.employeeNumber;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String customerName = result.getString("customerName");
                String lastName = result.getString("lastName");
                String firstName = result.getString("firstName");
                String salesRepEmployeeNumber = result.getString("salesRepEmployeeNumber");
                System.out.println("customer= " + customerName + "; " + salesRepEmployeeNumber + "; " + lastName + "; " + firstName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> findProductByName(String nameMatcher) throws SQLException {
        List<Product> productNameList = new ArrayList<>();
        String query = "SELECT productCode, productName FROM products WHERE productName LIKE ?";
        try (PreparedStatement statement = connectionFactory.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + nameMatcher + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {

                Product p = new Product();
                p.setProductCode(result.getString("productCode"));
                p.setProductName(result.getString("productName"));
                System.out.println("product:" + p.getProductCode() + " - " + p.getProductName());
                productNameList.add(p);
            }
        }
        //System.out.println("productNameList = " + productNameList.toString());
        return productNameList;
    }

    public List<Order> findOrdersByEmloyeeId(int id) {
        return null;
    }

    public List<Order> findOrdersByDate(Date from, Date to) {
        return null;
    }

    public static void main(String[] args) throws SQLException {

        ClassicModelsManager classicModelsManager = new ClassicModelsManager(new ConnectionFactory());
        List<Product> list = findProductByName(" ");
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Wybierz metodę do uruchomienia:");
//        System.out.println("1 - wypisz wszystkie biura");
//        System.out.println("2 - podnies cenę produktów o 10%");
//        System.out.println("3 - wypisz klientów wraz z danymi opiekuna handlowego");
//        String s = scan.nextLine();
//        int menu = Integer.parseInt(s);
//        switch (menu) {
//            case 1:
//                classicModelsManager.printAllOffices();
//                break;
//            case 2:
//                classicModelsManager.updateProductPrices(10.0);
//                break;
//            case 3:
//                classicModelsManager.printAllCustomersWithSalesRepName();
//                break;
//        }
    }


}
