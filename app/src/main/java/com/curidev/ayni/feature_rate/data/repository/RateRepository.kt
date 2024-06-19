package com.curidev.ayni.feature_rate.data.repository

import android.util.Log
import com.curidev.ayni.feature_product.data.remote.RateResponse
import com.curidev.ayni.feature_rate.data.remote.RateService
import com.curidev.ayni.feature_product.data.remote.RateServiceFactory
import com.curidev.ayni.feature_product.domain.model.Rate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateRepository(
    private val rateService: RateService = RateServiceFactory.getRateService()
) {
    fun createRate(rate: Rate, callback: (Rate) -> Unit) {
        val createRate = rateService.createRate(rate)

        createRate.enqueue(object: Callback<Rate> {
            override fun onResponse(
                call: Call<Rate>,
                response: Response<Rate>
            ) {
                if (response.isSuccessful){
                    callback(response.body() as Rate)
                }
            }
            override fun onFailure(
                call: Call<Rate>,
                t: Throwable
            ) {
                t.message?.let {
                    Log.d("RateRepository", it)
                }
            }
        })
    }
    fun getAll(callback: (List<Rate>) -> Unit) {
        val getAll = rateService.getAll()

        getAll.enqueue(object: Callback<List<RateResponse>> {
            override fun onResponse(
                call: Call<List<RateResponse>>,
                response: Response<List<RateResponse>>
            ) {
                    if (response.isSuccessful) {
                        val ratesResponse = response.body() as List<RateResponse>
                        var rates: List<Rate> = emptyList()
                        for (rateResponse in ratesResponse) {
                            rates = rates + Rate(
                                rateResponse.id,
                                rateResponse.rate,
                                rateResponse.date,
                                rateResponse.productId,
                                rateResponse.userId
                            )
                        }
                        callback(rates)
                    }
                }

            override fun onFailure(call: Call<List<RateResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("RateRepository", it)
                }
            }
        })
    }

    fun getRateById(id: Int, callback: (Rate) -> Unit){
        val getRateById = rateService.getRateById(id = id)

        getRateById.enqueue(object: Callback<RateResponse> {
            override fun onResponse(
                call: Call<RateResponse>,
                response: Response<RateResponse>
            ) {
                if (response.isSuccessful) {
                    val rateResponse = response.body() as RateResponse
                    val rate = Rate(
                        rateResponse.id,
                        rateResponse.rate,
                        rateResponse.date,
                        rateResponse.productId,
                        rateResponse.userId
                    )
                    callback(rate)
                }
            }

            override fun onFailure(call: Call<RateResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("RateRepository", it)
                }
            }
        })
    }
}