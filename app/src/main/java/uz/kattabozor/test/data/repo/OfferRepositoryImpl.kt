package uz.kattabozor.test.data.repo

import uz.kattabozor.test.data.api.OfferApi
import uz.kattabozor.test.domain.model.offer.Offer
import uz.kattabozor.test.domain.repo.OfferRepository
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerApi: OfferApi
) : OfferRepository {

    override suspend fun getOffers(): List<Offer> {
        return offerApi.getOffers().offers
    }
}