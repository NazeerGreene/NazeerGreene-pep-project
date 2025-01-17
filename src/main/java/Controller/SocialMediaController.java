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
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /*
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account Service
        app.post("register", this::signUpForNewUserHandler);
        app.post("login", this::loginForExistingUserHandler);

        // Message Service
        app.get("messages", this::getAllMessages);
        app.post("messages", this::createMessage);
        app.get("messages/{message_id}", this::getMessageById);
        app.patch("messages/{message_id}", this::updateMessage);
        app.delete("messages/{message_id}", this::deleteMessageById);

        app.get("accounts/{account_id}/messages", this::getMessagesForUserId);

        // Exceptions
        app.exception(JsonProcessingException.class, this::JsonProcessingExceptionHandler);

        return app;
    }

    // ACCOUNT SERVICE HANDLERS
    private void signUpForNewUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account newAccount = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerNewUser(newAccount);

        if (addedAccount == null) {
            context.status(HttpStatusCode.CLIENT_ERROR.code());
        } else {
            context.json(addedAccount);
        }
    }

    private void loginForExistingUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account unverifiedAccount = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyExistingUser(unverifiedAccount);

        if (verifiedAccount == null) {
            context.status(HttpStatusCode.FAILURE_LOGIN.code());
        } else {
            context.json(verifiedAccount);
        }
    }

    // MESSAGE SERVICE HANDLERS
    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessagesForUserId(Context context) {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUserId(account_id);
        context.json(messages);
    }

    private void getMessageById(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message == null) {
            context.status(HttpStatusCode.SUCCESS.code());
        } else {
            context.json(message);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message receivedMessage = mapper.readValue(context.body(), Message.class);
        int posted_by = receivedMessage.getPosted_by();

        Account user = accountService.getAccountUsernameByUserId(posted_by);

        if (user == null) {
            context.status(HttpStatusCode.CLIENT_ERROR.code());
            return;
        }

        Message createdMessage = messageService.createMessage(receivedMessage);

        if (createdMessage == null) {
            context.status(HttpStatusCode.CLIENT_ERROR.code());
        } else {
            context.json(createdMessage);
        }
    }

    private void deleteMessageById(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(message_id);
        if (message == null) {
            context.status(HttpStatusCode.SUCCESS.code());
        } else {
            context.json(message);
        }
    }

    private void updateMessage(Context context) throws JsonProcessingException {
        // below captures the message_text and time_posted_epoch neatly
        ObjectMapper mapper = new ObjectMapper();
        Message receivedMessage = mapper.readValue(context.body(), Message.class);

        int message_id = Integer.parseInt(context.pathParam("message_id"));

        receivedMessage.setMessage_id(message_id);

        Message updatedMessage = messageService.updateMessage(receivedMessage);

        if (updatedMessage == null) {
            context.status(HttpStatusCode.CLIENT_ERROR.code());
        } else {
            context.json(updatedMessage);
        }
    }

    // EXCEPTION HANDLERS
    private void JsonProcessingExceptionHandler(JsonProcessingException exception, Context context) {
        String exceptionOutput = String.format("Error while parsing input: %s\n\nMessage: %s\n", context.body(), exception.getOriginalMessage());
        
        context.status(HttpStatusCode.CLIENT_ERROR.code()).result(exceptionOutput);
    }

}