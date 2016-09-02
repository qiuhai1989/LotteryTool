package com.yuncai.modules.timer.termTask.event.impl;

import java.util.Map;

import com.yuncai.modules.timer.termTask.event.Event;

public abstract class AbstractEvent implements Event {
	private Event nextEvent;

	@SuppressWarnings("unchecked")
	abstract public Map execute(Map request);

	public Event getNextEvent() {
		return nextEvent;
	}

	public void setNextEvent(Event nextEvent) {
		this.nextEvent = nextEvent;
	}


}
