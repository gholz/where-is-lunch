package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.BuildConfig
import com.guilhermeholz.whereislunch.network.model.yelp.AuthResponse
import com.guilhermeholz.whereislunch.network.model.yelp.Business
import com.guilhermeholz.whereislunch.network.model.yelp.BusinessDetail
import com.guilhermeholz.whereislunch.network.model.yelp.SearchResponse
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import rx.Observable
import java.util.*

@OpenForTesting
class YelpApiMockFactory {
    companion object {
        fun getApi(): YelpApi {
            return mock {
                on {
                    it.getBusinessDetail(any(), any())
                } doReturn (Observable.just(getBusinessDetail()))

                on {
                    it.search(any(), any(), any())
                } doReturn (Observable.just(getSearchResponse()))

                on {
                    it.getAuthToken("client_credentials",
                            BuildConfig.YELP_CLIENT_ID,
                            BuildConfig.YELP_SECRET_KEY)
                } doReturn (Observable.just(getAuthResponse()))
            }
        }

        private fun getAuthResponse() = AuthResponse("token", "bearer", Date().time + 10000)

        private fun getSearchResponse(): SearchResponse {
            return SearchResponse(10, getBusinessList())
        }

        private fun getBusinessList(): List<Business> {
            return (0..10).map { getBusiness(it) }
        }

        private fun getBusiness(index: Int): Business {
            return Business(4.5f, "$", "+3423423435", "id_$index", false, listOf(), 100 + index,
                    "name", "url", mock(), "image", mock(), 100f)
        }

        private fun getBusinessDetail(): BusinessDetail {
            return BusinessDetail("id", "name", "image", false, false, "url", "$$$",
                    4.5f, 100, "+4321233424", listOf("photo1", "photo2", "photo3"),
                    listOf(), mock(), mock())
        }
    }
}