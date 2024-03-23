package br.com.fiap.postech.pontoeletronico.pontoeletronico.service

import br.com.fiap.postech.pontoeletronico.pontoeletronico.RegistroPonto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ErrorResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.EspelhoPontoDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.RegistroPontoResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.VisualizacaoRegistrosResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.ColaboradorRepository
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.RegistroPontoRepository
import br.com.fiap.postech.pontoeletronico.pontoeletronico.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class RegistroPontoService(
        private val registroPontoRepository: RegistroPontoRepository,
        private val colaboradorRepository: ColaboradorRepository,
        private val jwtUtil: JwtUtil,
        private val emailService: EmailService
) {
    fun registroPonto(matricula: String, token: String): ResponseEntity<Any> {

        jwtUtil.isValidToken(token)

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

    fun visualizaPontoDia(colaboradorId: Long, data: LocalDate, token: String): ResponseEntity<Any> {

        jwtUtil.isValidToken(token)

        val colaboradorOpt = colaboradorRepository.findById(colaboradorId)

        if (colaboradorOpt.isEmpty) {
            val errorResponse = ErrorResponseDto(
                    codigo = HttpStatus.NOT_FOUND.value(),
                    mensagem = "Colaborador não encontrado"
            )

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        val registros = registroPontoRepository.findTopByColaboradorAndDateOrderByHoraDesc(colaborador = colaboradorOpt.get(), data)

        val registrosDto = registros.map { RegistroPontoResponseDto(it.tipo, it.hora, it.colaborador.nome, it.colaborador.matricula) }

        if (registrosDto.size % 2 != 0) {
            val visualizacaoRegistrosResponseDto = VisualizacaoRegistrosResponseDto(
                    registrosDto,
                    0,
                    "Quantidade de registros inconsistente")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(visualizacaoRegistrosResponseDto)
        }

        val visualizacaoRegistrosResponseDto = VisualizacaoRegistrosResponseDto(
                registrosDto,
                calculaHorasTrabalhadasDia(registrosDto),
                "")

        return ResponseEntity.ok(visualizacaoRegistrosResponseDto)
    }

    suspend fun visualizaEspelhoPonto(colaboradorId: Long, emailDestinatario: String, ano: Int, mes: Int, token: String): ResponseEntity<Any> {

        jwtUtil.isValidToken(token)

        val colaboradorOpt = colaboradorRepository.findById(colaboradorId)

        if (colaboradorOpt.isEmpty) {
            val errorResponse = ErrorResponseDto(
                    codigo = HttpStatus.NOT_FOUND.value(),
                    mensagem = "Colaborador não encontrado"
            )

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
        }

        if (mes <= 0 && mes > LocalDate.now().month.value) {
            val errorResponse = ErrorResponseDto(
                    codigo = HttpStatus.BAD_REQUEST.value(),
                    mensagem = "Data inválida"
            )

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }

        if (ano <= 0 && ano > LocalDate.now().year) {
            val errorResponse = ErrorResponseDto(
                    codigo = HttpStatus.BAD_REQUEST.value(),
                    mensagem = "Data inválida"
            )

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
        }


        val colaborador = colaboradorOpt.get()

        val dataInicio = LocalDateTime.of(ano, Month.of(mes), 1, 0, 0)
        val dataFim = dataInicio.plusMonths(1).minusDays(1)

        val registros = registroPontoRepository.findTopByColaboradorAndYearAndHourOrderByHoraDesc(colaborador, dataInicio, dataFim)

        val registrosDto = registros.map { RegistroPontoResponseDto(it.tipo, it.hora, it.colaborador.nome, it.colaborador.matricula) }

        val mapRegistros = agrupaPordia(registrosDto)

        var listVisualizacaoRegistrosResponseDto = mutableListOf<VisualizacaoRegistrosResponseDto>()

        var totalHoras = 0L

        mapRegistros.entries.forEach({

            if (it.value.size % 2 != 0) {
                val visualizacaoRegistrosResponseDto = VisualizacaoRegistrosResponseDto(
                        registrosDto,
                        0,
                        "Quantidade de registros inconsistente")
                listVisualizacaoRegistrosResponseDto.add(visualizacaoRegistrosResponseDto)
            } else {
                val visualizacaoRegistrosResponseDto = VisualizacaoRegistrosResponseDto(
                        it.value,
                        calculaHorasTrabalhadasDia(it.value),
                        "")
                listVisualizacaoRegistrosResponseDto.add(visualizacaoRegistrosResponseDto)
                totalHoras += visualizacaoRegistrosResponseDto.horasTrabalhadas
            }
        })

        val espelhoPontoDto = EspelhoPontoDto(listVisualizacaoRegistrosResponseDto, totalHoras)

        emailService.enviarEmail(emailDestinatario, "Ponto Eletrônico", espelhoPontoEmFormatoHtml(espelhoPontoDto))

        return ResponseEntity.ok(espelhoPontoDto)

    }

    private fun espelhoPontoEmFormatoHtml(espelhoPontoDto: EspelhoPontoDto): String {

        var mensagemHtml: String = "<h1>Ponto Eletrônico</h1>"


        mensagemHtml += "<table>"


        espelhoPontoDto.registros.forEach({

            mensagemHtml += "<tr>"
            mensagemHtml += "<td>Dia</td>"
            mensagemHtml += "<td>Registros</td>"
            mensagemHtml += "</tr>"

            mensagemHtml += "<tr>"
            mensagemHtml += "<td>" + it.registros.get(0).hora.dayOfMonth + "</td>"
            mensagemHtml += "<td><table><tr><td>Evento</td><tr>Hora</td></tr>"
            it.registros.forEach({
                mensagemHtml += "<tr><td>" + it.tipo + "</td><td>" + it.hora.hour + "</td></tr>"
            })
            mensagemHtml += "</td></tr>"
        })

        mensagemHtml += "</table>"

        mensagemHtml += "<p>Total de Horas Trabalhadas: " + espelhoPontoDto.totalHoras + "</p>"


        return mensagemHtml
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

    private fun calculaHorasTrabalhadasDia(registrosDto: List<RegistroPontoResponseDto>): Long {
        val registrosOrdered = registrosDto.sortedBy { it.hora }

        var horaEntrada: LocalDateTime = LocalDateTime.now()
        var horaSaida: LocalDateTime = LocalDateTime.now()
        var qtdeHorasTrabalhadas: Long = 0

        registrosOrdered.forEach( {
            if (it.tipo == "entrada") {
                horaEntrada = it.hora
            } else {
                horaSaida = it.hora
                qtdeHorasTrabalhadas += ChronoUnit.HOURS.between(horaEntrada, horaSaida)
            }
        })

        return qtdeHorasTrabalhadas
    }

    private fun agrupaPordia(registrosDto: List<RegistroPontoResponseDto>): Map<Int, List<RegistroPontoResponseDto>> {

        val mapDias = mutableMapOf<Int, MutableList<RegistroPontoResponseDto>>()

        for (registro in registrosDto) {

            val diaDoMes = registro.hora.dayOfMonth
            val listaDia = mapDias.getOrPut(diaDoMes) { mutableListOf() }
            listaDia.add(registro)
        }

        return mapDias
    }
}
