package de.abas.training.eventhandling;

import de.abas.erp.axi.event.EventException;
import de.abas.erp.axi.screen.ScreenControl;
import de.abas.erp.axi2.EventHandlerRunner;
import de.abas.erp.axi2.annotation.ButtonEventHandler;
import de.abas.erp.axi2.annotation.EventHandler;
import de.abas.erp.axi2.annotation.FieldEventHandler;
import de.abas.erp.axi2.annotation.ScreenEventHandler;
import de.abas.erp.axi2.event.ButtonEvent;
import de.abas.erp.axi2.event.FieldEvent;
import de.abas.erp.axi2.event.ScreenEvent;
import de.abas.erp.axi2.type.ButtonEventType;
import de.abas.erp.axi2.type.FieldEventType;
import de.abas.erp.axi2.type.ScreenEventType;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.field.StringField;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.schema.customer.CustomerEditor;
import de.abas.erp.db.schema.sales.SalesOrder;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.jfop.rt.api.annotation.RunFopWith;
import de.abas.jfop.base.Color;

@EventHandler(head = CustomerEditor.class)
@RunFopWith(EventHandlerRunner.class)
public class ZipCodeEventHandler {
	
	ScreenControl screenControl = null;
	CustomerEditor head = null;

	@FieldEventHandler(field = "zipCode", type = FieldEventType.VALIDATION)
	public void zipCodeValidation(FieldEvent event,
			ScreenControl screenControl, DbContext ctx, CustomerEditor head)
			throws EventException {
		String ctryCode = head.getCtryCode();
		if (!head.getZipCode().isEmpty()) {
			if (ctryCode.equals("D")) {
				if (!((head.getZipCode().matches("[1-9][0-9]{4}") || (head
						.getZipCode().matches("[0][1-9][0-9]{3}"))))) {
					throw new EventException(
							"Zip code is invalid! Please enter a valid german zip code.",
							1);
				}
			}
		}
	}

	@ButtonEventHandler(field="ytest", type = ButtonEventType.AFTER)
	public void ytestAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, CustomerEditor head) throws EventException {
//		TextBox textBox = new TextBox(ctx, "Do you feel well?", "Please enter yes or no!");
//		textBox.setButtons(ButtonSet.YES_NO);
//		EnumDialogBox yesNo = textBox.show();
//		if (yesNo.equals(EnumDialogBox.Yes)) {
//			ctx.out().println("Nice for you.");
//		}
//		else {
//			ctx.out().println("Too bad");
//		}
		SelectionBuilder<SalesOrder> selectionBuilder = SelectionBuilder.create(SalesOrder.class);
		selectionBuilder.add(Conditions.eq(SalesOrder.META.customer, head));
		Query<SalesOrder> query = ctx.createQuery(selectionBuilder.build());
		for (SalesOrder salesOrder : query) {
			ctx.out().println(salesOrder.getIdno() + " - " + salesOrder.getSwd() + " - " + salesOrder.getSubject() + " - "  + salesOrder.getDateFrom());
		}
	}

	@ScreenEventHandler(type = ScreenEventType.ENTER)
	public void screenEnter(ScreenEvent event, ScreenControl screenControl, DbContext ctx, CustomerEditor head) throws EventException {
		this.screenControl = screenControl;
		this.head = head;
		colorMandatoryFields(Customer.META.swd);
		colorMandatoryFields(Customer.META.descr);
		colorMandatoryFields(Customer.META.addr);
		colorMandatoryFields(Customer.META.street);
		colorMandatoryFields(Customer.META.zipCode);
		colorMandatoryFields(Customer.META.town);
	}

	private void colorMandatoryFields(StringField<Customer> field){
		screenControl.setColor(head, field, Color.WHITE, Color.RED);
	}
	
}
