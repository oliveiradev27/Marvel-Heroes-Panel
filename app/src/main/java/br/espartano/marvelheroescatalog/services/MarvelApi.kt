package br.espartano.marvelheroescatalog.services

import br.espartano.marvelheroescatalog.constants.ApiConstants.API_KEY
import br.espartano.marvelheroescatalog.constants.ApiConstants.BASE_URL
import br.espartano.marvelheroescatalog.constants.ApiConstants.HEADER_API_KEY
import br.espartano.marvelheroescatalog.constants.ApiConstants.HEADER_HASH
import br.espartano.marvelheroescatalog.constants.ApiConstants.HEADER_TS
import br.espartano.marvelheroescatalog.constants.ApiConstants.PRIVATE_API_KEY
import br.espartano.marvelheroescatalog.data.api.Response
import br.espartano.marvelheroescatalog.extensions.md5
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getAllCharacters(@Query("offset") offset: Int? = 0): Observable<Response>?

    companion object {
        fun getService(): MarvelApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val ts = (Calendar
                        .getInstance(TimeZone.getTimeZone("UTC"))
                        .timeInMillis / 1000L).toString()

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter(HEADER_API_KEY, API_KEY)
                        .addQueryParameter(HEADER_TS, ts)
                        .addQueryParameter(HEADER_HASH, "$ts$PRIVATE_API_KEY$API_KEY".md5())
                        .build()

                    chain.proceed(original.newBuilder().url(url).build())
                }

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
                .create(MarvelApi::class.java)
        }
    }
}
