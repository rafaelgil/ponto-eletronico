package br.com.fiap.postech.pontoeletronico.pontoeletronico.service

import br.com.fiap.postech.pontoeletronico.pontoeletronico.Colaborador
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ColaboradorDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.dto.ErrorResponseDto
import br.com.fiap.postech.pontoeletronico.pontoeletronico.exception.AutenticacaoException
import br.com.fiap.postech.pontoeletronico.pontoeletronico.exception.DadosColaboradorException
import br.com.fiap.postech.pontoeletronico.pontoeletronico.repository.ColaboradorRepository
import br.com.fiap.postech.pontoeletronico.pontoeletronico.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class AutenticacaoService(
    private val colaboradorRepository: ColaboradorRepository,
    private val jwtUtil: JwtUtil
) {

    fun autenticar(colaborador: ColaboradorDto): String {

        var colaboradorOpt: Optional<Colaborador>? = null

        if (colaborador.nome.isNullOrEmpty() && !colaborador.matricula.isNullOrEmpty()) {
            colaboradorOpt = colaboradorRepository.findByMatricula(colaborador.matricula!!)

            if (colaboradorOpt.isEmpty) {
                throw DadosColaboradorException("Colaborador não encontrado")
            }

        } else if (!colaborador.nome.isNullOrEmpty() && colaborador.matricula.isNullOrEmpty()) {
            colaboradorOpt = colaboradorRepository.findByNome(colaborador.nome!!)

            if (colaboradorOpt.isEmpty) {
                throw DadosColaboradorException("Colaborador não encontrado")
            }

        } else if (colaborador.nome.isNullOrEmpty() && colaborador.matricula.isNullOrEmpty()) {
            throw DadosColaboradorException("Colaborador não encontrado")
        }

        if (colaboradorOpt!!.get().senha != colaborador.senha) {
            throw DadosColaboradorException("Senha inválida")
        }

        return jwtUtil.generateToken(colaboradorOpt.get().matricula)
    }
}