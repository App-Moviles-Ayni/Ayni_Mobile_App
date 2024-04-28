package com.curidev.ayni.order.repository

import android.util.Log
import com.curidev.ayni.order.domain.model.Sale
import com.curidev.ayni.order.remote.SaleService
import com.curidev.ayni.order.remote.SaleServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaleRepository(
    private val saleService: SaleService = SaleServiceFactory.getSaleService()
) {
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