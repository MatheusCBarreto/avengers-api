import jakarta.validation.Valid
import one.digitalinnovation.avengersapi.domain.avenger.AvengerRepository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val API_PATH = "/api/v1/avangers"

@RestController
@RequestMapping(value = [API_PATH])
class AvengersResource(

    @Autowired
    private val avengersRepository: AvengerRepository

) {

    @GetMapping
    fun getAvengers() = avengersRepository.getAvengers()
        .map{ AvengersResponse.from(it) }
        .let { ResponseEntity.ok().body(it) }


    @GetMapping("{id}/detail")
    fun getAvengersDetails(@PathVariable("id") id: Long) =
        avengersRepository.getDetail(id)?.let {
            ResponseEntity.ok().body(AvengersResponse.from(it))
        } ?: ResponseEntity.notFound().build<Void>()


    @PostMapping
    fun createAvenger(@Valid @RequestBody request: AvengerRequest) =
        request.toAvenger()
            .run { avengersRepository.create(this) }
            .let { ResponseEntity.created(URI("$API_PATH/${it.id}"))
                .body(AvengersResponse.from(it))
            }

    @PutMapping("{id}")
    fun updateAvenger(@Valid @RequestBody request: AvengerRequest, @PathVariable("id") id: Long) =
        avengersRepository.getDetail(id)?.let {
            AvengerRequest.to(it.id, request).apply {
                avengersRepository.update(this)
            }.let { avenger ->
                ResponseEntity.accepted().body(AvengersResponse.from(avenger))
            }
        } ?: ResponseEntity.notFound().build<Void>()


    @DeleteMapping("{id}")
    fun deleteAvenger(@PathVariable("id") id: Long) =
        avengersRepository.delete(id).let {
            ResponseEntity.accepted().build<Void>()
        }
}