package com.jk.helpdesk.services;

import com.jk.helpdesk.domain.Chamado;
import com.jk.helpdesk.domain.Cliente;
import com.jk.helpdesk.domain.Tecnico;
import com.jk.helpdesk.domain.dto.ChamadoDTO;
import com.jk.helpdesk.domain.dto.ClienteDTO;
import com.jk.helpdesk.domain.dto.TecnicoDTO;
import com.jk.helpdesk.domain.enums.Prioridade;
import com.jk.helpdesk.domain.enums.Status;
import com.jk.helpdesk.repositories.ChamadoRepository;
import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public ChamadoDTO findById(Integer id){
        Optional<Chamado> obj = chamadoRepository.findById(id);
        Optional<ChamadoDTO> dto = obj.map(ChamadoDTO::new);
        return dto.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID " + id));
    }

    public List<ChamadoDTO> findAll() {
        List<Chamado> chamadoList = chamadoRepository.findAll();
        return chamadoList.stream().map(ChamadoDTO::new).collect(Collectors.toList());
    }

    public ChamadoDTO create(@Valid ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoRepository.save(newChamado(chamadoDTO));
        return new ChamadoDTO(chamado);
    }

    public ChamadoDTO update(Integer id, ChamadoDTO chamadoDTO) {
        chamadoDTO.setId(id);
        findById(id);
        Chamado oldObj = newChamado(chamadoDTO);
        return new ChamadoDTO(chamadoRepository.save(oldObj));
    }

    private Chamado newChamado(ChamadoDTO chamadoDTO){
        TecnicoDTO tecnicoDTO = tecnicoService.findById(chamadoDTO.getTecnico());
        ClienteDTO clienteDTO = clienteService.findById(chamadoDTO.getCliente());
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        Cliente cliente = new Cliente(clienteDTO);
        Chamado chamado = new Chamado();
        if(chamadoDTO.getId() != null){
            chamado.setId(chamadoDTO.getId());
        }
        if(chamadoDTO.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setObservacoes(chamadoDTO.getObservacoes());
        return chamado;
    }
}
