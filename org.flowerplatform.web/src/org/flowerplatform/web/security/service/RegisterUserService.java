/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico, <http://www.crispico.com/>.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, at <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *   Crispico - Initial API and implementation
 *
 * license-end
 */
package org.flowerplatform.web.security.service;

import java.util.List;

import org.flowerplatform.web.security.dto.OrganizationAdminUIDto;
import org.flowerplatform.web.security.dto.UserAdminUIDto;
import org.flowerplatform.web.security.mail.SendMailService;
import org.flowerplatform.web.security.sandbox.SecurityEntityAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.flowerplatform.web.WebPlugin;
import org.flowerplatform.web.database.DatabaseOperation;
import org.flowerplatform.web.database.DatabaseOperationWrapper;
import org.flowerplatform.web.entity.User;

/**
 * @author Mariana
 */
public class RegisterUserService extends UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterUserService.class);
	
	/**
	 * Check if there is already an existing user with the same login, or if the
	 * email address is valid before registering this new user.
	 */
	public String register(final UserAdminUIDto dto, final String organizationPrefix) {
		logger.debug("Register new user with login = {}", dto.getLogin());
		
		dto.setIsActivated(false);
		final String message = mergeAdminUIDto(dto);
		
		new DatabaseOperationWrapper(new DatabaseOperation() {
			
			@Override
			public void run() {
				if (message == null) {
					User user = wrapper.findByField(User.class, "login", dto.getLogin()).get(0);
					sendActivationCodeForUser(user, organizationPrefix);
				}
				
			}
		});
		
		logger.debug("Resistration successful");
		return message;
	}
	
	/**
	 * Check if the user exists and is activated; otherwise, send the appropriate message. 
	 * If user is not yet activated, resend the activation code. Returns a message that will
	 * be displayed to the client.
	 */
	public String resendActivationCode(final String login, final String organizationFilter) {
		logger.debug("Resend activation code for user with login = {}", login);
		DatabaseOperationWrapper wrapper = new DatabaseOperationWrapper(new DatabaseOperation() {
			
			@Override
			public void run() {
				List<User> existingUsers = wrapper.findByField(User.class, "login", login);
				if (existingUsers.size() == 1) {
					if (!existingUsers.get(0).isActivated()) {
						sendActivationCodeForUser(existingUsers.get(0), organizationFilter);
						wrapper.setOperationResult("The activation code was sent to your mail box.");
					} else {
						wrapper.setOperationResult("This user is already activated!");
					}
				} else {
					wrapper.setOperationResult("This user does not exist!");
				}
			}
		});
		return (String) wrapper.getOperationResult();
	}
	
	private final String RECOVER_PASSWORD_SUBJECT = "mail.template.recover.password.subject";
	private final String RECOVER_PASSWORD_BODY = "mail.template.recover.password.body";
	private final String RECOVER_USERNAME_SUBJECT = "mail.template.recover.username.subject";
	private final String RECOVER_USERNAME_BODY = "mail.template.recover.username.body";
	
	/**
	 * Resets the password for the user with the given <code>login</code> and sends it
	 * by email. Returns <code>true</code> if the password was reset and sent, and 
	 * <code>false</code> if the user does not exist.
	 */
	public boolean forgotPassword(final String login) {
		logger.debug("Forgot password request for user with login = {}", login);
		DatabaseOperationWrapper wrapper = new DatabaseOperationWrapper(new DatabaseOperation() {
			
			@Override
			public void run() {
				List<User> existingUsers = wrapper.findByField(User.class, "login", login);
				if (existingUsers.size() == 0) {
					wrapper.setOperationResult(false);
					return;
				}
				
				User user = existingUsers.get(0);
				
				if (user.getLogin().startsWith(SecurityEntityAdaptor.ANONYMOUS)) {
					wrapper.setOperationResult(false); // do not allow reset for anonymous
					return;
				}
				
				String newPassword = generateRandomString();
				user.setHashedPassword(Util.encrypt(newPassword));
				wrapper.merge(user);
				
				String subject = WebPlugin.getInstance().getMessage(RECOVER_PASSWORD_SUBJECT);
				String content = WebPlugin.getInstance().getMessage(RECOVER_PASSWORD_BODY,
						new Object[] { 
							user.getLogin(),
							user.getName(),
							user.getEmail(),
							SendMailService.getInstance().getServerUrl(),
							newPassword 
						});
				
				SendMailService.getInstance().send(user.getEmail(), subject, content);
				wrapper.setOperationResult(true);
			}
		});
		return (boolean) wrapper.getOperationResult();
	}
	
	/**
	 * Checks if there is a registered user with the given <code>email</code>.
	 * Resets the password and sends the username and new password to the user.
	 * Returns <code>false</code> if the user does not exist, <code>true</code>
	 * otherwise.
	 */
	public boolean forgotUsername(final String email) {
		logger.debug("Forgot username request for user with email = {}", email);
		DatabaseOperationWrapper wrapper = new DatabaseOperationWrapper(new DatabaseOperation() {
			
			@Override
			public void run() {
				List<User> existingUsers = wrapper.findByField(User.class, "email", email);
				if (existingUsers.size() == 0) {
					wrapper.setOperationResult(false);
					return;
				}
				
				User user = existingUsers.get(0);
				
				if (user.getLogin().startsWith(SecurityEntityAdaptor.ANONYMOUS)) {
					wrapper.setOperationResult(false); // do not allow reset for anonymous
					return;
				}
				
				String newPassword = generateRandomString();
				user.setHashedPassword(Util.encrypt(newPassword));
				wrapper.merge(user);
				
				String subject = WebPlugin.getInstance().getMessage(RECOVER_USERNAME_SUBJECT);
				String content = WebPlugin.getInstance().getMessage(RECOVER_USERNAME_BODY,
						new Object[] { 
							user.getLogin(),
							user.getName(),
							user.getEmail(),
							SendMailService.getInstance().getServerUrl(),
							newPassword 
						});
				
				SendMailService.getInstance().send(user.getEmail(), subject, content);
				
				wrapper.setOperationResult(true);
			}
		});
		return (boolean) wrapper.getOperationResult();
	}
	
	public OrganizationAdminUIDto getOrganizationFilter(String organizationName) {
		return OrganizationService.getInstance().findByNameAsAdminUIDto(organizationName);
	}
}