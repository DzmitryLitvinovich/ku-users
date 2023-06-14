import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String FIND_BY_ID_QUERY = """
            SELECT * FROM users WHERE id = ?
    """;

    private static final String SAVE = """
            INSERT INTO users (name, surname, age, username, password, inserted_date_at_utc)
            VALUES (?, ?, ?, ?, ?, ?)
    """;

    private static final String UPDATE = """
            UPDATE users
            SET name = ?
            WHERE id = ?
    """;

    private static final String DELETE = """
            DELETE FROM users
            WHERE id = ?
    """;

    public User findById(Long id) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = new User();
                while (resultSet.next()) {
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setAge(resultSet.getInt("age"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setInsertedAtUtc(resultSet.getTimestamp("inserted_date_at_utc").toLocalDateTime());
                }
                return user;
            }
        }
    }

    public void save(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)
        ) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setTimestamp(6, new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));

            preparedStatement.execute();
        }
    }

    public void update(int id, String name) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {

        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        }
    }
}
