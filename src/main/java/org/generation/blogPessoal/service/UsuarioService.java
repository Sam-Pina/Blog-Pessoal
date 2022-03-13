package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	/**
	 * Cadastrar Usuário
	 * 
	 * Checa se o usuário já existe no Banco de Dados através do método
	 * findByUsuario, porquê não pode existir 2 usuários com o mesmo email. Se não
	 * existir retorna um Optional vazio.
	 * 
	 * isPresent() -> Se um valor estiver presente retorna true, caso contrário
	 * retorna false.
	 * 
	 * 
	 */
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(usuarioRepository.save(usuario));
	}

	/**
	 * Atualizar Usuário
	 * 
	 * Checa se o usuário já existe no Banco de Dados através do método findById,
	 * porquê não é possíve atualizar 1 usuário inexistente. Se não existir retorna
	 * um Optional vazio.
	 * 
	 * isPresent() -> Se um valor estiver presente retorna true, caso contrário
	 * retorna false.
	 * 
	 */
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			if (buscaUsuario.isPresent()) {
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			return Optional.of(usuarioRepository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
	}
	
	public Optional<UserLogin> logarUsuario(Optional<UserLogin> usuarioLogin) {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(generatorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
                                usuarioLogin.get().setSenha(usuario.get().getSenha());              
				return usuarioLogin;
			}
		}
		throw new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);
	}

	/**
	 * Método Criptografar Senhas.
	 * 
	 * Instancia um objeto da Classe BCryptPasswordEncoder para criptografar a senha
	 * do usuário.
	 *
	 * O método encode retorna a senha criptografada no formato BCrypt. Para mais
	 * detalhes, consulte a documentação do BCryptPasswordEncoder.
	 * 
	 */
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(senha);
		return senhaEncoder;
	}

	/**
	 * Método Comparar Senhas.
	 * 
	 * Checa se a senha enviada, depois de criptografada, é igual a senha gravada no
	 * Banco de Dados.
	 * 
	 * Instancia um objeto da Classe BCryptPasswordEncoder para comparar a senha do
	 * usuário com a senha gravad no Banco de dados.
	 *
	 * matches -> Verifca se a senha codificada obtida do banco de dados corresponde
	 * à senha enviada depois que ela também for codificada. Retorna verdadeiro se
	 * as senhas coincidem e falso se não coincidem.
	 * 
	 */
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaBanco);
	}

	/**
	 * Método Gerar Basic Token
	 * 
	 * A primeira linha, monta uma String (token) seguindo o padrão Basic, através
	 * da concatenação de caracteres que será codificada (Não criptografada) no
	 * formato Base64, através da Dependência Apache Commons Codec.
	 * 
	 * Essa String tem o formato padrão: <username>:<password> que não pode ser
	 * alterado
	 *
	 * Na segunda linha, faremos a codificação em Base 64 da String.
	 * 
	 * Observe que o vetor tokenBase64 é do tipo Byte para receber o resultado da
	 * codificação, porquê durante o processo é necessário trabalhar diretamente com
	 * os bits (0 e 1) da String
	 * 
	 * Base64.encodeBase64 -> aplica o algoritmo de codificação do Código Decimal
	 * para Base64, que foi gerado no próximo método. Para mais detalhes, veja
	 * Codificação 64 bits na Documentação.
	 * 
	 * Charset.forName("US-ASCII") -> Retorna o codigo ASCII (formato Decimal) de
	 * cada caractere da String. Para mais detalhes, veja a Tabela ASCII na
	 * Documentação.
	 *
	 * Na terceira linha, acrescenta a palavra Basic acompanhada de um espaço em
	 * branco (Obrigatório), além de converter o vetor de Bytes novamente em String
	 * e concatenar tudo em uma única String.
	 * 
	 * O espaço depois da palavra Basic é obrigatório. Caso não seja inserrido, o
	 * Token não será reconhecido.
	 */
	private String generatorBasicToken(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(structureBase64);
	}

}