package br.com.fiap.postech.pontoeletronico.pontoeletronico.service

import br.com.fiap.postech.pontoeletronico.pontoeletronico.RegistroPonto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.RegistroPontoRequestDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.ColaboradorRepository
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.RegistroPontoRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class RegistroPontoService(
        private val registroPontoRepository: RegistroPontoRepository,
        private val colaboradorRepository: ColaboradorRepository
) {
    fun registroPonto(registroPontoRequest: RegistroPontoRequestDto): ResponseEntity<Any> {
        val colaboradorOpt = colaboradorRepository.findById(registroPontoRequest.colaboradorId)

        if (colaboradorOpt.isEmpty) {
            return ResponseEntity.badRequest().body("Colaborador não encontrado")
        }

        val colaborador = colaboradorOpt.get()

        val dataHoje = LocalDate.now()
        val ultimoRegistroHoje = registroPontoRepository.findTopByColaboradorAndDateOrderByHoraDesc(colaborador, dataHoje).firstOrNull()

        val tipo = determinarTipoDeRegistro(ultimoRegistroHoje)

        val novoRegistroPonto = RegistroPonto(
                colaborador = colaborador,
                tipo = determinarTipoDeRegistro(ultimoRegistroHoje),
                hora = LocalDateTime.now()
        )

        registroPontoRepository.save(novoRegistroPonto)

        return ResponseEntity.ok().body("Ponto registrado com sucesso. Tipo de registro: $tipo")
    }

    private fun determinarTipoDeRegistro(ultimoRegistro: RegistroPonto?): String {
        return if (ultimoRegistro == null) {
            "entrada"
        } else {
            when (ultimoRegistro.tipo) {
                "entrada" -> "saída"
                "saída" -> "entrada"
                else -> "entrada"
            }
        }
    }
}
