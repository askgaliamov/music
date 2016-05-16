package com.mucis.input

import org.hibernate.validator.constraints.NotEmpty
import spock.lang.Specification


class ValidationFilterSpec extends Specification {

  def filter = new ValidationFilter()

  def "return false if any invalids"() {
    expect:
    !filter.test(new Bean(""))
  }

  def "return true if no invalids"() {
    expect:
    filter.test(new Bean("any"))
  }

  class Bean {
    Bean(String value) {
      this.value = value
    }
    @NotEmpty
    String value;
  }

}
