package br.com.fiap.postech.pontoeletronico.pontoeletronico.controller

import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ColaboradorDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.service.AutenticacaoService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("autenticar")
class AutenticacaoController(
        private val autenticacaoService: AutenticacaoService
) {

    @PostMapping
    fun autenticar(@RequestBody colaborador: ColaboradorDto): ResponseEntity<Any> {

        val token = autenticacaoService.autenticar(colaborador)

        val headers = HttpHeaders()
        headers.add("Authorization", token)

        return ResponseEntity.ok().headers(headers).body("Colaborador autenticado")
    }
}