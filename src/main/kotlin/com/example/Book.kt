package com.example

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue


@Entity
@Table(name = "book")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null

    @NotNull
    @Column(name = "isbn", nullable = false)
    var isbn: String? = null

    @ManyToOne
    var genre: Genre? = null

    constructor() {}

    constructor(@NotNull isbn: String, @NotNull name: String, genre: Genre) {
        this.isbn = isbn
        this.name = name
        this.genre = genre
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Book{")
        sb.append("id=")
        sb.append(id)
        sb.append(", name='")
        sb.append(name)
        sb.append("', isbn='")
        sb.append(isbn)
        sb.append("', genre='")
        sb.append(genre)
        sb.append("'}")
        return sb.toString()
    }
}