package br.com.fiap.postech.pontoeletronico.pontoeletronico

import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.RegistroPontoRequestDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.service.RegistroPontoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/registrar-ponto")
class RegistroPontoController(
        private val registroPontoService: RegistroPontoService,
) {
    @PostMapping
    fun registrarPonto(@RequestBody registroPontoRequest: RegistroPontoRequestDto): ResponseEntity<Any> {
        return registroPontoService.registroPonto(registroPontoRequest)
    }
}


