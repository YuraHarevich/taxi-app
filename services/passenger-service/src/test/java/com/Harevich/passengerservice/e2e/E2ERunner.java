package com.Harevich.passengerservice.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html"},
        glue = "com/Harevich/passengerservice/e2e",
        features = {"src/test/resources/features"}
)
@CucumberContextConfiguration
public class E2ERunner {
}
