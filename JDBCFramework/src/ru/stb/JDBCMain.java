package ru.stb;

import ru.stb.domain.dao.customer.CustomerRepository;
import ru.stb.domain.entity.customer.Customer;
import ru.stb.domain.mapper.customer.CustomerMapper;
import ru.stb.domain.mapper.user.UserMapper;
import ru.stb.domain.dao.user.UserRepository;
import ru.stb.domain.entity.user.User;

import java.util.List;

public class JDBCMain {

    public static void main(String[] args) {
        UserMapper userMapper = new UserMapper();
        UserRepository userRepository = new UserRepository(
                "jdbc:h2:~/test",
                "sa",
                "1234",
                "org.h2.Driver",
                userMapper
        );
        User user1 = new User(1L, "Name1", 20, "12345566");
        User user2 = new User(2L, "Name2", 25, "12345567");
        userRepository.create(user1);
        userRepository.create(user2);
        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user.toString()));
        User firstUser = userRepository.findById(1);
        System.out.println("User with id 1 = " + firstUser);
        userRepository.delete(user1.getId().intValue());
        userRepository.delete(user2.getId().intValue());

        CustomerMapper customerMapper = new CustomerMapper();
        CustomerRepository customerRepository = new CustomerRepository(
                "jdbc:h2:~/test",
                "sa",
                "1234",
                "org.h2.Driver",
                customerMapper
        );
        Customer customer = new Customer(1L, "CustomerFirstName1", "CustomerFirstName2", "Sadovaya St");
        customerRepository.create(customer);
        System.out.println(customerRepository.findById(1));
        customerRepository.delete(customer.getId().intValue());

    }

}
