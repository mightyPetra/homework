package pages.united;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.*;
import com.mightypetra.testautomation.DriverUtils;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static constants.FlightDataHeaders.*;

public class SearchResultPage {

    static final SelenideElement sectionFlightResults = $("#flight-result-elements");
    static final SelenideElement tableFlightResults = $("#fl-results");
    private static final SelenideElement btnShowAllFlights = $("#a-results-show-all");
    private static final SelenideElement btnBasicEconomy = $("#column-ECO-BASIC");


    public static JsonArray collectTableData() {
        tableFlightResults.waitUntil(Condition.appears, DriverUtils.TIMEOUT);
        ElementsCollection rows = Selenide.elements("li.flight-block");
        JsonArray flightsJson = new JsonArray();

        rows.forEach(row ->
        {
            JsonObject o = new JsonObject();
            o.addProperty(DEPART, getCleanText(row.$("div.flight-time-depart"), "\n"));
            o.addProperty(ARRIVE, getCleanText(row.$("div.flight-time-arrive"), "\n"));
            o.addProperty(STOPS, row.$("*.connection-count").text());
            o.addProperty(DURATION, row.$("a.flight-duration").text());
            o.addProperty(PRICE, getCleanText(row.$("div.price-point-wrap", 0), "[^$\\d]"));

            flightsJson.add(o);
        });

        return flightsJson;
    }

    private static String getCleanText(SelenideElement element, String regex) {
        return element.text().replaceAll(regex, " ").trim();
    }


    public static void showAllFlightResults() {
        btnShowAllFlights.click();
        tableFlightResults.waitUntil(Condition.appears, DriverUtils.TIMEOUT);
    }

    public static void sortFlightsByBasicEconomyFare(String order) {
        while (!btnBasicEconomy.getAttribute("class").contains(order)) {
            btnBasicEconomy.click();
        }
    }

    public static void printFlightInfo(JsonArray j) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Resulting json array: \n" + g.toJson(j));
    }


    public static JsonArray filterJsonOutBy(JsonArray flights, String field, String value) {

        List<JsonElement> toRemove = new ArrayList<>();

        flights.forEach(flight -> {
            if (flight.getAsJsonObject().get(field).getAsString().equals(value))
                toRemove.add(flight);
        });

        toRemove.forEach(flights::remove);

        return flights;
    }

}
