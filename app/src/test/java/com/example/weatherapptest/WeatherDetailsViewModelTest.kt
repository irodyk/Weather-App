package com.example.weatherapptest

import app.cash.turbine.test
import com.example.data.repository.weather.WeatherRepository
import com.example.data.repository.weather.WeatherResult
import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.data.util.EMPTY
import com.example.weatherapptest.util.DispatcherProvider
import com.example.weatherapptest.util.TestDispatcherProvider
import com.example.weatherapptest.weather.detail.WeatherDetailsUiState
import com.example.weatherapptest.weather.detail.WeatherDetailsViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherDetailsViewModelTest {

    private val testCoroutineScope = TestScope()
    private val weatherRepository = Mockito.mock(WeatherRepository::class.java)

    private lateinit var weatherDetailsViewModel: WeatherDetailsViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider()
        weatherDetailsViewModel = WeatherDetailsViewModel(weatherRepository, dispatcherProvider)
    }

    @Test
    fun testUiStateSuccessWhenIdCorrect() {
        // given
        val testWeatherId = "24382435"
        testCoroutineScope.runTest {
            // given
            Mockito.`when`(weatherRepository.getWeatherDetails(testWeatherId))
                .thenReturn(
                    WeatherResult (
                        WeatherInfoEntity(
                            id = testWeatherId,
                            tempMin = 10f,
                            tempMax = 15f,
                            pressure = 1022f,
                            windSpeed = 5f
                        )
                    )
                )

            // when
            weatherDetailsViewModel.getWeatherDetails(testWeatherId)

            // then
            weatherDetailsViewModel.weatherDetailsStateFlow.test {
                assertTrue(awaitItem() is WeatherDetailsUiState.Success)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun testUiStateErrorWhenIdEmpty() {
        // given
        val testWeatherId = String.EMPTY
        testCoroutineScope.runTest {
            // given
            Mockito.`when`(weatherRepository.getWeatherDetails(testWeatherId))
                .thenReturn(
                    WeatherResult (
                        WeatherInfoEntity(),
                        isSuccess = false,
                        exception = NullPointerException()
                    )
                )

            // when
            weatherDetailsViewModel.getWeatherDetails(testWeatherId)

            // then
            weatherDetailsViewModel.weatherDetailsStateFlow.test {
                assertTrue(awaitItem() is WeatherDetailsUiState.Error)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}