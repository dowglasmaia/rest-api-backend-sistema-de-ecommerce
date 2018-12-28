package com.maia.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.maia.cursomc.domain.Cidade;
import com.maia.cursomc.domain.Cliente;
import com.maia.cursomc.domain.Endereco;
import com.maia.cursomc.domain.enums.Perfil;
import com.maia.cursomc.domain.enums.TipoPessoa;
import com.maia.cursomc.domain.security.UserSS;
import com.maia.cursomc.dto.ClienteDTO;
import com.maia.cursomc.dto.ClienteNewDTO;
import com.maia.cursomc.repositores.ClienteRepository;
import com.maia.cursomc.repositores.EnderecoRepository;
import com.maia.cursomc.services.exception.AuthorizationException;
import com.maia.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe; // para encodar a Senha no banco de dados

	@Autowired
	private S3Service s3Service;

	@Autowired // instanciando o Repositorio do Cliente
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}") // pegando do application.properties
	private String prefix;

	// metodo para BusarPor ID com SpringDataJPA
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated(); // pega o Usuario Logado, autenticado
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado!");
		}

		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	// Inserir / Save
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	// update
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	// metodo auxilar para atualizar um novo cliente
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	// Delete
	public void delete(Integer id) {
		find(id); // Chama o Metodo Buscar por Id pra vereficar se o mesmo Existe
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Não é Possível Excluir Este Cliente, Poque a Pedidos Relacionados.");
		}
	}

	// Listar Todas as Clientes
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// Listando Clientes por Paginação
	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	// Metodo Auxiliar para Instaciar Uma Cliente Apartir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	// metodo para Salvar o Cliente Com Todos as Suas dependencias
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOrCnpf(),
				TipoPessoa.toEnum(objDTO.getTipoPessoa()), pe.encode(objDTO.getSenha()));

		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);

		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemtno(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());

		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}

	// Metodo para enviar a fto do cliente para o AWS S3
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated(); // pega o Usuario Logado, autenticado
		if (user == null) {
			throw new AuthorizationException("Acesso Negado!, Faço o Login para Continuar sua Operção!");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		String fileName = prefix + user.getId() + ".jpg";
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}

}
