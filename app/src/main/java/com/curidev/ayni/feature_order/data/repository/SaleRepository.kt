package com.curidev.ayni.feature_order.data.repository

import android.util.Log
import com.curidev.ayni.feature_order.data.remote.SaleResponse
import com.curidev.ayni.feature_order.domain.model.Sale
import com.curidev.ayni.feature_order.data.remote.SaleService
import com.curidev.ayni.feature_order.data.remote.SaleServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaleRepository(
    private val saleService: SaleService = SaleServiceFactory.getSaleService()
) {
    fun getAll(callback: (List<Sale>) -> Unit) {
        val getAll = saleService.getAll()

        getAll.enqueue(object: Callback<List<SaleResponse>> {
            override fun onResponse(
                call: Call<List<SaleResponse>>,
                response: Response<List<SaleResponse>>) {
                    if (response.isSuccessful) {
                        val salesResponse = response.body() as List<SaleResponse>
                        var sales: List<Sale> = emptyList()
                        for (saleResponse in salesResponse) {
                            sales = sales + Sale(
                                saleResponse.id,
                                saleResponse.name,
                                saleResponse.description,
                                saleResponse.price,
                                saleResponse.quantity,
                                saleResponse.imageUrl,
                                saleResponse.userId
                            )
                        }
                        callback(sales)
                    }
                }

            override fun onFailure(call: Call<List<SaleResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("SaleRepository", it)
                }
            }
        })
    }

    fun getSaleById(id: Int, callback: (Sale) -> Unit) {
        val getSaleById = saleService.getSaleById(id = id)

        getSaleById.enqueue(object: Callback<Sale> {
            override fun onResponse(
                call: Call<Sale>,
                response: Response<Sale>
            ) {
                    if (response.isSuccessful) {
                        callback(response.body() as Sale)
                    }
                }

            override fun onFailure(call: Call<Sale>, t: Throwable) {
                t.message?.let {
                    Log.d("SaleRepository", it)
                }
            }
        })

    }
}