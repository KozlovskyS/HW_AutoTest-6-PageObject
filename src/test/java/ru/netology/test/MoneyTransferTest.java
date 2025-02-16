package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferMoneyPage;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testTransferToFirstFromSecondCard() {     // перевод на первую со второй
        String secondCardNumber = DataHelper.getSecondCardInfo().getNumber();
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = 200;

        int firstCardBalanceExpected = firstCardBalance + summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance - summaToDeposit;

        dashBoardPage.chooseCardForTransfer(0);
        var transferMoneyPage = new TransferMoneyPage();
        transferMoneyPage.moneyTransfer(summaToDeposit, secondCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));


//        $("[data-test-id='login'] input").setValue(info.getLogin());
//        $("[data-test-id='password'] input").setValue(info.getPassword());
//        $("[data-test-id ='action-login']").click();

//        $("[data-test-id='code'] input").setValue(verificationCode.getCode());
//        $("[data-test-id='action-verify']").click();
//        $("[data-test-id='dashboard']").should(Condition.visible);
//        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button").click();
//        $$(".list__item")
//                .findBy(Condition.attribute("data-test-id", "92df3f1c-a033-48e6-8390-206f6b1f56c0"))
//                .$("[data-test-id='action-deposit'] button").click();
//        $$("[data-test-id='action-deposit']").get(0).click();
    }

    @Test
    void testTransferToFirstFromSecondCardOverBalance() {     // перевод на первую со второй сверх доступного баланса - получаем отрицательный баланс на второй
        String secondCardNumber = DataHelper.getSecondCardInfo().getNumber();
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = 20_000;

        int firstCardBalanceExpected = firstCardBalance + summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance - summaToDeposit;

        dashBoardPage.chooseCardForTransfer(0);
        var transferMoneyPage = new TransferMoneyPage();
        transferMoneyPage.moneyTransfer(summaToDeposit, secondCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));
    }

    @Test
    void testTransferToSecondFromFirstCard() {  // перевод на вторую с первой
//        String secondCardNumber = DataHelper.getSecondCardInfo().getNumber();
        String firstCardNumber = DataHelper.getFirstCardInfo().getNumber();

        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = 200;
        int firstCardBalanceExpected = firstCardBalance - summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance + summaToDeposit;

        dashBoardPage.chooseCardForTransfer(1);
        var transferMoneyPage = new TransferMoneyPage();
        transferMoneyPage.moneyTransfer(summaToDeposit, firstCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));

    }


}