package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashBoardPage {
    private final SelenideElement header = $("[data-test-id='dashboard']");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload'] button");
    private final SelenideElement firstCardDebitButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private final SelenideElement secondCardDebitButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashBoardPage() {
        header.shouldBe(Condition.visible);
    }

    public int getCardBalance(int index) {  //получить баланс по карте с id
        val text = cards.get(index).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferMoneyPage chooseCardForTransfer(String cardId) { //выбор карты для пополнения
        cards.findBy(Condition.attribute("data-test-id", cardId)).$("[data-test-id='action-deposit'] button").click();
        return new TransferMoneyPage();
    }


}
