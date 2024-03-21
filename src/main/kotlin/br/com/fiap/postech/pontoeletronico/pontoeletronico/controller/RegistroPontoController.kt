package br.com.fiap.postech.pontoeletronico.pontoeletronico

import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.RegistroPontoRequestDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.service.RegistroPontoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/registrar-ponto")
class RegistroPontoController(
        private val registroPontoService: RegistroPontoService,
) {
    @PostMapping
    fun registrarPonto(@RequestParam("colaboradorId") colaboradorId: Long): ResponseEntity<Any> {
        return registroPontoService.registroPonto(colaboradorId)
    }

    @GetMapping
    fun relatorioPonto(@RequestParam("colaboradorId") colaboradorId: Long, @RequestParam("data") data: LocalDate): ResponseEntity<Any> {
        return registroPontoService.visualizaPontoDia(colaboradorId, data)
    }

    @GetMapping
    @RequestMapping("/espelho")
    fun relatorioEspelhoPonto(@RequestParam("colaboradorId") colaboradorId: Long, @RequestParam("ano") ano: Int, @RequestParam("mes") mes: Int): ResponseEntity<Any> {
        return registroPontoService.visualizaEspelhoPonto(colaboradorId, ano, mes)
    }
}


