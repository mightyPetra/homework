package com.bilderlings.testautomation.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.Double.parseDouble;
import static java.util.UUID.randomUUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Ordering {

    private final long timeout = 5000;

    //registration data
    private final String name = "Bruce";
    private final String surname = RandomStringUtils.randomAlphabetic(10);
    private final String email = name+"."+surname+"@batmail.com";
    private final String password = randomUUID().toString();
    private final String birthDate = "19/02/2009";


    private String itemOne;
    private double itemOnePrice;
    private final double itemOneQuantity = 5;
    private double itemOneTotal;
    private String itemTwo;
    private double itemTwoPrice;

    private double orderTotal;

    @BeforeClass
    public void setUp() {
        System.setProperty("selenide.browser", "chrome");
        open("http://demo.prestashop.com");
        $("span.hide-header").click();
        switchTo().frame("framelive");
    }

    @Test
    public void registration() {

        //go to login page
        $("div.user-info").click();
        assertTrue($(byId("login-form")).is(visible));

        //go to registration page
        $("div.no-account").click();
        assertTrue($(byId("customer-form")).is(visible));

        //Register an account,
        $(byName("id_gender")).selectRadio("1");
        $(byName("firstname")).setValue(name);
        $(byName("lastname")).setValue(surname);
        $(byName("email")).setValue(email);
        $(byName("password")).setValue(password);
        $(byName("birthday")).setValue(birthDate).submit();

        //check you're logged in
        $(byText("Sign out")).waitUntil(visible, timeout);
        assertTrue($("div.user-info").has(text(name+" "+surname)), name+" "+surname+"is visible on top of the page");
    }

    @org.testng.annotations.Test(dependsOnMethods = {"registration"})
    public void shopping() {

        //Open "Accessories" section
        //Filter out only available items with price range $18-20
        final String filter = "$18.00 - $20.00";
        openFilteredAccessoriesPage(filter);

        //Check the items are correctly filtered
        assertTrue($("li.filter-block").has(text(filter)), "filter "+filter+" successfully applied");

        ElementsCollection products = $$("article.product-miniature.js-product-miniature");
        for (SelenideElement p : products){
            double price = priceToDouble(p.$("div.product-description").$("span.price").text());
            assertTrue(price<=20.0&&price>=18.0, "price "+price+" is within range of "+filter);
        }

        //Randomly choose one of items, increase quantity of items and add to cart
        int randomProductNr = new Random().nextInt(products.size());
        SelenideElement product = products.get(randomProductNr);
        //excluding current item from collection, to prevent choosing same thing
        products = products.exclude(attribute("data-id-product", product.attr("data-id-product")));

        //select item
        product.click();
        itemOne = getProductTitle();
        itemOnePrice = getProductPrice();
        $(byName("qty")).setValue(Double.toString(itemOneQuantity));
        SelenideElement addToCart = $("button.btn.btn-primary.add-to-cart");
        addToCart.click();

        //Check a price is correctly calculated
        $(byText("Product successfully added to your shopping cart")).waitUntil(visible, timeout);
        assertEquals(priceToDouble($("div.cart-content").$$("p").get(1).getText()),
                itemOneTotal = itemOnePrice*itemOneQuantity,
                "total price has been correctly calculated");

        //Go back to filtered list of items, choose one more item

        $(byText("Continue shopping")).click();

        openFilteredAccessoriesPage(filter);

        products.last().click();
        itemTwo = getProductTitle();
        itemTwoPrice = getProductPrice();
        addToCart.click();

        //Go to cart
        $(byText("Proceed to checkout")).click();
        //Check that a price is correctly calculated
        double cartSubtotal = priceToDouble($(byId("cart-subtotal-products")).$("span.value").text());
        assertEquals(cartSubtotal, orderTotal=itemOneTotal+itemTwoPrice, "order subtotal is correctly calculated");
    }

    @Test(dependsOnMethods = "shopping")
    public void checkout() {
        //Checkout
        $(byText("Proceed to checkout")).click();
        $(byId("checkout-personal-information-step")).waitUntil(visible, timeout);

        //fill out the form
        $(byName("company")).setValue(surname + " Enterprises");
        $(byName("address1")).setValue("Bat Street");
        $(byName("address2")).setValue("100");
        $(byName("city")).setValue("Gotham");
        $(byName("id_state")).selectOption(33);
        $(byName("postcode")).setValue("11939");
        $(byName("phone")).setValue("9111111");

        if (!$(byName("use_same_address")).is(checked)) {
            $(byName("use_same_address")).click();
        }

        $(byName("confirm-addresses")).click();

        //choose a shipping method
        if(!$(byId("delivery_option_2")).is(selected)){
            $(byId("delivery_option_2")).click();
        }

        $(byName("confirmDeliveryOption")).click();

        $("div.payment-options").waitUntil(visible , timeout);
        //choose "payment by Check", check the total price
        $(byText("Pay by Check")).click();
        $("span.custom-checkbox").click();
        $(byId("payment-confirmation")).click();

        //Confirm your order and check order details
        ElementsCollection orderLines = $("div.order-confirmation-table").$$("div.order-line.row");

        summaryLineChecker(itemOne, itemOnePrice, itemOneQuantity, itemOneTotal);
        summaryLineChecker(itemTwo, itemTwoPrice, 1, itemTwoPrice);

        assertTrue($(byId("order-details")).has(text("Payment method: Payments by check")));

    }

    @Test(dependsOnMethods = "checkout")
    public void logout() {
        // Logout, check you've been successfully logged out
        $(byText("Sign out")).click();
        assertTrue($(byText("Sign in")).is(visible), "sign out was succesful");
    }

    @AfterClass
    public void tearDown() {
        close();
    }

    //helper methods

    private void openFilteredAccessoriesPage(String filter){

        $(byId("category-6")).click();
        $(byId("category-description")).waitUntil(visible, timeout);


        $(withText("Price")).scrollIntoView(true);

        $(byText(filter)).click();
        $("li.filter-block").waitUntil(visible, timeout);
    }

    private String getProductTitle(){
        return $("h1.h1").text();
    }

    private double getProductPrice(){
        return priceToDouble($("div.current-price").$("span").getAttribute("content"));
    }

    private void summaryLineChecker(String item, double price, double qty, double total){
        SelenideElement orderLine = $$("div.order-line.row").find(text(item)).$("div.col-sm-6.col-xs-12.qty");
        assertEquals(priceToDouble(orderLine.$("div.col-xs-5.text-sm-right.text-xs-left").text()), price);
        assertEquals(priceToDouble(orderLine.$("div.col-xs-2").text()), qty);
        assertEquals(priceToDouble(orderLine.$("div.col-xs-5.text-xs-right.bold").text()), total);
    }

    private double priceToDouble(String val){
        return parseDouble(val.replaceAll("[^\\d.]+", ""));
    }
}
