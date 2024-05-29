package rf.lopatuxin.tgbot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import rf.lopatuxin.tgbot.service.command.CommandHandler;
import rf.lopatuxin.tgbot.service.command.CommandHandlerRegistry;
import rf.lopatuxin.tgbot.service.message.SimpleMessageHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleMessageHandlerTest {

    private static final long CHAT_ID = 123;
    @Mock
    private CommandHandlerRegistry commandHandlerRegistry;
    @Mock
    private CommandHandler commandHandler;
    @InjectMocks
    private SimpleMessageHandler simpleMessageHandler;

    @Test
    void testHandleUpdate_StartCommand() {
        Update update = createUpdate("/start");
        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setText("Добро пожаловать! Нажмите кнопку 'О компании', чтобы узнать больше.");
        expectedMessage.setChatId(String.valueOf(CHAT_ID));
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        expectedMessage.setReplyMarkup(keyboardMarkup);

        when(commandHandlerRegistry.getHandler("/start")).thenReturn(commandHandler);
        when(commandHandler.handle(CHAT_ID)).thenReturn(expectedMessage);

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals(expectedMessage.getText(), response.getText());
        assertEquals(String.valueOf(CHAT_ID), response.getChatId());
    }

    @Test
    void testHandleUpdate_AboutCompanyCommand() {
        Update update = createUpdate("О компании");
        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setText("Мы компания, которая занимается... (добавьте ваше описание здесь).");
        expectedMessage.setChatId(String.valueOf(CHAT_ID));

        when(commandHandlerRegistry.getHandler("О компании")).thenReturn(commandHandler);
        when(commandHandler.handle(CHAT_ID)).thenReturn(expectedMessage);

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals(expectedMessage.getText(), response.getText());
        assertEquals(String.valueOf(CHAT_ID), response.getChatId());
    }

    @Test
    void testHandleUpdate_NoTextMessage() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(false);

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals("Обновление не содержит текстового сообщения.", response.getText());
    }

    private Update createUpdate(String text) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn(text);
        when(message.getChatId()).thenReturn(CHAT_ID);

        return update;
    }
}