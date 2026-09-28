# Plano de Ensino

## Curso: Projeto Integrador — Sistema de Gestão de Estoque para Almoxarifado

**link para página** https://karizeviecelli.github.io/devs-saep-2026-2/

**Técnico em Desenvolvimento de Sistemas | SENAI | Preparatório SAEP**

Professor(a): Karize Viecelli — [@karizeviecelli]

Carga horária total: **55 horas**, 

distribuídas em **14 aulas** (13 aulas de 4h + 1 aula de encerramento de 3h).

---

## Contexto do projeto

Uma fabricante de ferramentas manuais (martelos, chaves de fenda, alicates etc.) precisa de um sistema
para controlar entrada e saída de estoque, evitando falta e excesso de produtos. Os alunos vão construir,
ao longo do curso, um sistema web ou desktop completo que atenda a esse desafio — exatamente como
pedido no Caderno de Prova do Estudante (SAEP), cobrindo as 9 entregas oficiais.

## Fio condutor / analogia do curso

**"Montando a sua caixa de ferramentas de desenvolvedor(a)"** — assim como o almoxarifado organiza
martelos, chaves e alicates em prateleiras certas, cada aula adiciona **uma ferramenta nova** à caixa de
ferramentas do aluno (um conceito, uma técnica, uma peça do sistema). No fim do curso, a caixa está
completa e o sistema também. Cada aula abre retomando qual "ferramenta" foi guardada na aula anterior
e qual será adicionada agora.

## Checkpoints do projeto final

As aulas marcadas como **[CHECKPOINT]** correspondem a uma das 9 entregas oficiais do SAEP e usam
critério de aprovação **nota ≥ 7** no painel de feedback do professor.

---

## Mapa de aulas

| Aula | Carga | Módulo | Foco da aula | Entrega SAEP |
|---|---|---|---|---|
| 1 | 4h | 1. Levantamento e Modelagem | Contextualização do projeto + Engenharia de requisitos | **Entrega 1** — Lista de requisitos funcionais 🏁 |
| 2 | 4h | 1. Levantamento e Modelagem | Modelagem relacional de dados | **Entrega 2** — DER 🏁 |
| 3 | 4h | 1. Levantamento e Modelagem | SQL: DDL/DML, chaves primárias/estrangeiras | **Entrega 3** — Script de criação e população (`saep_db`) 🏁 |
| 4 | 4h | 2. Autenticação e Estrutura | Fluxo de autenticação e tratamento de erro de login | **Entrega 4** — Interface de login 🏁 |
| 5 | 4h | 2. Autenticação e Estrutura | Arquitetura da tela principal e navegação | **Entrega 5** — Interface principal 🏁 |
| 6 | 4h | 3. CRUD de Produtos | Listagem automática e inserção de produtos | (preparação Entrega 6) |
| 7 | 4h | 3. CRUD de Produtos | Edição, exclusão, busca dinâmica e validações | **Entrega 6** — Interface cadastro de produto 🏁 |
| 8 | 4h | 4. Gestão de Estoque | Algoritmos de ordenação + registro de movimentação | (preparação Entrega 7) |
| 9 | 4h | 4. Gestão de Estoque | Alertas de estoque mínimo e histórico/rastreabilidade | **Entrega 7** — Interface gestão de estoque 🏁 |
| 10 | 4h | 5. Qualidade e Infraestrutura | Fundamentos de teste de software e casos de teste | **Entrega 8** — Descritivo de casos de teste 🏁 |
| 11 | 4h | 5. Qualidade e Infraestrutura | Requisitos de infraestrutura (SGBD, linguagem, SO) | **Entrega 9** — Lista de requisitos de infraestrutura 🏁 |
| 12 | 4h | 6. Integração e Entrega | Integração fim a fim do sistema + revisão cruzada | Projeto completo (revisão) |
| 13 | 4h | 6. Integração e Entrega | Empacotamento das entregas + simulado de prova prática | Projeto completo (simulado) |
| 14 | 3h | 6. Integração e Entrega | Apresentação e encerramento | — |

**Total:** 13 × 4h + 1 × 3h = **55 horas** ✅

---

## Observações pedagógicas

- Cada aula segue o padrão: `aula-XX.md` (teoria) + 5 páginas HTML interativas (exposição → demo ao vivo →
  prática guiada → desafio → feedback do professor).
- Aulas 6 e 8 são de **preparação/aprofundamento** (sem entrega fechada ainda), pois os itens 6 e 7 do
  Caderno de Prova são os mais longos (45 min estimados cada) e exigem duas aulas para cobrir bem:
  listagem/inserção antes, edição/exclusão/busca/validação depois (mesma lógica para estoque:
  ordenação/movimentação antes, alertas/histórico depois).
- Aulas 12 e 13 não trazem conceito novo: são aulas de **projeto** — prática guiada vira roteiro de
  integração/empacotamento, desafio vira checklist de progresso.
- Aula 14 é de **encerramento**: exposição vira briefing do formato de apresentação, demo vira roteiro-
  modelo de fala, prática guiada vira guia de revisão por pares, desafio vira checklist cronometrado de
  últimos ajustes, e o feedback avalia critérios de apresentação (clareza, demo funcionando, domínio
  técnico, cumprimento do formato de entrega em .zip/.rar/.7zip).

---

Direitos Reservados  Karize Viecelli — @karizeviecelli
