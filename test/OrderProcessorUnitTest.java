import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ec.InventoryService;
import ec.OrderProcessor;

public class OrderProcessorUnitTest {
    
    @Test
    void testProcessOrder_InStock() {
        InventoryService inventoryService = Mockito.mock(InventoryService.class);
        OrderProcessor orderProcessor = new OrderProcessor(inventoryService);
        Mockito.when(inventoryService.checkStock(1)).thenReturn(10);
        assertTrue(orderProcessor.processOrder(1, 5));
    }

    @Test
    public void testProcessOrder_OutOfStock() {
        InventoryService inventoryService = Mockito.mock(InventoryService.class);
        OrderProcessor orderProcessor = new OrderProcessor(inventoryService);
        Mockito.when(inventoryService.checkStock(1)).thenReturn(0);
        assertFalse(orderProcessor.processOrder(1, 5));
    }
}
