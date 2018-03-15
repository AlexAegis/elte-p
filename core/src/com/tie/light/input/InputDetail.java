package com.tie.light.input;


import com.badlogic.gdx.controllers.Controller;

import java.util.Objects;

public class InputDetail {

	private Long time;

	public InputDetail(Long time) {
		this.time = time;
	}

	public Long elapsedTime() {
		return System.currentTimeMillis() - time;
	}

	public Boolean isInitial() {
		return elapsedTime() <= 4;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InputDetail inputDetail = (InputDetail) o;
		return Objects.equals(time, inputDetail.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(time);
	}

	@Override
	public String toString() {
		return "InputDetail{" +
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
