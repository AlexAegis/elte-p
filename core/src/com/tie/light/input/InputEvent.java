package com.tie.light.input;

import java.util.Objects;

public class InputEvent {

	private Long time;
	private Boolean fired = false;

	public InputEvent(Long time) {
		this.time = time;
	}

	public Long elapsedTime() {
		return System.currentTimeMillis() - time;
	}

	public Boolean fire() {
		Boolean result = fired;
		fired = true;
		return !result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InputEvent inputEvent = (InputEvent) o;
		return Objects.equals(time, inputEvent.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(time);
	}

	@Override
	public String toString() {
		return "InputEvent{" +
				"time=" + time +
				'}';
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
