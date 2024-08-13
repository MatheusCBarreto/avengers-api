import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import one.digitalinnovation.avengersapi.domain.avenger.Avenger
import org.jetbrains.annotations.NotNull

data class AvengerRequest(


    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val nick: String,

    @field:NotNull
    @field:NotBlank
    @field:NotEmpty
    val person: String,

    val description: String? = "",
    val history: String? = "",

    ) {
    // método da instância
    fun toAvenger() = Avenger(
        nick = nick,
        person = person,
        description = description,
        history = history
    )
    // método da classe
    companion object {
        fun to(id: Long?, avenger: AvengerRequest) = Avenger(
            id = id,
            nick = avenger.nick,
            person = avenger.person,
            description = avenger.description,
            history = avenger.history
        )
    }
}