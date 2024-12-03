/*
 * Copyright (c) 2024. Desarrollo de Soluciones Moviles, DISC.
 */

package cl.ucn.disc.dsm.pictwin.data.network

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * The base URL of the API.
     */
    const val URL = "http://localhost:7000/"

    /**
     * Provides the Gson instance.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            // .setDateFormat("yyyy-MM-dd'HH:mm:ss")
            .create()
    }

    /**
     * Provides the [ApiService] instance.
     */
    @Provides
    @Singleton
    fun provideApiService(gson: Gson): ApiService {

        val log = LoggerFactory.getLogger(ApiService::class.java)

        val logging = HttpLoggingInterceptor { message -> log.debug(message) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}