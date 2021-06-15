package com.geeksven.squeezr.model.api

data class StatisticsResponse(val totalSqueezes : Int, val totalVisitsLastDay : Int, val allDocs: List<StatisticSqueeze>)
