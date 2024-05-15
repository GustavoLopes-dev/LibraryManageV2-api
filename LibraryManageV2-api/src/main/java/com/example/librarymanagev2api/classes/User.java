package com.example.librarymanagev2api.classes;

import com.example.librarymanagev2api.enums.BookCategory;
import com.example.librarymanagev2api.enums.Gender;
import com.example.librarymanagev2api.enums.Profile;
import com.example.librarymanagev2api.enums.Status;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Document("users")
public class User {
    /**
     * Variaveis do usuario
     * */
    private static List<String>   id_inUse;

    @Id
    private String         user_id;
    private String                user_name;
    private Integer               user_age;
    private String                birth_date;
    private String                user_cpf;
    private String                user_email;
    private String                user_phone;
    private String                user_cep;
    private Profile               user_profile;
    private Status                user_status;
    private Gender                user_gender;
    private boolean               has_located;

    private List<Book> user_books = new ArrayList<>();
    private List<BookCategory> user_favGenres = new ArrayList<>();
    private List<Book> user_recommendations = new ArrayList<>();
    private List<String> user_observations = new ArrayList<>();
    private Address user_address;

    // Classe Builder interna para construir instâncias de User
    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder withUserName(String userName) {
            user.setUser_name(userName);
            return this;
        }

        public Builder withBirthDate(String birthDate) {
            user.setBirth_date(birthDate);
            return this;
        }

        public Builder withUserCPF(String userCPF) {
            user.setUser_cpf(userCPF);
            return this;
        }

        public Builder withUserEmail(String userEmail) {
            user.setUser_email(userEmail);
            return this;
        }

        public Builder withUserPhone(String userPhone) {
            user.setUser_phone(userPhone);
            return this;
        }

        public Builder withUserCEP(String userCEP) {
            user.setUser_cep(userCEP);
            return this;
        }

        public Builder withUserProfile(Profile userProfile) {
            user.setUser_profile(userProfile);
            return this;
        }

        public Builder withUserStatus(Status userStatus) {
            user.setUser_status(userStatus);
            return this;
        }

        public Builder withUserGender(Gender userGender) {
            user.setUser_gender(userGender);
            return this;
        }

        public Builder withUserFavGenres(List<BookCategory> userFavGenres) {
            user.setUser_favGenres(userFavGenres);
            return this;
        }

        public Builder withUserAddress(Address userAddress) {
            user.setUser_address(userAddress);
            return this;
        }

        public User build() {
            user.setUser_profile(Profile.DEFAULT);
            user.setUser_status(Status.ACTIVE);
            user.setUser_address(null);
            return user;
        }
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * Método para gerar um novo ID de usuário aleatório e único.
     * Este método gera um ID de usuário aleatório de 8 caracteres, composto por letras maiúsculas e dígitos de 0 a 9.
     * O ID gerado é verificado para garantir que seja único, evitando duplicatas na lista de IDs em uso.
     * @return O novo ID de usuário gerado.
     */
    public static String generateUserId() {
        String userId;
        do {
            userId = generateRandomId();
        } while (id_inUse.contains(userId)); // Verifica se o ID já está em uso
        id_inUse.add(userId); // Adiciona o novo ID à lista de IDs em uso
        return userId;
    }

    /**
     * Método para gerar um ID de usuário aleatório.
     * Este método gera um ID de usuário aleatório de 8 caracteres, composto por letras maiúsculas e dígitos de 0 a 9.
     * @return O ID de usuário aleatório gerado.
     */
    private static String generateRandomId() {
        // Tamanho desejado para o ID
        int length = 8;
        // Caracteres que podem ser usados no ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // Objeto para gerar números aleatórios
        Random random = new Random();
        // StringBuilder para construir o ID
        StringBuilder idBuilder = new StringBuilder();
        // Gera cada caractere do ID aleatoriamente
        for (int i = 0; i < length; i++) {
            // Gera um índice aleatório dentro do intervalo de caracteres disponíveis
            int index = random.nextInt(characters.length());
            // Adiciona o caractere correspondente ao índice ao ID
            idBuilder.append(characters.charAt(index));
        }
        return idBuilder.toString();
    }

