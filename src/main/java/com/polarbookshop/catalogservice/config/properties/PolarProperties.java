package com.polarbookshop.catalogservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author clement.tientcheu@cerebrau.com
 * @project catalog-service
 * @org Cerebrau
 */
@ConfigurationProperties(prefix = "polar")
public class PolarProperties {
    /**
     * A message to welcome users
     */
    private String greeting;
    @NestedConfigurationProperty
    private TestData testData;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public TestData getTestData() {
        return testData;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }

    public static class TestData {
        /**
         * Create sample Book data when true, false by default.
         */
        private boolean enable = false;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
