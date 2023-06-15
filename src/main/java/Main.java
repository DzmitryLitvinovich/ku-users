import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        UserRepository userRepository = new UserRepository();

        List<User> users = userRepository.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

//        User user = userRepository.findById(2L);
//        System.out.println(user);

//        User user = new User();
//        user.setName("Java_3");
//        user.setSurname("Surname_Java_3");
//        user.setAge(25);
//        user.setUsername("Username_Java_3");
//        user.setPassword("Password_Java_3");
//        user.setInsertedAtUtc(LocalDateTime.now());
//
//        userRepository.save(user);

//        userRepository.update(2, "Java_2");

//        userRepository.delete(3L);

    }
}
