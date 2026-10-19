# Testes da Aula 10

Alunos: Johnny Amorim Parma e Vinicius Rover

Turma: Desenvolvimento de Sistema

Seguir o modelo do documento documentacao_Aula10.docx anexado para registrar os casos de teste, os resultados obtidos, as evidências e o resumo da execução.

Foram adicionados sete testes unitários em `src/test/java/com/senai/template/services/MovimentacaoServiceTest.java`, usando JUnit Jupiter e Mockito. Os repositórios são simulados; não é necessário iniciar MySQL nem o contexto Spring para essa classe. Nenhuma regra da implementação foi alterada.

| Método | Caso documentado | Verificação |
|---|---|---|
| ct27EntradaSomaSaldoERegistraHistorico | CT-27 | Entrada de estoque e histórico |
| ct28SaidaSubtraiSaldoERegistraHistorico | CT-28 | Saída de estoque e histórico |
| ct29SaidaMaiorQueSaldoNaoPersisteAlteracoes | CT-29 | Rejeição sem alteração de saldo ou histórico |
| ct31QuantidadeZeroNaoConsultaNemPersisteDados | CT-31 | Quantidade zero rejeitada |
| ct33ProdutoInativoNaoPodeSerMovimentado | CT-33 | Produto inativo rejeitado |
| ct36SaldoIgualAoMinimoIntegraListaDeAlerta | CT-36 | Alerta no limite exato |
| ct35SaldoAcimaDoMinimoNaoIntegraListaDeAlerta | CT-35 | Ausência de alerta acima do mínimo |

Para executar apenas os sete testes, na raiz do projeto:

```bash
./mvnw -Dtest=MovimentacaoServiceTest test
```

No Windows:

```bat
mvnw.cmd -Dtest=MovimentacaoServiceTest test
```

O pom.xml original declara java.version 21 e compiler source/target 23. Utilize um JDK compatível com essa configuração. O teste de contexto original permanece no projeto e pode exigir um banco disponível ao executar toda a suíte.

Os testes unitários cobrem o serviço em isolamento. Eles não validam a tela, autenticação, requisições HTTP ou persistência em um banco real; execute também os roteiros manuais do descritivo.

## Tentativa de execução em 19/10/2026

O comando foi iniciado, mas o Maven falhou antes da compilação ao resolver o POM pai do Spring Boot: `repo.maven.apache.org: Temporary failure in name resolution`. Nenhum dos sete testes chegou a executar; não há resultado Passou/Falhou. Além disso, o ambiente disponível possui apenas Java 17, anterior à configuração do projeto. O pom.xml não foi alterado.
