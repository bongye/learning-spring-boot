package com.pzeya.learning.spring.boot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employee")
public class Employee {
  @Id String id;
  String firstName;
  String lastName;
}
