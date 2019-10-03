package com.pzeya.learning.spring.boot.repository;

import com.pzeya.learning.spring.boot.employee.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RunWith(SpringRunner.class)
@DataMongoTest
public class EmployeeRepositoryTests {
  @Autowired MongoOperations mongoOperations;
  @Autowired ReactiveMongoOperations operations;

  @Before
  public void setUp() {
    mongoOperations.dropCollection(Employee.class);

    Employee e1 = new Employee();
    e1.setId(UUID.randomUUID().toString());
    e1.setFirstName("Bilbo");
    e1.setLastName("Baggins");
    e1.setRole("burglar");
    mongoOperations.insert(e1);

    Employee e2 = new Employee();
    e2.setId(UUID.randomUUID().toString());
    e2.setFirstName("Frodo");
    e2.setLastName("Baggins");
    e2.setRole("ring bearer");
    mongoOperations.insert(e2);
  }

  @Test
  public void testSelectByExample() {
    Employee e = new Employee();
    e.setFirstName("Bilbo");
    Example<Employee> example = Example.of(e);

    Mono<Employee> singleEmployee =
        operations.findOne(new Query(byExample(example)), Employee.class);
    Assert.assertEquals(e.getFirstName(), singleEmployee.block().getFirstName());

    e = new Employee();
    e.setLastName("baggins");
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withIgnoreCase()
            .withMatcher("lastName", startsWith())
            .withIncludeNullValues();
    example = Example.of(e, matcher);
    Flux<Employee> multipleEmployees =
        operations.find(new Query(byExample(example)), Employee.class);

    operations.findOne(Query.query(where("firstName").is("Frodo")), Employee.class);
  }
}
