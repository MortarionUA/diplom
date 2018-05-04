package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.EquipmentLine;
import com.gmail.dmytro.backend.data.entity.Line;

public class EquipmentDeletedEvent {

	private EquipmentLine line;

	public EquipmentDeletedEvent(EquipmentLine line) {
		this.line = line;
	}

	public EquipmentLine getOrderItem() {
		return line;
	}
}
