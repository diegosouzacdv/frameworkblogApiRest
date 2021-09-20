# Blogframework

Desafio reformulaçao de blog da Framework Digital , construir uma nova versão com um framework Web mais atual e utilizando o modelo de APIs.

**Requisitos Técnicos:**

- Código versionado em repositório GIT
- Java versão 8 ou superior
- Spring Boot
- Banco de Dados Postgresql
- API Restful
- Maven
- Angular 8

**Deverão ser construídas interfaces Web e APIs de forma a suportar as seguintes operações:**
| header | header |
| ------ | ------ |
| Segurança | Permitir o cadastro de usuários e login com autenticação via token JWT. |
| Post | Permitir o cadastro e consulta de posts com texto, imagens e links. Apenas o criador do post poderá ter permissão para excluí-lo. |
| Comentário | Suportar a adição e exclusão de comentários em posts. Os posts poderão ser visíveis a todos os usuários. Apenas o criador do comentário poderá ter permissão para excluí-lo. |
| Foto | Permitir a criação de álbuns de fotos. As fotos dos álbuns poderão ser visíveis a todos os usuários. Apenas o dono de um álbum poderá excluí-lo. |

