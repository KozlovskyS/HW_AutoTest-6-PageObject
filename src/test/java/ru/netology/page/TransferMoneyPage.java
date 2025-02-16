package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {
    private final SelenideElement headerTransfer = $("h1").shouldHave(Condition.text("Пополнение карты"));
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement chooseCardToDepositButton = $("[data-test-id='action-transfer']");
    private final SelenideElement cardNumberFromField = $("[data-test-id='from'] input");

    int amount = 200;

    public TransferMoneyPage() {
        headerTransfer.shouldBe(Condition.visible);
    }

    public DashBoardPage moneyTransfer(int amount, String cardNumberFrom) { //перевод с карты id=from суммы amount
        amountField.setValue(String.valueOf(amount));
        cardNumberFromField.setValue(cardNumberFrom);
        chooseCardToDepositButton.click();
        return new DashBoardPage();
    }
}
