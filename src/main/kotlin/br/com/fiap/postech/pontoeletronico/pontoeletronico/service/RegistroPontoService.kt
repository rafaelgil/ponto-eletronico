package br.com.fiap.postech.pontoeletronico.pontoeletronico.service

import br.com.fiap.postech.pontoeletronico.pontoeletronico.RegistroPonto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ErrorResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.RegistroPontoResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.ColaboradorRepository
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.RegistroPontoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class RegistroPontoService(
        private val registroPontoRepository: RegistroPontoRepository,
        private val colaboradorRepository: ColaboradorRepository
) {
    fun registroPonto(matricula: String): ResponseEntity<Any> {
        val colaboradorOpt = colaboradorRepository.findByMatricula(matricula)

        if (colaboradorOpt.isEmpty) {
            val errorResponse = ErrorResponseDto(
                    codigo = HttpStatus.NOT_FOUND.value(),
                    mensagem = "Colaborador não encontrado"
            )

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val colaborador = colaboradorOpt.get()

        val dataHoje = LocalDate.now()
        val ultimoRegistroHoje = registroPontoRepository.findTopByColaboradorAndDateOrderByHoraDesc(colaborador, dataHoje).firstOrNull()

        val novoRegistroPonto = RegistroPonto(
                colaborador = colaborador,
                hora = LocalDateTime.now(),
                tipo = determinarTipoDeRegistro(ultimoRegistroHoje)
        )

        registroPontoRepository.save(novoRegistroPonto)

        val responseDto = RegistroPontoResponseDto(
                tipo = novoRegistroPonto.tipo,
                hora = novoRegistroPonto.hora,
                nomeColaborador = colaborador.nome,
                matriculaColaborador =  colaborador.matricula
        )

        return ResponseEntity.ok(responseDto)
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
