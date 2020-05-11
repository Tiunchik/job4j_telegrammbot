/**
 * Package bot.bot for
 *
 * @author Maksim Tiunchik
 */
package bot.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Bot - telegramm bot main programm
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 11.05.2020
 */
@Component
public class Bot extends TelegramLongPollingBot {

    /**
     * inner logger
     */
    private static final Logger LOG = LogManager.getLogger(Bot.class.getName());

    /**
     * Array with chatId for time-date sending
     */
    @Autowired
    private ArrayList<String> arrayID;

    /**
     * individual token for bot registration
     */
    @Value("${bot.token}")
    private String token;

    /**
     * bot name
     */
    @Value("${bot.username}")
    private String username;

    /**
     * return bot name
     *
     * @return username
     */
    @Override
    public String getBotUsername() {
        return username;
    }

    /**
     * return bot tolen
     *
     * @return token
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * executed when user send message, with command /intive add chat between user and bot to list of mailing
     *
     * @param update information about message - chatid, username, text
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        if (message.equals("/start")) {
            sendStartMsg(chatId);
            return;
        }
        if (message.equals("/invite")) {
            arrayID.add(chatId);
            return;
        }
        if (message.equals("/exit")) {
            arrayID.remove(chatId);
            return;
        }
        sendMsg(chatId, message);

    }

    /**
     * send mirrow message to user
     *
     * @param chatId chatId
     * @param s      mirrowed text
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Exception: " + e.toString(), e);
        }
    }

    /**
     * send to user buttons /invite and /exit for adding user to mailing
     *
     * @param chatId chatId
     */
    public synchronized void sendStartMsg(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Greetings! Please press one of two buttons and chose our next steps");
        setButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Exception: " + e.toString(), e);
        }
    }

    /**
     * prepare buttons for sendStartMsg
     *
     * @param sendMessage message to add buttons
     */
    private synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/invite"));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("/exit"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
