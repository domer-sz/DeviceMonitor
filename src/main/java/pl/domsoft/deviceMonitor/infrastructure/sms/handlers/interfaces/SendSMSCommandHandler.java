package pl.domsoft.deviceMonitor.infrastructure.sms.handlers.interfaces;

import pl.domsoft.deviceMonitor.infrastructure.base.interfaces.CommandHandler;
import pl.domsoft.deviceMonitor.infrastructure.sms.commands.SendSMSCommand;
import pl.smsapi.api.response.SendStatusResponse;

/**
 * Created by szymo on 11.09.2017.
 */
public interface SendSMSCommandHandler extends CommandHandler<SendSMSCommand, SendStatusResponse> {
}
