package com.example

import javax.persistence.EntityManager
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional
import java.util.*
import javax.inject.Singleton
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Singleton
open class GenreService(@CurrentSession val entityManager: EntityManager) {

    @Transactional(readOnly = true)
    open fun findById(@NotNull id: Long?): Optional<Genre> {
        return Optional.ofNullable(entityManager.find(Genre::class.java, id))
    }

    @Transactional
    open fun save(@NotBlank name: String): Genre {
        val genre = Genre(name)
        entityManager.persist(genre)
        return genre
    }

    @Transactional
    open fun deleteById(@NotNull id: Long?) {
        findById(id).ifPresent { genre -> entityManager.remove(genre) }
    }

    @Transactional(readOnly = true)
    open fun findAll(): List<Genre> {
        val qlString = "SELECT g FROM Genre as g"
        val query = entityManager.createQuery(qlString, Genre::class.java)
        return query.resultList
    }

    @Transactional
    open fun update(@NotNull id: Long?, @NotBlank name: String): Int {
        return entityManager.createQuery("UPDATE Genre g SET name = :name where id = :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate()
    }
}