package com.example.branchtask.di
import com.example.branchtask.common.constants
import com.example.branchtask.data.remote.HomeAPi
import com.example.branchtask.data.remote.OathInterceptor
import com.example.branchtask.data.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object userinfomodule {

    @Provides
    @Singleton // ensure this have only the singleton instance throughout the whole lifetime of our app
    fun provideRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder().baseUrl(constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: OathInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesUserRepository(retrofitBuilder: Retrofit.Builder) : UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesHomeAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): HomeAPi {
        return retrofitBuilder.client(okHttpClient).build().create(HomeAPi::class.java)
    }


}