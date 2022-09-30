package com.weatheralert.handler;

import java.lang.reflect.Method;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.exception.IllegalReturnValueException;

/**
 * Call the method that is marked with {@link CommandHandlerMethod} and map it
 * to a command
 */
public interface CommandHandler {

	default public <T> BotApiMethod<?> handleCommand(T argumment, Command command) {
		Method method = HandlerDefenitionStore.getMethod(command);
		return invokeMethod(method, argumment);
	}

	private <T> BotApiMethod<?> invokeMethod(Method method, T argumment) {
		try {
			method.setAccessible(true);
			Object returnValue = method.invoke(this, argumment);
			if (returnValue instanceof BotApiMethod<?>) {
				return (BotApiMethod<?>) returnValue;
			} else {
				throw new IllegalReturnValueException("Return type of method: " + method.getName()
						+ " must be cast to (BotApiMethod)");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
