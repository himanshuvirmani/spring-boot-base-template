package com.springboot.demo.service.criteria;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
public class CitySearchCriteria implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  public CitySearchCriteria(String name) {
    Assert.notNull(name, "Name must not be null");
    this.name = name;
  }
}
