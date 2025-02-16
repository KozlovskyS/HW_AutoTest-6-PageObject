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
        String firstCardId = DataHelper.getFirstCardInfo().getId();
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = DataHelper.generationValidAmount(secondCardBalance);
        int firstCardBalanceExpected = firstCardBalance + summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance - summaToDeposit;

        var transferMoneyPage = dashBoardPage.chooseCardForTransfer(firstCardId);
        transferMoneyPage.moneyTransfer(summaToDeposit, secondCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));
    }


}