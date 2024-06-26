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

        getAll.enqueue(object : Callback<List<SaleResponse>> {
            override fun onResponse(
                call: Call<List<SaleResponse>>,
                response: Response<List<SaleResponse>>
            ) {
                if (response.isSuccessful) {
                    val salesResponse = response.body() ?: emptyList()
                    val sales = mutableListOf<Sale>()

                    for (saleResponse in salesResponse) {
                        val sale = Sale(
                            saleResponse.id ?: 0,
                            saleResponse.name ?: "",
                            saleResponse.description ?: "",
                            saleResponse.unitPrice ?: 0.0,
                            saleResponse.quantity ?: 0,
                            saleResponse.imageUrl ?: "",
                            saleResponse.userId ?: 0
                        )
                        sales.add(sale)
                    }
                    callback(sales)
                } else {
                    Log.d("SaleRepository", "Failed to get sales: ${response.message()}")
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<SaleResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("SaleRepository", it)
                }
                callback(emptyList())
            }
        })
    }

    fun getSaleByName(name:String, callback: (List<Sale>) -> Unit) {
        val getSalesByName = saleService.getSalesByName(name)

        getSalesByName.enqueue(object: Callback<List<SaleResponse>> {
            override fun onResponse(
                call: Call<List<SaleResponse>>,
                response: Response<List<SaleResponse>>
            ) {
                if (response.isSuccessful) {
                    val salesResponse = response.body() as List<SaleResponse>
                    val sales = salesResponse.map { salesResponse ->
                        Sale(
                            salesResponse.id,
                            salesResponse.name,
                            salesResponse.description,
                            salesResponse.unitPrice,
                            salesResponse.quantity,
                            salesResponse.imageUrl,
                            salesResponse.userId
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
        val getSaleById = saleService.getSaleById(saleId = id)

        getSaleById.enqueue(object: Callback<Sale> {
            override fun onResponse(
                call: Call<Sale>,
                response: Response<Sale>) {
                if (response.isSuccessful) {
                    response.body()?.let { sale ->
                        callback(sale)
                    }
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