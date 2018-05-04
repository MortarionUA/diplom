package com.gmail.dmytro.ui.view.orderedit;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.gmail.dmytro.backend.data.entity.Line;
import com.gmail.dmytro.backend.data.entity.Product;
import com.gmail.dmytro.backend.data.entity.Quantity;
import com.gmail.dmytro.ui.util.DollarPriceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;

@SpringComponent
@PrototypeScope
public class ProductInfo extends ProductInfoDesign {

	private final DollarPriceConverter priceFormatter;

	private final ViewEventBus viewEventBus;

	private BeanValidationBinder<Line> binder;

	// Use Label instead of TextArea in "report mode" for a better presentation
	private Label readOnlyComment = new Label();

	private boolean reportMode = false;

	@Autowired
	public ProductInfo(DollarPriceConverter priceFormatter, ViewEventBus viewEventBus) {
		this.priceFormatter = priceFormatter;
		this.viewEventBus = viewEventBus;

	}

	@PostConstruct
	public void init() {
		binder = new BeanValidationBinder<>(Line.class);
		binder.setRequiredConfigurator(null);
		binder.forField(quantity).withConverter(new StringToIntegerConverter(-1, "Please enter a number"))
				.bind("count");
		binder.bindInstanceFields(this);
		binder.addValueChangeListener(e -> fireProductInfoChanged());

		product.addSelectionListener(e -> {
			Optional<Product> selectedProduct = e.getFirstSelectedItem();
//			Quantity productPrice = selectedProduct.map(Product::getValue).orElse(new Quantity(0.0, "USD"));
//			updatePrice(productPrice);
			double productPrice = selectedProduct.map(Product::getValue).orElse(0.0);
			updatePrice(productPrice/100);
		});

		readOnlyComment.setWidth("100%");
		readOnlyComment.setId(comment.getId());
		readOnlyComment.setStyleName(comment.getStyleName());

		delete.addClickListener(e -> fireOrderItemDeleted());
	}

	private void updatePrice(double productPrice) {
		String priceStr = new String();
		priceStr+=productPrice;
		price.setValue(priceStr);
	}

	private void fireProductInfoChanged() {
		viewEventBus.publish(this, new ProductInfoChangeEvent());
	}

	private void fireOrderItemDeleted() {
		viewEventBus.publish(this, new LineDeletedEvent(getItem()));
	}

	public double getSum() {
		Line item = getItem();
		return item.getCount() * item.getProduct().getValue();
	}

	public void setItem(Line item) {
		binder.setBean(item);
	}

	public Line getItem() {
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
			readOnlyComment.setVisible(!comment.isEmpty());
			readOnlyComment.setValue(comment.getValue());
			replaceComponent(comment, readOnlyComment);
		} else {
			replaceComponent(readOnlyComment, comment);
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
		return product.isEmpty();
	}

	public Stream<HasValue<?>> validate() {
		return binder.validate().getFieldValidationErrors().stream().map(BindingValidationStatus::getField);
	}

	@Override
	public void focus() {
		product.focus();
	}
}
