package com.tie.light.input;

import java.util.Objects;
import java.util.function.Consumer;

public class InputEvent {

	private Long time;
	private Boolean fired = false;
	private Integer fireCount = 0;

	public InputEvent(Long time) {
		this.time = time;
	}

	public Long elapsedTime() {
		return System.currentTimeMillis() - time;
	}

	public Boolean fire() {
		return fire(false);
	}

	public Boolean fire(Boolean continuous) {
		Boolean result = fired;
		fired = true;
		return continuous || !result;
	}

	public Boolean fire(Long timer, Consumer<Integer> consumer) {
		if (elapsedTime() / timer >= fireCount) {
			consumer.accept(fireCount);
			fireCount++;
		}
		fired = true;
		return fired;
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