    // Método para verificar se um ID específico já está em uso
    public static boolean isIdInUse(String userId) {
        return id_inUse.contains(userId);
    }

    // Método para limpar a lista de IDs em uso (usado para reinicializar)
    public static void clearIdInUse() {
        id_inUse.clear();
    }
    /**
     * Retorna o ID do usuário.
     *
     * @return O ID do usuário.
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Retorna o nome do usuário em letras maiúsculas.
     *
     * @return O nome do usuário em letras maiúsculas.
     */
    public String getUser_name() {
        return user_name.toUpperCase();
    }

    /**
     * Define a idade do usuário com base na data de nascimento fornecida.
     *
     * @param birth_date A data de nascimento do usuário no formato "ddmmaaaa".
     * @return A idade do usuário.
     */
    public Integer setUser_age(String birth_date) {
        // Remover qualquer caractere que não seja número
        String cleanedDateStr = birth_date.replaceAll("[^\\d]", "");

        // Converter para um valor numérico de data
        int birthDateValue = Integer.parseInt(cleanedDateStr);

        // Converter para LocalDate
        LocalDate birthDateFormatted = LocalDate.parse(String.format("%08d", birthDateValue),
                DateTimeFormatter.ofPattern("ddMMuuuu"));

        // Calcular a idade
        LocalDate currentDate = LocalDate.now();
        long yearsBetween = ChronoUnit.YEARS.between(birthDateFormatted, currentDate);
        user_age = (int) yearsBetween;
        return user_age;
    }

    /**
     * Retorna a data de nascimento do usuário.
     *
     * @return A data de nascimento do usuário no formato "dd/MM/aaaa".
     */
    public String getBirth_date() {
        return birth_date;
    }

    /**
     * Define a data de nascimento do usuário com base na string fornecida.
     * A string de data fornecida deve estar no formato "ddmmaaaa".
     * Se a string de data estiver em um formato inválido, uma mensagem de erro será exibida no console.
     *
     * @param birth_date A string representando a data de nascimento no formato "ddmmaaaa".
     */
    public void setBirth_date(String birth_date) {
        try {
            // Formatar a data de entrada para o formato desejado
            SimpleDateFormat inputFormat = new SimpleDateFormat("ddmmaaa");
            Date date = inputFormat.parse(birth_date);

            // Formatar a data para o formato dd/mm/aaaa
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            this.birth_date = outputFormat.format(date);
            setUser_age(birth_date);
        } catch (ParseException e) {
            // Lidar com uma possível exceção de análise
            System.out.println("Erro ao analisar a data: " + e.getMessage());
        }
    }

    /**
     * Retorna o CPF do usuário.
     *
     * @return O CPF do usuário.
     */
    public String getUser_cpf() {
        return user_cpf;
    }

    /**
     * Define o CPF do usuário com base no valor fornecido.
     *
     * @param user_cpf O CPF do usuário.
     */
    public void setUser_cpf(String user_cpf) {
        // Remover caracteres não numéricos do CPF
        String cleanedCpf = user_cpf.replaceAll("\\D", "");

        // Verificar se o CPF tem o tamanho correto
        if (cleanedCpf.length() != 11) {
            System.out.println("CPF inválido. Deve conter 11 dígitos.");
            return;
        }

        // Formatar o CPF no formato xxx.xxx.xxx-xx
        this.user_cpf = String.format("%s.%s.%s-%s", cleanedCpf.substring(0, 3),
                cleanedCpf.substring(3, 6),
                cleanedCpf.substring(6, 9),
                cleanedCpf.substring(9));
    }

