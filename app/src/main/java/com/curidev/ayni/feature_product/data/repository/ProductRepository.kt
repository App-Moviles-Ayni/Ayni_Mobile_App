package com.curidev.ayni.feature_product.data.repository

import android.util.Log
import com.curidev.ayni.feature_product.data.remote.ProductResponse
import com.curidev.ayni.feature_product.data.remote.ProductService
import com.curidev.ayni.feature_product.data.remote.ProductServiceFactory
import com.curidev.ayni.feature_product.domain.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository (
    private val productService: ProductService = ProductServiceFactory.getProductService()
) {
    fun getAll(callback: (List<Product>) -> Unit) {
        val getAll = productService.getAll()

        getAll.enqueue(object: Callback<List<ProductResponse>> {
            override fun onResponse(
                call: Call<List<ProductResponse>>,
                response: Response<List<ProductResponse>>
            ) {
                if (response.isSuccessful) {
                    val productsResponse = response.body() ?: emptyList()
                    val products: List<Product> = productsResponse.map { productResponse ->
                        Product(
                            id = productResponse.id,
                            name = productResponse.name, // Cambiado a name en lugar de description
                            description = productResponse.description,
                            recommendedCultivationDistance = productResponse.recommendedCultivationDistance,
                            recommendedCultivationDepth = productResponse.recommendedCultivationDepth,
                            recommendedGrowingClimate = productResponse.recommendedGrowingClimate,
                            recommendedSoilType = productResponse.recommendedSoilType,
                            recommendedGrowingSeason = productResponse.recommendedGrowingSeason,
                            imageUrl = productResponse.imageUrl,
                            userId = productResponse.userId.toInt()
                        )
                    }
                    callback(products)
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("ProductRepository", it)
                }
            }
        })
    }

    fun getProductById(id: Int, callback: (Product) -> Unit) {
        val getProductById = productService.getProductById(id = id)

        getProductById.enqueue(object: Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { product ->
                        callback(product)
                    }
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                t.message?.let {
                    Log.d("ProductRepository", it)
                }
            }
        })
    }
}