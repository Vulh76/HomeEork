package ru.stb.domain.dao.customer;

import ru.stb.domain.dao.AbstractRepository;
import ru.stb.domain.dao.Mapper;
import ru.stb.domain.entity.customer.Customer;

public class CustomerRepository extends AbstractRepository<Customer> {

    public CustomerRepository(String jdbcUrl, String user, String password, String driver, Mapper<Customer> mapper) {
        super(jdbcUrl, user, password, driver, mapper);
    }

}
