# 📝 MyTasks - Gerenciador de Tarefas Android

O **MyTasks** é um aplicativo Android nativo desenvolvido em Kotlin para gerenciamento de tarefas pessoais. O projeto foca em boas práticas de arquitetura, interface moderna e integração com serviços em nuvem.

## 🚀 Funcionalidades

* **Autenticação:**
    * Login e Cadastro com E-mail e Senha (Firebase Auth).
    * Login Social com Google (Integração Google Sign-In / Credential Manager).
* **Gerenciamento de Tarefas (CRUD):**
    * Listagem de tarefas com indicadores visuais de status.
    * Criação e Edição de tarefas com validação de campos.
    * Exclusão com gesto de "Swipe" (deslizar) e opção de desfazer (Undo).
* **Interface Inteligente:**
    * **Cores Dinâmicas:** Cards mudam de cor lateralmente baseados no status (Atrasada, Hoje, Futura, Concluída).
    * **Customização:** Tela de configurações para alterar o formato de exibição da data (Curto ou Extenso).
* **Segurança:** Dados do usuário protegidos e vinculados ao UID do Firebase.

## 🛠 Tech Stack & Bibliotecas

O projeto foi construído utilizando a arquitetura **MVVM (Model-View-ViewModel)** para garantir separação de responsabilidades e testabilidade.

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Componentes de Arquitetura:**
    * ViewModel & LiveData.
    * ViewBinding.
    * Navigation (Intents & Activities).
* **Networking & Dados:**
    * [Retrofit2](https://square.github.io/retrofit/) (Consumo de API REST).
    * GSON (Conversor JSON).
    * SharedPreferences (Configurações locais).
* **Integrações:**
    * Firebase Authentication.
    * Google Play Services Auth.
* **Gerenciamento de Dependências:**
    * Gradle Version Catalogs (`libs.versions.toml`).
* **Design:**
    * Material Design Components (Material 3).
    * ConstraintLayout & CardView.
    * Recycler View com ListAdapter.

## ⚙️ Como rodar o projeto

### Pré-requisitos
* Android Studio Ladybug ou superior.
* JDK 17 ou superior.

### Passo a passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/mcscorazza/DM125_MyTasks.git](https://github.com/mcscorazza/DM125_MyTasks.git)
    ```

2.  **Configuração do Firebase:**
    * Este projeto utiliza o Firebase. Para rodar, você precisará criar um projeto no [Console do Firebase](https://console.firebase.google.com/).
    * Adicione um app Android no console usando o pacote `dev.corazza.mytasks`.
    * Gere o arquivo `google-services.json` e coloque na pasta `app/` do projeto.
    * **Importante:** Para o Login Google funcionar, adicione o SHA-1 da sua máquina nas configurações do projeto no Firebase.

3.  **API Backend:**
    * Certifique-se de que a API REST que o app consome está rodando e a URL base no Retrofit está apontando para o endereço correto (ex: localhost, IP da rede ou servidor nuvem).

4.  **Build:**
    * Abra o projeto no Android Studio, aguarde o Sync do Gradle e execute (`Shift + F10`).

## ✒️ Autor

**Marcos Corazza**
* LinkedIn: [LinkedIn](https://linkedin.com/in/corazza)
* GitHub: [GitHub](https://github.com/mcscorazza)

---
_Desenvolvido como parte dos estudos da Pós Graduação em Desenvolvimento Mobile `DM125 - Desenvolvimento de aplicativos em Kotlin para Android com Firebase`._
