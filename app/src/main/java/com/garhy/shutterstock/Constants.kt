package com.garhy.shutterstock

class Constants {
    companion object{
        const val CONSUMER_KEY =	"fd01c-b62a9-08463-7a773-db59b-62392"
        const val CONSUMER_SECRET = 	"75061-85b0a-eb765-61504-b3914-48b84"
        const val BASE_URL = "https://api.shutterstock.com/"
        const val HEADER_AUTH: String = "Authorization"
        const val ITEMS_PER_PAGE = 20
        const val SEARCH_API = "v2/images/search"
        const val QUERY_PARAM = "query"
        const val PAGE_PARAM = "page"
        const val PER_PAGE_PARAM = "per_page"
    }
}