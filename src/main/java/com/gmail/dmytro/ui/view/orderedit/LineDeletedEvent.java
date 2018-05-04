package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Line;

public class LineDeletedEvent {

	private Line line;

	public LineDeletedEvent(Line line) {
		this.line = line;
	}

	public Line getOrderItem() {
		return line;
	}
}
