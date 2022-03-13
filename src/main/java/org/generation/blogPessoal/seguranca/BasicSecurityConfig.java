package org.generation.blogPessoal.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * English:
 * The Spring Security @EnableWebSecurity annotation is annotated at class level with @Configuration 
 * annotation to enable web securities in our application defined by WebSecurityConfigurer implementations. 
 * The WebSecurityConfigurerAdapter is the implementation class of the WebSecurityConfigurer interface. 
 * The @EnableWebSecurity enables the web securities defined by WebSecurityConfigurerAdapter automatically. 
 * To override web securities defined by WebSecurityConfigurerAdapter in our Java configuration class, 
 * we need to extend this class and override its methods.
 * 
 * 
 * Portuguese:
 * A anotação Spring Security @EnableWebSecurity é anotada no nível de classe com a anotação @Configuration 
 * para habilitar seguranças da web em nosso aplicativo definido pelas implementações do WebSecurityConfigurer. 
 * O WebSecurityConfigurerAdapter é a classe de implementação da interface WebSecurityConfigurer. 
 * O @EnableWebSecurity habilita os títulos da web definidos pelo WebSecurityConfigurerAdapter automaticamente. 
 * Para substituir os títulos da Web definidos por WebSecurityConfigurerAdapter em nossa classe de configuração Java, 
 * precisamos estender essa classe e substituir seus métodos.
 * 
 * 
 * @author Samuel
 * @since final version 03/13/22(eng); versão final 13/03/22(pt-br);
 *
 */
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	/**
	 * English:
	 * This method authenticates existing records in our database. inMemoryAuthentication is a default user that we choose to stay in memory,
	 * it serves to have access when we are going to run on the web.
	 * 
	 * 
	 * Portuguese:
	 * Esse metodos faz a autenticação de cadastros existentes em nosso banco de dados. O inMemoryAuthentication é um usuario padrao que escolhemos para ficar em memoria,
	 * ele serve para termos acesso quando formos executar na web.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
		auth.inMemoryAuthentication().withUser("Samuel").password(passwordEncoder().encode("1234")).authorities("ROLE_ADMIN");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * antMatchers().permitAll -> Endpoint liberado de autenticação
	 * 
	 * HttpMethod.OPTIONS -> O parâmetro HttpMethod.OPTIONS permite que o cliente
	 * (frontend), possa descobrir quais são as opções de requisição permitidas para
	 * um determinado recurso em um servidor. Nesta implementação, está sendo
	 * liberada todas as opções das requisições através do método permitAll().
	 * 
	 * anyRequest().authenticated() -> Todos os demais endpoints serão autenticados
	 * 
	 * httpBasic -> Tipo de autenticação http (Basic Security)
	 * 
	 * Http Sessions: As sessões HTTP são um recurso que permite que os servidores
	 * da Web mantenham a identidade do usuário e armazenem dados específicos do
	 * usuário durante várias interações (request/response) entre um aplicativo
	 * cliente e um aplicativo da Web.
	 * 
	 * sessionManagement() -> Cria um gerenciador de Sessões
	 * 
	 * sessionCreationPolicy(SessionCreationPolicy.STATELESS) -> Define como o
	 * Spring Secuiryt irá criar (ou não) as sessões
	 * 
	 * STATELESS -> Nunca será criada uma sessão, ou seja, basta enviar o token
	 * através do cabeçalho da requisição que a mesma será processada.
	 * 
	 * cors -> O compartilhamento de recursos de origem cruzada (CORS) surgiu porquê
	 * os navegadores não permitem solicitações feitas por um domínio (endereço)
	 * diferente daquele de onde o site foi carregado. Desta forma o Frontend da
	 * aplicação, por exemplo, obrigatoriamente teria que estar no mesmo domínio que
	 * o Backend. Habilitando o CORS, o Spring desabilita esta regra e permite
	 * conexões de outros domínios.
	 * 
	 * CSRF: O cross-site request forgery (falsificação de solicitação entre sites),
	 * é um tipo de ataque no qual comandos não autorizados são transmitidos a
	 * partir de um usuário em quem a aplicação web confia.
	 * 
	 * csrf().disabled() -> Esta opção de proteção é habilitada por padrão no Spring
	 * Security, entretanto precisamos desabilitar, caso contrário, todos os
	 * endpoints que respondem ao verbo POST não serão executados.
	 * 
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		.and().csrf().disable();
	}
}
