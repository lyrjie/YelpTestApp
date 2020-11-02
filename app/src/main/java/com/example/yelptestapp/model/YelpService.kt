package com.example.yelptestapp.model

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

// In production code is handled by DI instead
object YelpService {

    val client: ApolloClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ApolloClient.builder()
            .serverUrl("https://api.yelp.com/v3/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor())
                    .build()
            )
            .build()
    }
}

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                // Is never committed in real life
                "Bearer Xh4atEYpzQe93TLkoCWlLCeOUUe0R1XIwebgURbvVz9H0rdLwplfNBYO7B26ENmCulboVvcjkv1VCJVBdx_Iy0nRWKOClp5xEOQW098FnY4hOoYMZgOvdpEg0dmfX3Yx"
            )
            .build()

        return chain.proceed(request)
    }
}