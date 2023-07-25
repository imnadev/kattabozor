package uz.kattabozor.test.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.kattabozor.test.domain.repo.OfferRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {
    operator fun invoke() = flow {
        val offers = offerRepository.getOffers();
        emit(offers)
    }.flowOn(Dispatchers.IO)
}