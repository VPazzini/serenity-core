package net.thucydides.core.webdriver.chrome

import net.serenitybdd.core.webdriver.driverproviders.ChromeDriverCapabilities
import net.thucydides.core.guice.Injectors
import net.thucydides.core.util.EnvironmentVariables
import spock.lang.Specification
import spock.lang.Unroll

class WhenPassingChromeExperimentalOptionsToWebdriver extends Specification {

    @Unroll
    def "should accept experimental options"() {
        given:
        System.setProperty("chrome_experimental_options.test1", "test1");
        System.setProperty("chrome_experimental_options.test_boolean", "false");
        System.setProperty("chrome_experimental_options.test_int", "5");
        System.setProperty("chrome_experimental_option.test_false", "test1");
        System.setProperty("chrome_experimental_options.test1.test2", "some bigger value with a lot of characters");
        def chromeCap = new ChromeDriverCapabilities(Injectors.getInjector().getProvider(EnvironmentVariables.class).get(), "");
        when:
        def options = chromeCap.getCapabilities()
        then:

        (options.getCapability("goog:chromeOptions") as TreeMap<String, Object>).containsKey("test1")
        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).get("test1") == "test1"
        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).get("test1") instanceof String

        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).containsKey("test1.test2")
        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).get("test1.test2") == "some bigger value with a lot of characters"

        !((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).containsKey("test_false")

        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).get("test_boolean") == false
        ((TreeMap<String, Object>) options.getCapability("goog:chromeOptions")).get("test_int") instanceof Integer


    }


}
