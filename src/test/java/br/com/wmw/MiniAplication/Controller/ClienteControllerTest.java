package br.com.wmw.MiniAplication.Controller;

import java.net.URI;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	void deveRetornarOkNaBuscaClientePorCodigo() throws Exception {
		URI uri = new URI("/cliente/1");
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	@Order(2)
	void deveRetornarTodosClientesCadastrado() throws Exception {
		URI uri = new URI("/cliente");
		String json = "[{\"codigo\":1,\"nome\":\"Thiaguinho\",\"tipoPessoa\":\"FISICA\",\"cpfCnpj\":\"111.111.111-11\",\"telefone\":\"(048) 99999-9999\",\"email\":\"thiaguinho@email.com\"}]";
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.content().json(json));
	}
	
	@Test
	@Order(3)
	void deveRetornarCriadoNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void deveRetornarErroCampoNomeObrigatórioNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"nome\",\"mensagem\":\"Campo obrigatório\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErroCampoTipoPessoObrigatorioNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"tipoPessoa\",\"mensagem\":\"Campo obrigatorio\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().string(response));
	}
	
	@Test
	void deveRetornarErroCampoTipoPessoInvalidoNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "{\"campo\":\"tipoPessoa\",\"mensagem\":\"Dado inválido, escolha: JURIDICA ou FISICA\"}";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErroCampoCpfCnojObrigatórioNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"cpfCnpj\",\"mensagem\":\"Campo obrigatório\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErroCPFInvalidoNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"FISICA\",\"cpfCnpj\":\"11111111111\",\"telefone\":\"99999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"cpfCnpj\",\"mensagem\":\"invalid Brazilian individual taxpayer registry number (CPF)\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErroCNPJInvalidoNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-68\",\"telefone\":\"99999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"cpfCnpj\",\"mensagem\":\"invalid Brazilian corporate taxpayer registry number (CNPJ)\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErrCpfCnpjJaCadastrado() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "{\"campo\":\"CPF/CNPJ\",\"mensagem\":\"Registro já cadastrado\"}";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	
	@Test
	void deveRetornarErroCampoTelefoneObrigatórioNoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"\",\"email\":\"Renanzinh@email.com\"}";
		String response = "[{\"campo\":\"telefone\",\"mensagem\":\"Campo obrigatório\"}]";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarNaoEncontradoQuandoCadastroClienteNaoExisteParaAtulizar() throws Exception {
		URI uri = new URI("/cliente/3");
		String json = "{\"nome\":\"Renanzinh\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"09.257.487/0001-67\",\"telefone\":\"99999999999\",\"email\":\"Renanzinh@email.com\"}";
		String response = "{\"campo\":\"id\",\"mensagem\":\"Cliente não encontrado\"}";
		mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarErroEmailInvalidoNaAtualizacaoDoCadastroCliente() throws Exception {
		URI uri = new URI("/cliente/1");
		String json = "{\"telefone\":\"99999999999\",\"email\":\"Renanzinhemail.com\"}";
		String response = "[{\"campo\":\"email\",\"mensagem\":\"E-mail inválido\"}]";
		mockMvc.perform(MockMvcRequestBuilders.put(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().json(response));
	}
	
	@Test
	void deveRetornarOkRemocaoDeClientePorCodigo() throws Exception {
		URI uri = new URI("/cliente/1");
		mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	void deveRetornarCriadoNoCadastroClienteSemEmail() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\":\"Rodrigo\",\"tipoPessoa\":\"JURIDICA\",\"cpfCnpj\":\"01.520.711/0001-50\",\"telefone\":\"99999999999\"}";
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
