package uz.kattabozor.test.domain.repo

import uz.kattabozor.test.domain.model.offer.Offer

interface OfferRepository {
    suspend fun getOffers() : List<Offer>
}