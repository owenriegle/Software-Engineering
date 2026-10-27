import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ec.InventoryService;
import ec.OrderProcessor;

public class OrderProcessorIntegrationTest {

    public class InMemoryInventoryService implements InventoryService {

        ArrayList<Integer> inventory = new ArrayList<>();

        @Override
        public int checkStock(int itemID) {

            return inventory.get(itemID);
        }

        @Override
        public boolean reserveItem(int itemID, int quantity) {

            int stockInventory = checkStock(itemID);
            if (quantity <= stockInventory) {
                inventory.set(itemID, (inventory.get(itemID) - quantity));
                return true;
            } else {
                return false;
            }
        }
        
    }

    @Test
    public void testOrderProcessorWithRealInventory() {

        InMemoryInventoryService inventoryService = new InMemoryInventoryService();

        // add fake quantities
        inventoryService.inventory.add(3);
        inventoryService.inventory.add(7);
        inventoryService.inventory.add(20);

        OrderProcessor orderProcessor = new OrderProcessor(inventoryService);
        orderProcessor.processOrder(0, 2);

        assertEquals(1, inventoryService.inventory.get(0));
    }
}
