package com.garhy.shutterstock.model

import com.garhy.shutterstock.exceptions.AppException
import com.google.gson.annotations.SerializedName

data class ApiResponse @JvmOverloads constructor(var page : Int? = -1 , var per_page : Int?  = -1,
                  var total_count : Int? = -1,@SerializedName("data") var imageModels : ArrayList<ImageModel>? = null, var appException: AppException?= null)