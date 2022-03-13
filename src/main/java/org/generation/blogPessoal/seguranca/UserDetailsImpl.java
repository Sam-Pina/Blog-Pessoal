package org.generation.blogPessoal.seguranca;

import java.util.Collection;
import java.util.List;

import org.generation.blogPessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe UserDetailsImpl
 * 
 * Implementa a interface UserDetails, que descreve o usuário para o Spring
 * Security,ou seja, detalha as caracteríticas do usuário.
 * 
 * Por se tratar de uma implementação de uma interface, a classe deve ter em seu
 * nome o sufixo Impl para indicar que se trata de uma implementação.
 * 
 * As características descritas na interface UserDetails são:
 * 
 * 1) Credenciais do usuário (Username e Password) 
 * 2) As Autorizações do usuário (o que ele pode e não pode fazer), através da Collection authorities do tipo GrantedAuthority 
 * 3) As Restrições (isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired() e isEnabled()) da conta do usuário.
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Usuario user) {
		this.userName= user.getUsuario();
		this.password = user.getSenha();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}