    /**
     * Retorna o endereço de e-mail do usuário.
     *
     * @return O endereço de e-mail do usuário.
     */
    public String getUser_email() {
        return user_email;
    }

    /**
     * Define o endereço de e-mail do usuário após validar se está em um formato correto.
     *
     * @param user_email O endereço de e-mail a ser definido para o usuário.
     * @throws IllegalArgumentException Se o endereço de e-mail não estiver em um formato válido.
     */
    public void setUser_email(String user_email) {
        // Expressão regular para validar o formato do e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compilar a expressão regular
        Pattern pattern = Pattern.compile(emailRegex);

        // Verificar se o e-mail corresponde ao padrão
        Matcher matcher = pattern.matcher(user_email);

        if (matcher.matches()) {
            this.user_email = user_email;
            System.out.println("E-mail válido.");
        } else {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

    /**
     * Obtém o número de telefone do usuário.
     *
     * @return O número de telefone do usuário.
     */
    public String getUser_phone() {
        return user_phone;
    }

    /**
     * Define o número de telefone do usuário após remover caracteres não numéricos e validar o tamanho.
     *
     * @param user_phone O número de telefone a ser definido para o usuário.
     * @throws IllegalArgumentException Se o número de telefone não estiver no tamanho válido (entre 11 e 15 dígitos).
     */
    public void setUser_phone(String user_phone) {
        // Remover todos os caracteres não numéricos
        String cleanedPhone = user_phone.replaceAll("\\D", "");

        // Verificar se o tamanho do número de telefone está correto (entre 11 e 15 dígitos)
        if (cleanedPhone.length() >= 11 && cleanedPhone.length() <= 15) {
            // Formatar o número de telefone no formato desejado
            this.user_phone = formatPhoneNumber(cleanedPhone);
        } else {
            throw new IllegalArgumentException("Número de telefone inválido. Deve ter entre 11 e 15 dígitos.");
        }
    }

    /**
     * Formata o número de telefone fornecido para o formato internacional desejado.
     *
     * @param phone O número de telefone a ser formatado.
     * @return O número de telefone formatado no formato internacional.
     */
    private String formatPhoneNumber(String phone) {
        StringBuilder formattedPhone = new StringBuilder();

        // Adicionar o prefixo "+"
        formattedPhone.append("+");

        // Adicionar os dois primeiros dígitos
        formattedPhone.append(phone.substring(0, 2));

        // Adicionar o código de área entre parênteses
        if (phone.length() >= 4) {
            formattedPhone.append("(").append(phone.substring(2, 4)).append(")");
        }

        // Adicionar os próximos cinco dígitos
        if (phone.length() >= 9) {
            formattedPhone.append(phone.substring(4, 9));
        }

        // Adicionar o traço
        formattedPhone.append("-");

        // Adicionar os últimos quatro dígitos
        if (phone.length() >= 13) {
            formattedPhone.append(phone.substring(9, 13));
        }

        return formattedPhone.toString();
    }

    /**
     * Retorna o CEP do usuário.
     *
     * @return O CEP do usuário.
     */
    public String getUser_cep() {
        return user_cep;
    }

    /**
     * Define o CEP do usuário.
     *
     * @param user_cep O CEP a ser definido para o usuário.
     */
    public void setUser_cep(String user_cep) {
        this.user_cep = user_cep;
    }

    /**
     * Retorna o perfil do usuário.
     *
     * @return O perfil do usuário.
     */
    public Profile getUser_profile() {
        return user_profile;
    }

    /**
     * Define o perfil do usuário.
     *
     * @param user_profile O perfil a ser definido para o usuário.
     */
    public void setUser_profile(Profile user_profile) {
        this.user_profile = user_profile;
    }

    /**
     * Retorna o status do usuário.
     *
     * @return O status do usuário.
     */
    public Status getUser_status() {
        return user_status;
    }

    /**
     * Define o status do usuário.
     *
     * @param user_status O status a ser definido para o usuário.
     */
    public void setUser_status(Status user_status) {
        this.user_status = user_status;
    }

    /**
     * Verifica se o usuário possui livros localizados.
     *
     * @return true se o usuário possui livros localizados, false caso contrário.
     */
    public boolean getHas_located() {
        return user_books != null;
    }

    /**
     * Retorna a lista de livros do usuário.
     *
     * @return A lista de livros do usuário, ou null se não houver livros localizados.
     */
    public List<Book> getUser_books() {
        if (getHas_located()) {
            return user_books;
        } else {
            return null;
        }
    }

    /**
     * Adiciona um livro à lista de livros do usuário.
     *
     * @param bookadd O livro a ser adicionado à lista.
     */
    public void setUser_books(Book bookadd) {
        this.user_books.add(bookadd);
    }

    /**
     * Retorna a lista de gêneros de livros favoritos do usuário.
     *
     * @return A lista de gêneros de livros favoritos do usuário.
     */
    public List<BookCategory> getUser_favGenres() {
        return user_favGenres;
    }

    /**
     * Define a lista de gêneros de livros favoritos do usuário.
     *
     * @param user_favGenres A lista de gêneros de livros favoritos do usuário.
     */
    public void setUser_favGenres(List<BookCategory> user_favGenres) {
        this.user_favGenres = user_favGenres;
    }

    /**
     * Retorna a lista de recomendações de livros para o usuário.
     *
     * @return A lista de recomendações de livros para o usuário.
     */
//    public List<Book> getUser_recomendations() {
//        return user_recomendations;
//    }

    /**
     * Define a lista de recomendações de livros para o usuário.
     *
     * @param user_recomendations A lista de recomendações de livros para o usuário.
     */
//    public void setUser_recomendations(List<Book> user_recomendations) {
//        this.user_recomendations = user_recomendations;
//    }

    /**
     * Retorna a lista de observações do usuário.
     *
     * @return A lista de observações do usuário.
     */
    public List<String> getUser_observations() {
        return user_observations;
    }

    /**
     * Define a lista de observações do usuário.
     *
     * @param user_observations A lista de observações do usuário.
     */
    public void setUser_observations(List<String> user_observations) {
        this.user_observations = user_observations;
    }

    /**
     * Retorna o endereço do usuário.
     *
     * @return O endereço do usuário, ou null se não estiver definido.
     */
    public Address getUser_adress() {
        return user_address;
    }

    /**
     * Define o endereço do usuário.
     *
     * @param user_address O endereço do usuário.
     */
    public void setUser_address(Address user_address) {
        this.user_address = user_address;
    }

    /**
     * Define o gênero do usuário com base no caractere fornecido.
     *
     * @param user_gender O caractere representando o gênero do usuário ('M' para masculino ou 'F' para feminino).
     * @throws IllegalArgumentException Se o caractere fornecido não for 'M' ou 'F'.
     */
    public void setUser_gender(Gender user_gender) {
        this.user_gender = user_gender;
    }

    // Método para definir o gênero do usuário com base no caractere fornecido
    public void setUser_gender(Character genderChar) {
        if (genderChar.equals('M')) {
            this.user_gender = Gender.MALE;
        } else if (genderChar.equals('F')) {
            this.user_gender = Gender.FEMALE;
        } else {
            throw new IllegalArgumentException("Gênero inválido. Use 'M' para masculino ou 'F' para feminino.");
        }
    }

    /**
     * Retorna o gênero do usuário como uma string.
     *
     * @return Uma string representando o gênero do usuário ("MALE" para masculino ou "FEMALE" para feminino).
     * @throws IllegalArgumentException Se o gênero do usuário não estiver definido.
     */
    public String getUser_gender() {
        if (this.user_gender != null) {
            return this.user_gender.toString();
        } else {
            throw new IllegalArgumentException("Há algo errado no registro do usuário.");
        }
    }
}
