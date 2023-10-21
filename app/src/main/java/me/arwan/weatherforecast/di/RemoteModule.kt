package me.arwan.weatherforecast.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arwan.weatherforecast.BuildConfig
import me.arwan.weatherforecast.data.remote.datasource.WeatherRemoteDataSourceImpl
import me.arwan.weatherforecast.data.remote.service.WeatherService
import me.arwan.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val url = chain.request().url().newBuilder()
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()
            val request = chain.request().newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(
        weatherService: WeatherService
    ): WeatherRemoteDataSource = WeatherRemoteDataSourceImpl(weatherService)

}