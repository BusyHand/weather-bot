package com.weatheralert.bpp;

import java.lang.reflect.Method;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.exception.CommandConflictException;
import com.weatheralert.handler.HandlerDefenitionStore;
import com.weatheralert.model.HandlerDefenition;

/**
 * BPP for map {@link HandlerDefenition} to {@link CommandHandlerMethod} in
 * {@link HandlerDefenitionStore}
 */
@Component
public class HandlerInjectorBPP implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			CommandHandlerMethod commandAnnotation = method.getAnnotation(CommandHandlerMethod.class);
			if (commandAnnotation != null) {
				Command command = commandAnnotation.value();
				if (HandlerDefenitionStore.isNotPresent(command)) {
					HandlerDefenitionStore.put(command, beanName, method);
				} else {
					throw new CommandConflictException("Command '" + command + "' not obvious for injection");
				}
			}
		}
		return bean;
	}
}
