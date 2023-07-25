package uz.kattabozor.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.kattabozor.test.data.repo.OfferRepositoryImpl
import uz.kattabozor.test.domain.repo.OfferRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOfferRepository(offerRepositoryImpl: OfferRepositoryImpl): OfferRepository
}