package com.example.data

import com.example.data.repository.weather.*
import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.data.repository.weather.response.*
import com.example.data.util.EMPTY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    private val testCoroutineScope = TestScope()

    private val weatherApi = Mockito.mock(WeatherApi::class.java)
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = WeatherRepositoryImpl(weatherApi)
    }

    @Test
    fun testWeatherListFetchSucceededAndSizeMatches() {
        testCoroutineScope.runTest {
            // given
            val sizeTest = 2
            Mockito.`when`(weatherApi.getWeatherList())
                .thenReturn(
                    WeatherListResponse(
                        sizeTest,
                        listOf (
                            Weather(),
                            Weather()
                        )
                    )
                )

            // when
            val result = weatherRepository.getWeatherList()

            // then
            Assert.assertEquals(result.response.size, sizeTest)
        }
    }

    @Test
    fun testWeatherListFetchSucceededAndMappingCorrect() {
        testCoroutineScope.runTest {
            // given
            val testEntity = WeatherInfoEntity (
                id = "24543531",
                name = "Sydney",
                tempMin = 10f,
                tempMax = 15f,
                pressure = 1022f,
                windSpeed = 5f
            )

            Mockito.`when`(weatherApi.getWeatherList())
                .thenReturn(
                    WeatherListResponse(
                        1,
                        listOf (
                            Weather(
                                id = "24543531",
                                name = "Sydney",
                                main = Main(
                                    temp_min = 10f,
                                    temp_max = 15f,
                                    pressure = 1022f
                                ),
                                wind = Wind(
                                    speed = 5f
                                )
                            )
                        )
                    )
                )

            // when
            val result = weatherRepository.getWeatherList()

            // then
            result.response.forEach {
                Assert.assertEquals(it, testEntity)
            }
        }
    }

    @Test
    fun testSuccessReturnedWhenIdIsNotEmptyAndMappingCorrect() {
        testCoroutineScope.runTest {
            // given
            val testWeatherId = "24543531"
            val testEntity = WeatherInfoEntity (
                id = "24543531",
                name = "Sydney",
                tempMin = 10f,
                tempMax = 15f,
                pressure = 1022f,
                windSpeed = 5f
            )

            Mockito.`when`(weatherApi.getWeatherDetails(testWeatherId))
                .thenReturn(
                    Weather(
                        id = testWeatherId,
                        name = "Sydney",
                        main = Main(
                            temp_min = 10f,
                            temp_max = 15f,
                            pressure = 1022f
                        ),
                        wind = Wind(
                            speed = 5f
                        )
                    )
                )

            // when
            val result = weatherRepository.getWeatherDetails(testWeatherId)

            // then
            Assert.assertEquals(result.response, testEntity)
        }
    }

    @Test
    fun testErrorReturnedWhenIdIsEmpty() {
        testCoroutineScope.runTest {
            // given
            val testMessage = "Oops!"
            val testWeatherId = String.EMPTY
            Mockito.`when`(weatherApi.getWeatherDetails(testWeatherId))
                .thenThrow(
                    NullPointerException(testMessage)
                )

            // when
            val result = weatherRepository.getWeatherDetails(testWeatherId)

            // then
            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.exception is NullPointerException)
            Assert.assertEquals(result.exception?.message, testMessage)
        }
    }
}