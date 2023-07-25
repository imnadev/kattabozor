package uz.kattabozor.test.data.api

import retrofit2.http.GET
import uz.kattabozor.test.domain.model.offer.OfferResponse

interface OfferApi {

    @GET("offers")
    suspend fun getOffers() : OfferResponse
}