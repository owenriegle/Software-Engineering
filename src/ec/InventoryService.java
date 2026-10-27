package ec;

public interface InventoryService {

    int checkStock(int itemID);

    boolean reserveItem(int itemID, int quantity);
}
