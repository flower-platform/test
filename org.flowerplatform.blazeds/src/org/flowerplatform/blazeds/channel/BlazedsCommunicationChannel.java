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
package org.flowerplatform.blazeds.channel;

import java.util.Collections;
import java.util.Set;

import org.flowerplatform.blazeds.heartbeat.HeartbeatStatefulService;
import org.flowerplatform.communication.CommunicationPlugin;
import org.flowerplatform.communication.IPrincipal;
import org.flowerplatform.communication.channel.CommunicationChannel;
import org.flowerplatform.communication.command.AbstractClientCommand;

import flex.messaging.MessageClient;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.services.MessageService;
import flex.messaging.util.UUIDUtils;

public class BlazedsCommunicationChannel extends CommunicationChannel {

	protected CommunicationChannelMessagingAdapter communicationChannelMessagingAdapter;
	
	protected MessageClient messageClient;
	
	/**
	 * Lock intended to be acquired by the channel state observer (heartbeat manager) for disposing a channel or 
	 * by the channel manager to update channel's flag when an object has been just received or is about to be sent.
	 * Note: the lock isn't kept while processing the received object.
	 * Note: the lock is kept while disposing a channel. 
	 */
	public final Object lock = new Object();
	
	public BlazedsCommunicationChannel(
			CommunicationChannelMessagingAdapter communicationChannelMessagingAdapter,
			MessageClient messageClient) {
		super();
		this.communicationChannelMessagingAdapter = communicationChannelMessagingAdapter;
		this.messageClient = messageClient;
//		FlexClient flexClient = communicationChannelMessagingAdapter.getDestination().getService().getMessageBroker().getFlexClientManager().getFlexClient((String) flexClientId);
//		messageClient = flexClient.getMessageClients().get(0);  // We have the convention that a Flex Client will only have just one subscriber (MessageClient).
	}

	@Override
	public void handleReceivedObjectWillStart(Object object) {
		super.handleReceivedObjectWillStart(object);
		HeartbeatStatefulService heartbeatStatefulService = (HeartbeatStatefulService) CommunicationPlugin.getInstance()
        		.getServiceRegistry().getService(HeartbeatStatefulService.SERVICE_ID);
		if (heartbeatStatefulService != null) {
			heartbeatStatefulService.notifyObjectReceived(this, object);
		}
	}

	@Override
	public void sendCommandWithPush(AbstractClientCommand command) {
		if (command == null)
			return;
		command = new ServerSnapshotClientCommand(command);
		
		AsyncMessage asyncMessage = new AsyncMessage();
		asyncMessage.setDestination(messageClient.getDestinationId());
        asyncMessage.setMessageId(UUIDUtils.createUUID());
        asyncMessage.setBody(command);

        MessageService messageService = (MessageService) messageClient.getDestination().getService();
        Set<Object> messageClientIds = Collections.singleton(messageClient.getClientId());
        messageService.pushMessageToClients(messageClientIds, asyncMessage, false);
        
        HeartbeatStatefulService heartbeatStatefulService = (HeartbeatStatefulService) CommunicationPlugin.getInstance()
        		.getServiceRegistry().getService(HeartbeatStatefulService.SERVICE_ID);
        if (heartbeatStatefulService != null) {
        	heartbeatStatefulService.notifyObjectSent(this, command);
        }
	}

	@Override
	public void appendCommandToCurrentHttpResponse(AbstractClientCommand command) {
		super.appendCommandToCurrentHttpResponse(new ServerSnapshotClientCommand(command));
	}

	/**
	 * It's the Flex Client ID. Don't mistake this with message client id.
	 */
	@Override
	public Object getId() {
		return messageClient == null ? null : messageClient.getFlexClient().getId();
	}

	@Override
	public IPrincipal getPrincipal() {
		// Inspired from FlexContext.getUserPrincipal()
		if (messageClient == null) {
			return null;
		}
		
		if (messageClient.getDestination().getService().getMessageBroker().getLoginManager().isPerClientAuthentication()) {
			return (IPrincipal) messageClient.getFlexClient().getUserPrincipal();
		} else {
			return (IPrincipal) messageClient.getFlexSession().getUserPrincipal();
		}
	}

	/**
	 * @author Mariana
	 */
	@Override
	public void disconnect() {
		messageClient.getFlexClient().invalidate();
	}

	@Override
	public boolean isDisposed() {
		return messageClient == null;
	}

	@Override
	public void dispose() {
		messageClient = null;
	}

}