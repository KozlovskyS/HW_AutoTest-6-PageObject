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

        int summaToDeposit = DataHelper.generationValidAmount(secondCardBalance);
        int firstCardBalanceExpected = firstCardBalance + summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance - summaToDeposit;

        var transferMoneyPage = dashBoardPage.chooseCardForTransfer(0);
        transferMoneyPage.moneyTransfer(summaToDeposit, secondCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));
    }

    @Test
    void testTransferToSecondFromFirstCard() {  // перевод на вторую с первой
        String firstCardNumber = DataHelper.getFirstCardInfo().getNumber();
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = DataHelper.generationValidAmount(firstCardBalance);
        int firstCardBalanceExpected = firstCardBalance - summaToDeposit;
        int secondCardBalanceExpected = secondCardBalance + summaToDeposit;

        var transferMoneyPage = dashBoardPage.chooseCardForTransfer(1);
        transferMoneyPage.moneyTransfer(summaToDeposit, firstCardNumber);

        Assertions.assertEquals(firstCardBalanceExpected, dashBoardPage.getCardBalance(0));
        Assertions.assertEquals(secondCardBalanceExpected, dashBoardPage.getCardBalance(1));
    }

    @Test
    void testTransferToFirstFromSecondCardOverBalance() {     // перевод на первую со второй сверх доступного баланса
        String secondCardNumber = DataHelper.getSecondCardInfo().getNumber();
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashBoardPage.getCardBalance(0);
        var secondCardBalance = dashBoardPage.getCardBalance(1);

        int summaToDeposit = DataHelper.generationInvalidAmount(secondCardBalance);

        var transferMoneyPage = dashBoardPage.chooseCardForTransfer(0);
        transferMoneyPage.moneyTransfer(summaToDeposit, secondCardNumber);

        String errorMessage = "Ошибка! Сумма превышает баланс"; //таким должен быть текст сообщения
        transferMoneyPage.findErrorMessage(errorMessage);
    }
}