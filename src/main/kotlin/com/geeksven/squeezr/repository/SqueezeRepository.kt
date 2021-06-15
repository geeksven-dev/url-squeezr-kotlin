package com.geeksven.squeezr.repository

import com.geeksven.squeezr.model.Squeeze
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SqueezeRepository : CrudRepository<Squeeze, Long> {
    fun findBySlug(slug: String): Squeeze

    @Query("select * from Squeeze s LEFT JOIN Visits v on s.id = v.squeeze where v.created > (current_date - 1)")
    fun findByVisitsInLast24h(): List<Squeeze>
}