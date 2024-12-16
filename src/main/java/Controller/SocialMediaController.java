package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    private static final AccountService accountService;
    private static final MessageService messageService;

    static {
        accountService = new AccountService();
        messageService = new MessageService();
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
        app.get("messages", this::getAllMessages);
        app.post("messages", this::createMessage);
        app.get("messages/{message_id}", this::getMessageById);
        app.patch("messages/{message_id}", this::updateMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);

        app.get("accounts/{account_id}/messages", this::getMessagesForUserId);

        return app;
    }

    private void signUpForNewUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account newAccount = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerNewUser(newAccount);

        if (addedAccount == null) {
            context.status(HttpStatusCode.CLIENT_ERROR.code());
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

    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();

        context.json(messages); // implicit 200 OK
    }

    private void getMessagesForUserId(Context context) throws JsonProcessingException {
        int user_id = Integer.parseInt(context.pathParam("message_id"));
        List<Message> messages = messageService.getMessagesByUserId(user_id);

        context.json(messages); // implicit 200 OK
    }

    private void getMessageById(Context context) throws JsonProcessingException {
        int user_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(user_id);

        Object emptyJson = new Object();

        context.json(message == null ? emptyJson : message); // implicit 200 OK
    }

    private void createMessage(Context context) {
        // Json Message(id, content), 200
        // verify poster_id
        // fail: 400
    }

    private void deleteMessageById(Context context) {
        // Json Message(id, content), 200
        // message not found: Json {}, 200
    }

    private void updateMessageById(Context context) {
        // Json Message(id, content), 200
        // fail: 400
    }

}