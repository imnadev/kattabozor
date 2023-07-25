package uz.kattabozor.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.kattabozor.test.data.api.OfferApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOfferApi(retrofit: Retrofit): OfferApi = retrofit.create(OfferApi::class.java)
}