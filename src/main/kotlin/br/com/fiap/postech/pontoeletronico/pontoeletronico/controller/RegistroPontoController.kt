package br.com.fiap.postech.pontoeletronico.pontoeletronico.controller

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
    fun registrarPonto(
            @RequestParam("matricula") matricula: String,
            @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<Any> {
        return registroPontoService.registroPonto(matricula, authorization)
    }

    @GetMapping
    fun relatorioPonto(
        @RequestParam("colaboradorId") colaboradorId: Long,
        @RequestParam("data") data: LocalDate,
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<Any> {
        return registroPontoService.visualizaPontoDia(colaboradorId, data, authorization)
    }

    @GetMapping
    @RequestMapping("/espelho")
    suspend fun relatorioEspelhoPonto(@RequestHeader("Authorization") authorization: String,
                                      @RequestParam("colaboradorId") colaboradorId: Long,
                                      @RequestParam("email") email: String,
                                      @RequestParam("ano") ano: Int,
                                      @RequestParam("mes") mes: Int): ResponseEntity<Any> {
        return registroPontoService.visualizaEspelhoPonto(colaboradorId, email, ano, mes, authorization)
    }
}


