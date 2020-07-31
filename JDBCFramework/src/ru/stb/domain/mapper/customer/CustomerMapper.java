package ru.stb.domain.mapper.customer;

import ru.stb.domain.dao.Mapper;
import ru.stb.domain.entity.customer.Customer;
import ru.stb.domain.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper implements Mapper<Customer> {

    @Override
    public List<Customer> map(ResultSet resultSet) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while(resultSet.next()) {
            customers.add(
                    new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("address")
                    )
            );
        }
        return customers;
    }

}
