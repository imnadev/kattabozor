package uz.kattabozor.test.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.kattabozor.test.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logger)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient, converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder().addConverterFactory(converterFactory).client(httpClient)
            .baseUrl("https://www.kattabozor.uz/hh/test/api/v1/").build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)
}