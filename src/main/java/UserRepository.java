import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String SELECT_QUERY = """
        SELECT * FROM users
    """;

    private static final String FIND_BY_ID_QUERY = """
        SELECT * FROM users WHERE id = ?
    """;

    private static final String SAVE = """
        INSERT INTO users (name, surname, age, username, password, inserted_date_at_utc)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    private static final String UPDATE = """
        UPDATE users SET name = ?, surname = ?, age = ?, username = ?, password = ?, inserted_date_at_utc = ? WHERE id = ?
    """;

    private static final String DELETE = """
        DELETE FROM users WHERE id = ?
    """;


    private User fillUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setAge(resultSet.getInt("age"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setInsertedDateAtUtc(resultSet.getTimestamp("inserted_date_at_utc").toLocalDateTime());
        return user;
    }

    private void buildQuery(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getUsername());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setTimestamp(6, new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = fillUser(resultSet);

                users.add(user);
            }
        }
        return users;
    }

    public User findById(Long id) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = new User();
                while (resultSet.next()) {
                    user = fillUser(resultSet);
                }
                return user;
            }
        }
    }

    public void save(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)
        ) {
            buildQuery(preparedStatement, user);

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    user.setId(id);
                }
            }
        }
    }



    public void update(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            buildQuery(preparedStatement, user);
            preparedStatement.setLong(7, user.getId());

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
