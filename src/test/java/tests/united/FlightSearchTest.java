package tests.united;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.mightypetra.testautomation.DriverUtils;
import constants.FlightDataHeaders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.united.HomePage;
import pages.united.SearchResultPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

class FlightSearchTest {

    @BeforeAll
    static void setUp() {
        DriverUtils.setDriver(DriverUtils.FIREFOX);
        open("https://www.united.com");

    }

    @Test
    @DisplayName("(╯°□°）╯ Search for a flight and export results as JSON")
    void searchForFlightsWithGivenParameters() {

        Map<String, String> data = ImmutableMap.of(
                "flight_type", "one-way",
                "origin", "New York JFK",
                "destination", "MIA - All Airports",
                "date", "Aug 20",
                "cabin_type", "Economy"
        );

        HomePage.fillSearchData(data);
        HomePage.submitSearch();

        SearchResultPage.showAllFlightResults();
        SearchResultPage.sortFlightsByBasicEconomyFare("ascending");

        JsonArray flights = SearchResultPage.collectFlightData();

        SearchResultPage.filterJsonOutBy(flights, FlightDataHeaders.PRICE, "");
        SearchResultPage.printFlightInfo(flights);

    }

    @AfterAll
    static void close() {
        closeWebDriver();
    }

}
