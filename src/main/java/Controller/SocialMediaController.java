package Controller;

import Model.Account;
import Service.AccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    private static final AccountService accountService;

    private static final int FAILURE_REGISTRATION = 400;
    private static final int FAILURE_LOGIN = 401;
    private static final int SUCCESS_REGISTRATION = 200;

    static {
        accountService = new AccountService();
    }

    /*
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account Handlers
        app.post("register", this::signUpForNewUserHandler);
        app.post("login", this::loginForExistingUserHandler);

        // Message Handlers

        return app;
    }


    private void signUpForNewUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account newAccount = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerNewUser(newAccount);

        if (addedAccount == null) {
            context.status(HttpStatusCode.FAILURE_REGISTRATION.code());
        } else {
            context.json(addedAccount).status(HttpStatusCode.SUCCESS.code());
        }
    }

    private void loginForExistingUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account unverifiedAccount = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyExistingUser(unverifiedAccount);

        if (verifiedAccount == null) {
            context.status(HttpStatusCode.FAILURE_LOGIN.code());
        } else {
            context.json(verifiedAccount).status(HttpStatusCode.SUCCESS.code());
        }
    }

}