package pages.united;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.mightypetra.testautomation.DriverUtils.TIMEOUT;

public class HomePage {

    private static final SelenideElement frmBookingPanel = $("#travelTab-panel");

    private static final SelenideElement rdgOneWay = frmBookingPanel.$("#oneway");
    private static final SelenideElement rdgRoundTrip = frmBookingPanel.$("#roundtrip");
    private static final SelenideElement txbOrigin = frmBookingPanel.$("#bookFlightOriginInput");
    private static final SelenideElement txbDestination = frmBookingPanel.$("#bookFlightDestinationInput");
    private static final SelenideElement originInputMenu = frmBookingPanel.$("#bookFlightOriginInput-menu");
    private static final  SelenideElement destinationInputMenu = frmBookingPanel.$("#bookFlightDestinationInput-menu");
    private static final SelenideElement txbDepartDate = frmBookingPanel.$("#DepartDate");
    private static final SelenideElement btnCabinType = frmBookingPanel.$("#cabinType");
    private static final SelenideElement cabinTypeListbox = frmBookingPanel.$("#cabinType ~ div ul[role='listbox']");
    private static final SelenideElement btnFindFlights = frmBookingPanel.$("button[type='submit']");
    private static final SelenideElement loader = $("#fl-results-loader-full");


    public static void fillSearchData(Map<String, String> data) {

        frmBookingPanel.waitUntil(Condition.visible,TIMEOUT);

        data.forEach((field, value) -> {

            switch (field) {
                case "flight_type":
                    if (value.equals("one-way")) {
                        rdgOneWay.click();
                    } else {
                        rdgRoundTrip.click();
                    }
                    break;
                case "origin":
                    setValue(txbOrigin, value);
                    originInputMenu.waitUntil(Condition.appears, TIMEOUT);
                    originInputMenu.$x("./li/button", 0).click();
                    break;
                case "destination":
                    setValue(txbDestination, value);
                    destinationInputMenu.waitUntil(Condition.appears, TIMEOUT);
                    destinationInputMenu.$x("./li/button", 0).click();
                    break;
                case "date":
                    txbDepartDate.clear();
                    txbDepartDate.setValue(value);
                    break;
                case "cabin_type":
                    btnCabinType.click();
                    cabinTypeListbox.waitUntil(Condition.appears, TIMEOUT);
                    cabinTypeListbox.$(String.format("li[aria-label='%s']", value)).click();
                    break;
            }

        });

    }

    public static void submitSearch(){
        btnFindFlights.submit();
        loader.waitWhile(Condition.visible, TIMEOUT);
        SearchResultPage.tableFlightResults.waitUntil(Condition.appears, TIMEOUT);
    }

    private static void setValue(SelenideElement textBox, String value) {
        textBox.waitUntil(Condition.enabled, TIMEOUT);
        textBox.clear();
        textBox.waitUntil(Condition.empty, TIMEOUT);
        textBox.setValue(value);
    }

}
