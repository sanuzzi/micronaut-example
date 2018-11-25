package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.validation.Validated
import org.jetbrains.annotations.NotNull
import javax.validation.Valid
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.*
import java.net.URI
import javax.validation.constraints.NotBlank


@Validated
@Controller("/people") // Ruta principal. Todas las rutas serán /people/...saraza...
class PeopleController(val genreService: GenreService) {

    @Get("/{id}")
    fun show(id: Long?): Genre {
        return genreService
                .findById(id)
                .orElse(null)
    }

    @Put("/{id}")
    fun update(id: Long, @Body @Valid command: GenreCommand): HttpResponse<*> {
        genreService.update(id, command.name)

        return HttpResponse
                .noContent<Any>()
                .header(HttpHeaders.LOCATION, location(id).path)
    }

    @Get(value = "/")
    fun index(): List<Genre> {
        return genreService.findAll()
    }

    @Post("/")
    fun save(@Body @Valid cmd: GenreCommand): HttpResponse<Genre> {
        val genre = genreService.save(cmd.name)

        return HttpResponse
                .created<Genre>(genre)
                .headers { headers -> headers.location(location(genre.id)) }
    }

    @Delete("/{id}")
    fun delete(id: Long?): HttpResponse<*> {
        genreService.deleteById(id)
        return HttpResponse.noContent<Any>()
    }

    protected fun location(id: Long?): URI {
        return URI.create("/genres/" + id!!)
    }

    protected fun location(genre: Genre): URI {
        return location(genre.id)
    }





    private val engine = V8Engine

    @Get("/old/") // La ruta quedaría "/people" o "people/"
    fun indexOld(): HttpResponse<List<Person>> = HttpResponse.ok(peopleService.people) // ¡Se puede poner "=" como en wollok!

    @Post("/old/") // Misma ruta, pero
    fun saveOld(@Body person: Person): HttpResponse<Person> {
        peopleService.people.add(person)
        return HttpResponse.created(person)
    }

    @Get("/engines")
    fun engines() : HttpResponse<String> = HttpResponse.ok(this.engine.start())
}

class Person(@NotNull val name: String)

object peopleService {
    val people =  mutableListOf(
            Person(name = "Fer"),
            Person(name = "Naty"),
            Person(name = "Migue")
    )
}

object V8Engine {
    val cylinders = 8

    fun start() : String {
        return "Starting V8"
    }
}


class GenreCommand(@NotBlank val name: String)