package me.arwan.weatherforecast.di

import android.content.Context
import androidx.room.Room
import me.arwan.weatherforecast.data.local.database.WeatherDataBase
import me.arwan.weatherforecast.data.local.datasource.WeatherLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.arwan.weatherforecast.data.local.database.CoordinatesDAO
import me.arwan.weatherforecast.data.local.datasource.WeatherLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideWeathersDatabase(
        @ApplicationContext context: Context,
    ): WeatherDataBase = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        "WEATHER_DB"
    ).build()

    @Provides
    @Singleton
    fun provideGenreDao(
        weatherDataBase: WeatherDataBase,
    ): CoordinatesDAO = weatherDataBase.getCoordinatesDao()

    @Singleton
    @Provides
    fun provideWeathersDataSource(
        coordinatesDAO: CoordinatesDAO
    ): WeatherLocalDataSource = WeatherLocalDataSourceImpl(coordinatesDAO)

}