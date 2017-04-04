package refactor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AddressDb {
    /**
     * @return Method creates new database connection with defined params
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
    }

    /**
     * Insert person record into the database
     * Method isn't used in other code
     *
     * @param person object to insert into database
     */
    public void addPerson(Person person) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into AddressEntry values (?, ?, ?)")) {
                statement.setLong(1, System.currentTimeMillis());
                statement.setString(2, person.getName());
                statement.setString(3, person.getPhoneNumber().getNumber());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks up the given person, null if not found.
     *
     * @param name - name of the peson to find
     * @return Person object by given name or null if it not dound
     */
    public Person findPerson(String name) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from AddressEntry where name = ?")) {
                statement.setString(1, name);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        String foundName = rs.getString("name");
                        PhoneNumber phoneNumber = new PhoneNumber(rs.getString("phoneNumber"));
                        return new Person(foundName, phoneNumber);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return method returns all person records or empty List
     */
    public List<Person> getAll() {
        List<Person> entries = new LinkedList<>();

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery("select * from AddressEntry")) {
                    while (result.next()) {
                        String name = result.getString("name");
                        PhoneNumber phoneNumber = new PhoneNumber(result.getString("phoneNumber"));
                        Person person = new Person(name, phoneNumber);
                        entries.add(person);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }

}
