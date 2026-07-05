package za.ac.cput.factory;
/* LineItemFactory.java

   LineItem Factory class

   Author: Somila Ndoboza (231157592)

   Date: 28 June 2026
*/
import za.ac.cput.domain.LineItem;
import za.ac.cput.util.Helper;
import za.ac.cput.util.IdGenerator;

public class LineItemFactory {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * Creates a line item
     */
    public static LineItem createLineItem(String description, int quantity, double unitPrice) {
        Helper.requireNotEmptyOrNull(description, "Description");
        Helper.requirePositive(quantity, "Quantity");
        Helper.requirePositive(unitPrice, "Unit Price");

        Long lineItemId = idGenerator.generateLongId();

        return new LineItem.Builder()
                .setDescription(description)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .build();
    }

    /**
     * Creates a line item with total pre-calculated
     */
    public static LineItem createLineItemWithTotal(String description, int quantity,
                                                   double unitPrice, double total) {
        Helper.requireNotNegative(total, "Total");

        LineItem item = createLineItem(description, quantity, unitPrice);

        return new LineItem.Builder()
                .copy(item)
                .setTotal(total)
                .build();
    }
}