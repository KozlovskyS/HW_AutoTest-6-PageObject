package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testOpenDashBoardSuccessful() {

        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);

//        $("[data-test-id='login'] input").setValue(info.getLogin());
//        $("[data-test-id='password'] input").setValue(info.getPassword());
//        $("[data-test-id ='action-login']").click();

//        $("[data-test-id='code'] input").setValue(verificationCode.getCode());
//        $("[data-test-id='action-verify']").click();
//        $("[data-test-id='dashboard']").should(Condition.visible);
    }
}