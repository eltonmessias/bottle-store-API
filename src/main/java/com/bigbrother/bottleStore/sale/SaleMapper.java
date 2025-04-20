package com.bigbrother.bottleStore.sale;

import com.bigbrother.bottleStore.sale.payment.PaymentMethodUsedResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleMapper {
    public SaleResponse fromSale(Sale sale) {
        String customerName = "";

        if(sale.getCustomer() != null) {
            customerName = sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName();
        }

        List<PaymentMethodUsedResponse> paymentMethods = sale.getPaymentMethods().stream()
                .map(pm -> new PaymentMethodUsedResponse(pm.getPaymentMethod(), pm.getAmount())).toList();
        return new SaleResponse(
                sale.getId(),
                sale.getSeller().getId(),
                sale.getSaleDate(),
                customerName,
                sale.getTotalAmount(),
                sale.getTotalProfit(),
                paymentMethods
        );
    }
}
