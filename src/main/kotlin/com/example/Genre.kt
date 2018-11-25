package com.example

import java.util.HashSet
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "genre")
class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    var books: MutableSet<Book> = HashSet()

    constructor() {}

    constructor(@NotNull name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\''.toString() +
                '}'.toString()
    }
}