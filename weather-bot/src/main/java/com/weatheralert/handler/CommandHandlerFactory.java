package com.weatheralert.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import com.weatheralert.commands.Command;

/**
 * Factory that find needed bean by {@link HandlerDefenitionStore} and invoke
 * their handler method
 */
@Component
public class CommandHandlerFactory {

	@Autowired
	private Map<String, CommandHandler> handlers;

	public <T> BotApiMethod<?> handleCommand(T argumment, String commandFromData, boolean isFromTextInput) {
		Command command;
		if (isFromTextInput)
			command = parseCommandFromText(commandFromData);
		else
			command = parseCommand(commandFromData);

		return handleCommand(argumment, command);
	}

	public <T> BotApiMethod<?> handleCommand(T argumment, Command command) {
		CommandHandler handler = getHandler(command);
		return handler.handleCommand(argumment, command);
	}

	public CommandHandler getHandler(Command command) {
		String beanName = HandlerDefenitionStore.getBeanName(command);
		return handlers.get(beanName);
	}

	public Command parseCommand(String command) {
		for (Command c : Command.values()) {
			if (c.getName().equals(command))
				return c;
		}
		return Command.ERROR;
	}

	public Command parseCommandFromText(String text) {
		Command command = null;
		for (Command c : Command.values()) {
			if (c.isLineCommand() && text.contains(c.getName()))
				if (command == null || command.getName().length() < c.getName().length()) {
					command = c;
				}
		}
		if (command == null)
			return Command.ERROR;
		return command;
	}
}
