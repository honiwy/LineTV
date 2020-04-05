package studio.honidot.linetv.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import studio.honidot.linetv.BuildConfig
import studio.honidot.linetv.data.DramaResult

private const val HOST_NAME = "static.linetv.tw"
private const val JSON_ROUTE = "dramas-sample.json"
private const val BASE_URL = "https://$HOST_NAME/interview/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = when (BuildConfig.LOGGER_VISIABLE) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    })
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface LineTVApiService {
    @GET(JSON_ROUTE)
    fun getDramas():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<DramaResult>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object LineTVApi {
    val RETROFIT_SERVICE : LineTVApiService by lazy { retrofit.create(LineTVApiService::class.java) }
}