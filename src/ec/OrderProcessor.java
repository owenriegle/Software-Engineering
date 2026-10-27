package ec;

public class OrderProcessor {

    private final InventoryService inventoryService;

    public OrderProcessor(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public boolean processOrder(int itemID, int quantity) {

        if (inventoryService.checkStock(itemID) >= quantity) {
            inventoryService.reserveItem(itemID, quantity);
            return true;
        } else {
            return false;
        }
    }

}
