import android.app.Application
import android.content.Context
import com.example.data.BuildConfig
import com.example.data.repository.weather.WeatherApi
import com.example.data.repository.weather.WeatherRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Repositories {
    val weatherRepository: WeatherRepositoryImpl
}

val Context.repositories get() = applicationContext as Repositories

abstract class RepositoryApplication : Application(), Repositories {

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val weatherRepository by lazy {
        WeatherRepositoryImpl (
            retrofitClient.create(WeatherApi::class.java)
        )
    }
}