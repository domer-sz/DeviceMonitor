package pl.domsoft.deviceMonitor.appconfig.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.smsapi.Client;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.exception.ClientException;

/**
 * Created by szymo on 11.09.2017.
 */
@Configuration
public class SmsApiConfig {

    @Value("${sms.client.email}")
    String clientEmail;
    @Value("${sms.client.password.md5}")
    String clientPassword;
    @Value("${sms.message.sender}")
    String sender;
    @Value("${sms.feedback.errors.on.client.email}")
    boolean sendErrorFeedback;

    @Autowired
    Client client;

    @Bean
    @Scope("singleton")
    public Client smsApiClient(){
        Client client = null;
        try {
            client = new Client(clientEmail);
            client.setPasswordHash(clientPassword);
            System.out.println(client);
        } catch (ClientException e) {
            //TODO wysylka feedbacku
            /**
             * 101 Niepoprawne lub brak danych autoryzacji.
             * 102 Nieprawidłowy login lub hasło
             * 103 Brak punków dla tego użytkownika
             * 105 Błędny adres IP
             * 110 Usługa nie jest dostępna na danym koncie
             * 1000 Akcja dostępna tylko dla użytkownika głównego
             * 1001 Nieprawidłowa akcja
             */
            System.out.println(e.getMessage());
        }

        return client;
    }

    @Bean
    @Scope("prototype")
    public SMSSend smsToSend(){
        SmsFactory smsApi = new SmsFactory(client);
        SMSSend action = smsApi.actionSend()
                .setSender(getSender()); //Pole nadawcy lub typ wiadomość 'ECO', '2Way'
        return action;
    }

    private String getSender() {
        if(this.sender == null || this.sender.isEmpty()){
            return "Informacja";
        }else{
            return this.sender;
        }
    }
}
