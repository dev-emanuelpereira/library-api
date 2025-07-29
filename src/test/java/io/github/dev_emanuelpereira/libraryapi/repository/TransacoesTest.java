package io.github.dev_emanuelpereira.libraryapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    TransacaoService transacaoService;

    /**
     * Commit -> Confirmar alterações
     * Rollback -> desfazer as alterações
     */
    @Transactional
    void transacaoSimples() {
        transacaoService.executar();
    }
}
