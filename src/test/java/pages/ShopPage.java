package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ShopPage {

    public static final SelenideElement productsForm = $("div.products");


    public static ShopItem getShopItemByIndex(int index) {
        return new ShopItem(index);

    }

    public static void openShopItemByIndex(int index) {
        getShopItemByIndex(index).itemThumbnail.click();
    }


    public static class ShopItem {

        private final String xptShopItem = "//article[%d]";
        private SelenideElement shopItem;
        public SelenideElement itemThumbnail;

        ShopItem(int index) {
            this.shopItem = $x(String.format(xptShopItem, index));
            this.itemThumbnail = shopItem.$("a.thumbnail");
        }
    }

}
