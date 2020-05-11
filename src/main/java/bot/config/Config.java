/**
 * Package bot.config for
 *
 * @author Maksim Tiunchik
 */
package bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

/**
 * Class Config - prepare array bean for
 *
 * @author Maksim Tiunchik (senebh@gmail.com)
 * @version 0.1
 * @since 11.05.2020
 */
@Configuration
public class Config {

    /**
     * create ArrayList with all registered users
     *
     * @return arraylist
     */
    @Bean
    @Scope("singleton")
    public ArrayList<String> arrayID () {
        return new ArrayList<String>();
    }
}
