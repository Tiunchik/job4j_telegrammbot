/**
 * Package bot.schedule for
 *
 * @author Maksim Tiunchik
 */
package bot.schedule;

import bot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class Mailing - send current time to user
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 11.05.2020
 */
@Component
@EnableScheduling
public class Mailing {

    /**
     * simple data pattern
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * link to bot
     */
    @Autowired
    private Bot bot;

    /**
     * array with chatID info
     */
    @Autowired
    private ArrayList<String> arrayID;

    /**
     * send info about current tine to all registered users
     */
    @Scheduled(fixedRate = 10000)
    public void sentTime() {
        String time = "The time is now " + dateFormat.format(new Date());
        arrayID.forEach(e -> bot.sendMsg(e, time));
    }
}
