package br.com.desafio.infra.gateways;

import br.com.desafio.adapter.gateway.PaymentGateway;
import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.core.domain.PaymentModel;
import br.com.desafio.core.domain.enums.PaymentType;
import br.com.desafio.infra.persitence.h2.entities.PaymentEntity;
import br.com.desafio.infra.persitence.h2.entities.PaymentItemEntity;
import br.com.desafio.infra.persitence.h2.repositories.PaymentItemRepository;
import br.com.desafio.infra.persitence.h2.repositories.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentRepositoryGateway implements PaymentGateway {

    private final PaymentRepository paymentRepository;
    private final PaymentItemRepository paymentItemRepository;

    public PaymentRepositoryGateway(PaymentRepository paymentRepository, PaymentItemRepository paymentItemRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentItemRepository = paymentItemRepository;
    }

    @Override
    public Long getSeller(Long sellerId) {
        PaymentEntity bySellerId = paymentRepository.findBySellerId(sellerId);
        if(bySellerId == null){
            return null;
        }
        return bySellerId.getSellerId();
    }

    @Override
    public PaymentItemModel getPaymentId(Long paymentId) {
        return mappemItemPayment(paymentItemRepository.gettemIdById(paymentId));
    }

    @Override
    public void savePayment(PaymentItemModel paymentItemModel) {
        PaymentItemEntity paymentItem = paymentItemRepository.gettemIdById(paymentItemModel.getItemId());
        paymentItem.setPaymentValue(paymentItemModel.getPaymentValue());
        paymentItem.setPaymentStatus(paymentItemModel.getPaymentStatus());
        paymentItemRepository.save(paymentItem);
    }

    /**
     * INTERNAL SERVICES
     */

    private PaymentModel mappedPaymentEntityRepositoryToDomain(PaymentEntity entity) {
        PaymentModel model = new PaymentModel();
        if(entity != null){
            model.setSellerId(entity.getSellerId());
            model.setPaymentItemModels(mappedPaymentItensModel(entity.getPaymentItems()));
        }
        return model;
    }

    private List<PaymentItemModel> mappedPaymentItensModel(List<PaymentItemEntity> paymentItemEntities) {
        List<PaymentItemModel> itemModels = new ArrayList<>();
        paymentItemEntities.forEach(item -> {
            PaymentItemModel itemModel = mappemItemPayment(item);
            itemModels.add(itemModel);
        });
        return itemModels;
    }

    private PaymentItemModel mappemItemPayment(PaymentItemEntity item) {
        PaymentItemModel itemModel = new PaymentItemModel();
        if(item != null) {
            itemModel.setPaymentId(item.getSellerPayment().getId());
            itemModel.setItemId(item.getItemId());
            itemModel.setPaymentValue(item.getPaymentValue());
            itemModel.setPaymentStatus(item.getPaymentStatus());
        }
        return itemModel;
    }

    private PaymentItemEntity mappemItemPaymentEntity(PaymentItemModel model) {
        PaymentItemEntity paymentItem = new PaymentItemEntity();
        paymentItem.setItemId(model.getPaymentId());
        paymentItem.setPaymentValue(model.getPaymentValue());
        paymentItem.setPaymentStatus(model.getPaymentStatus());
        return paymentItem;
    }
}
