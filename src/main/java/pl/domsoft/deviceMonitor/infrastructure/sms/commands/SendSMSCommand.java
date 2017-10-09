package pl.domsoft.deviceMonitor.infrastructure.sms.commands;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.Command;
import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.sms.handlers.interfaces.SendSMSCommandHandler;
import pl.smsapi.api.action.sms.SMSSend;

/**
 * Created by szymo on 11.09.2017.
 */
public class SendSMSCommand implements Command {

    private final SMSSend smsSend;

    public SendSMSCommand(SMSSend smsSend) {
        this.smsSend = smsSend;
    }

    public SMSSend getSmsSend() {
        return smsSend;
    }

    @Override
    public Class<? extends CommandHandler> getHandlerClass() {
        return SendSMSCommandHandler.class;
    }

    @Override
    public boolean isLoggable() {
        return true;
    }
}
