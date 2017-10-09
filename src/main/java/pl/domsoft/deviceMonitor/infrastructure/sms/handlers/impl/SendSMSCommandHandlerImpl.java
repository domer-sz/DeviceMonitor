package pl.domsoft.deviceMonitor.infrastructure.sms.handlers.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.AppException;
import pl.domsoft.deviceMonitor.infrastructure.base.exceptions.repositories.AppExceptionRepository;
import pl.domsoft.deviceMonitor.infrastructure.shared.ExceptionPersistable;
import pl.domsoft.deviceMonitor.infrastructure.sms.commands.SendSMSCommand;
import pl.domsoft.deviceMonitor.infrastructure.sms.handlers.interfaces.SendSMSCommandHandler;
import pl.smsapi.api.response.SendStatusResponse;
import pl.smsapi.exception.ActionException;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.HostException;
import pl.smsapi.exception.SmsapiException;

/**
 * Created by szymo on 11.09.2017.
 */
@Component
class SendSMSCommandHandlerImpl implements SendSMSCommandHandler, ExceptionPersistable{

    @Value("${sms.feedback.errors.on.client.email}")
    boolean sendErrorFeedback;

    private final AppExceptionRepository appExceptionRepository;

    SendSMSCommandHandlerImpl(AppExceptionRepository appExceptionRepository) {
        this.appExceptionRepository = appExceptionRepository;
    }

    @Override
    public SendStatusResponse handle(SendSMSCommand command) throws AppException {
        try {
            return command.getSmsSend().execute();
        } catch( ActionException e) {

            //TODO send error feedback
            /**
             * Błędy związane z akcją (z wyłączeniem błędów 101,102,103,105,110,1000,1001 i 8,666,999,201)
             * http://www.smsapi.pl/sms-api/kody-bledow
             */
            System.out.println(e.getMessage());
            throw new AppException("Wystąpił błąd podczas wysyłania sms", e.getMessage(), getExcpRep());
        } catch( ClientException e) {
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
            throw new AppException("Wystąpił błąd podczas wysyłania sms", e.getMessage(), getExcpRep());
        } catch( HostException e) {
            /** błąd po stronie servera lub problem z parsowaniem danych
             *
             * 8 - Błąd w odwołaniu
             * 666 - Wewnętrzny błąd systemu
             * 999 - Wewnętrzny błąd systemu
             * 201 - Wewnętrzny błąd systemu
             **/
            System.out.println(e.getMessage());
            throw new AppException("Wystąpił błąd podczas wysyłania sms", e.getMessage(), getExcpRep());
        } catch( SmsapiException e ) {
            System.out.println(e.getMessage());
            throw new AppException("Wystąpił błąd podczas wysyłania sms", e.getMessage(), getExcpRep());
        }

    }

    @Override
    public AppExceptionRepository getExcpRep() {
        return appExceptionRepository;
    }
}
