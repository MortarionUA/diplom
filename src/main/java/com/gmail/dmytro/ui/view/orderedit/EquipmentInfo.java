package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Equipment;
import com.gmail.dmytro.backend.data.entity.EquipmentLine;
import com.gmail.dmytro.backend.data.entity.Line;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Stream;

@SpringComponent
@PrototypeScope
public class EquipmentInfo extends EquipmentInfoDesign {

	private final ViewEventBus viewEventBus;

	private BeanValidationBinder<EquipmentLine> binder;

	// Use Label instead of TextArea in "report mode" for a better presentation
	private Label readOnlyComment = new Label();

	private boolean reportMode = false;

	@Autowired
	public EquipmentInfo(ViewEventBus viewEventBus) {
		this.viewEventBus = viewEventBus;

	}

	@PostConstruct
	public void init() {
		binder = new BeanValidationBinder<>(EquipmentLine.class);
		binder.setRequiredConfigurator(null);
		binder.bindInstanceFields(this);

		binder.addValueChangeListener(e -> fireEquipmentInfoChanged());

		equipment.addSelectionListener(e -> {
			Optional<Equipment> selectedEquipmet = e.getFirstSelectedItem();
		});

		readOnlyComment.setWidth("100%");
		readOnlyComment.setId(info.getId());
		readOnlyComment.setStyleName(info.getStyleName());

		delete.addClickListener(e -> fireOrderItemDeleted());
	}

	private void fireEquipmentInfoChanged() {
		viewEventBus.publish(this, new EquipmentInfoChangeEvent());
	}

	private void fireOrderItemDeleted() {
		viewEventBus.publish(this, new EquipmentDeletedEvent(getItem()));
	}

	public void setItem(EquipmentLine item) {
		binder.setBean(item);
	}

	public EquipmentLine getItem() {
		return binder.getBean();
	}

	public void setReportMode(boolean reportMode) {
		if (reportMode == this.reportMode) {
			return;
		}
		this.reportMode = reportMode;
		binder.setReadOnly(reportMode);
		delete.setVisible(!reportMode);

		// Swap the TextArea for a Label in report mode
		if (reportMode) {
			readOnlyComment.setVisible(!info.isEmpty());
			readOnlyComment.setValue(info.getValue());
			replaceComponent(info, readOnlyComment);
		} else {
			replaceComponent(readOnlyComment, info);
		}
	}

	/**
	 * Checks if no product has been selected. If no product is selected, the
	 * whole product info section is ignored when saving changes.
	 *
	 * @return <code>true</code> if no product is selected, <code>false</code>
	 *         otherwise
	 */
	public boolean isEmpty() {
		return equipment.isEmpty();
	}

	public Stream<HasValue<?>> validate() {
		return binder.validate().getFieldValidationErrors().stream().map(BindingValidationStatus::getField);
	}

	@Override
	public void focus() {
		equipment.focus();
	}
}
