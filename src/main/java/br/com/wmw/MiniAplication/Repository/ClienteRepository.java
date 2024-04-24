package br.com.wmw.MiniAplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wmw.MiniAplication.Domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	boolean existsByCpfCnpj(String cpfCnpj);

	void deleteByCpfCnpj(String cpfCnpj);

}
