package rf.lopatuxin.tgbot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import rf.lopatuxin.tgbot.service.message.SimpleMessageHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleMessageHandlerTest {

    private static final long CHAT_ID = 123;

    @InjectMocks
    private SimpleMessageHandler simpleMessageHandler;

    @Test
    void testHandleUpdate_StartCommand() {
        Update update = createUpdate("/start");

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals("Добро пожаловать! Нажмите кнопку 'О компании', чтобы узнать больше.", response.getText());
        assertEquals(123L, Long.parseLong(response.getChatId()));
        assertEquals(1, ((ReplyKeyboardMarkup) response.getReplyMarkup()).getKeyboard().size());
    }

    @Test
    void testHandleUpdate_AboutCompanyCommand() {
        Update update = createUpdate("О компании");

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals("Мы компания, которая занимается... (добавьте ваше описание здесь).", response.getText());
        assertEquals(123L, Long.parseLong(response.getChatId()));
    }

    @Test
    void testHandleUpdate_UnknownCommand() {
        Update update = createUpdate("Unknown command");

        SendMessage response = simpleMessageHandler.handleUpdate(update);

        assertEquals("Неизвестная команда. Пожалуйста, используйте /start.", response.getText());
        assertEquals(CHAT_ID, Long.parseLong(response.getChatId()));
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